package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.RAM;
import ua.alexd.excelInteraction.imports.RAMExcelImporter;
import ua.alexd.repos.RAMRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.RAMSpecification.memoryEqual;
import static ua.alexd.specification.RAMSpecification.modelLike;

@Controller
@RequestMapping("/ram")
public class RAMController {
    private final RAMRepo ramRepo;
    private static Iterable<RAM> lastOutputtedRams;

    private final RAMExcelImporter excelImporter;

    public RAMController(RAMRepo ramRepo, RAMExcelImporter excelImporter) {
        this.ramRepo = ramRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var ramSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var rams = ramRepo.findAll(ramSpecification);
        lastOutputtedRams = rams;
        siteModel.addAttribute("rams", rams);
        return "view/ram/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newRAM") RAM newRAM, @NotNull Model model) {
        if (!saveRecord(newRAM)) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель оперативної пам'яті уже присутня в базі!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @PostMapping("/edit/{editRam}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel, @RequestParam Integer editMemory,
                              @NotNull @PathVariable RAM editRam, @NotNull Model siteModel) {
        editRam.setModel(editModel);
        editRam.setMemory(editMemory);
        if (!saveRecord(editRam)) {
            siteModel.addAttribute("errorMessage",
                    "Представлена змінювана модель оперативної пам'яті уже присутня в базі!");
            siteModel.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var RAMFilePath = "";
        try {
            RAMFilePath = saveUploadingFile(uploadingFile);
            var newRAMs = excelImporter.importFile(RAMFilePath);
            newRAMs.forEach(this::saveRecord);
            return "redirect:/ram";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(RAMFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці оперативної пам'яті!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("rams", lastOutputtedRams);
        return "ramExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delRam}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable RAM delRam) {
        ramRepo.delete(delRam);
        return "redirect:/ram";
    }

    private boolean saveRecord(RAM saveRAM) {
        try {
            ramRepo.save(saveRAM);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}