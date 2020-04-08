package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.HDD;
import ua.alexd.excelInteraction.imports.HDDExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.HDDRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.HDDSpecification.memoryEqual;
import static ua.alexd.specification.HDDSpecification.modelLike;

@Controller
@RequestMapping("/hdd")
public class HDDController {
    private final HDDRepo hddRepo;
    private static Iterable<HDD> lastOutputtedHDDs;

    private final HDDExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public HDDController(HDDRepo hddRepo, HDDExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.hddRepo = hddRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) Integer memory,
                             @NotNull Model siteModel) {
        var hddSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var hdds = hddRepo.findAll(hddSpecification);
        lastOutputtedHDDs = hdds;
        siteModel.addAttribute("hdds", hdds);
        return "view/hdd/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newHDD") HDD newHDD,
                            @NotNull Model model) {
        if (!saveRecord(newHDD)) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель HDD диску уже присутня в базі!");
            model.addAttribute("hdds", lastOutputtedHDDs);
            return "view/hdd/table";
        }
        return "redirect:/hdd";
    }

    @NotNull
    @PostMapping("/edit/{editHDD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel, @RequestParam Integer editMemory,
                             @NotNull @PathVariable HDD editHDD, @NotNull Model model) {
        editHDD.setModel(editModel);
        editHDD.setMemory(editMemory);
        if (!saveRecord(editHDD)) {
            model.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            model.addAttribute("hdds", lastOutputtedHDDs);
            return "view/hdd/table";
        }
        return "redirect:/hdd";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var HDDFilePath = "";
        try {
            HDDFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newHDDs = excelImporter.importFile(HDDFilePath);
            newHDDs.forEach(this::saveRecord);
            return "redirect:/hdd";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(HDDFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці HDD дисків!");
            model.addAttribute("hdds", lastOutputtedHDDs);
            return "view/hdd/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("hdds", lastOutputtedHDDs);
        return "hddExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delHDD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable HDD delHDD) {
        hddRepo.delete(delHDD);
        return "redirect:/hdd";
    }

    private boolean saveRecord(HDD saveHDD) {
        try {
            hddRepo.save(saveHDD);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}