package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.RAM;
import ua.alexd.excelUtils.imports.RAMExcelImporter;
import ua.alexd.repos.RAMRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
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
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var ramSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var rams = ramRepo.findAll(ramSpecification);
        lastOutputtedRams = rams;
        siteModel.addAttribute("rams", rams);
        return "/list/ramList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/ramAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@NotNull @ModelAttribute("newRAM") RAM newRAM, @NotNull Model model) {
        if (!saveRecord(newRAM)) {
            model.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "add/ramAdd";
        }
        return "redirect:/ram";
    }

    @NotNull
    @GetMapping("/edit/{editRam}")
    private String editRecord(@PathVariable RAM editRam, @NotNull Model model) {
        model.addAttribute("editRam", editRam);
        return "/edit/ramEdit";
    }

    @NotNull
    @PostMapping("/edit/{editRam}")
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull @PathVariable RAM editRam, @NotNull Model siteModel) {
        editRam.setModel(model);
        editRam.setMemory(memory);
        if (!saveRecord(editRam)) {
            siteModel.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "edit/ramEdit";
        }
        return "redirect:/ram";
    }

    @NotNull
    @GetMapping("/importExcel")
    private String importExcel(@NotNull Model model) {
        initializeImportAttributes(model);
        return "excel/excelFilesUpload";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var RAMFilePath = "";
        try {
            RAMFilePath = saveUploadingFile(uploadingFile);
            var newRAMs = excelImporter.importFile(RAMFilePath);
            newRAMs.forEach(this::saveRecord);
            return "redirect:/ram";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(RAMFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці оперативної пам'яті!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", RAM.class.getSimpleName());
        model.addAttribute("tableName", "оперативної пам'яті");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("rams", lastOutputtedRams);
        return "ramExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delRam}")
    private String deleteRecord(@NotNull @PathVariable RAM delRam) {
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