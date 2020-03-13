package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.HDD;
import ua.alexd.excelUtils.imports.HDDExcelImporter;
import ua.alexd.repos.HDDRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.inputUtils.inputValidator.stringContainsAlphabet;
import static ua.alexd.specification.HDDSpecification.memoryEqual;
import static ua.alexd.specification.HDDSpecification.modelLike;

@Controller
@RequestMapping("/hdd")
public class HDDController {
    private final HDDRepo hddRepo;
    private static Iterable<HDD> lastOutputtedHDDs;

    private final HDDExcelImporter excelImporter;

    public HDDController(HDDRepo hddRepo, HDDExcelImporter excelImporter) {
        this.hddRepo = hddRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var hddSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var hdds = hddRepo.findAll(hddSpecification);
        lastOutputtedHDDs = hdds;
        siteModel.addAttribute("hdds", hdds);
        return "/list/hddList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/hddAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull Model siteModel) {
        if (!stringContainsAlphabet(model)) {
            siteModel.addAttribute("errorMessage", "Модель нового HDD диску задано некоректно!");
            return "add/hddAdd";
        }

        var newHDD = new HDD(model, memory);
        if (!saveRecord(newHDD, siteModel))
            return "add/hddAdd";

        return "redirect:/hdd";
    }

    @NotNull
    @GetMapping("/edit/{editHDD}")
    private String editRecord(@PathVariable HDD editHDD, @NotNull Model model) {
        model.addAttribute("editHDD", editHDD);
        return "/edit/hddEdit";
    }

    @NotNull
    @PostMapping("/edit/{editHDD}")
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull @PathVariable HDD editHDD, @NotNull Model siteModel) {
        if (!stringContainsAlphabet(model)) {
            siteModel.addAttribute("errorMessage", "Модель змінюваного HDD диску задано некоректно!");
            return "edit/hddEdit";
        }

        editHDD.setModel(model);
        editHDD.setMemory(memory);
        if (!saveRecord(editHDD, siteModel))
            return "edit/hddEdit";

        return "redirect:/hdd";
    }

    @NotNull
    @GetMapping("/importExcel")
    private String importExcel(@NotNull Model model) {
        initializeImportAttributes(model);
        return "parts/excelFilesUpload";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var HDDFilePath = "";
        try {
            HDDFilePath = saveUploadingFile(uploadingFile);
            var newHDDs = excelImporter.importFile(HDDFilePath);
            newHDDs.forEach(newType -> saveRecord(newType, model));
            return "redirect:/hdd";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(HDDFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці HDD дисків!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", HDD.class.getSimpleName());
        model.addAttribute("tableName", "HDD дисків");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("hdds", lastOutputtedHDDs);
        return "hddExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delHDD}")
    private String deleteRecord(@NotNull @PathVariable HDD delHDD) {
        hddRepo.delete(delHDD);
        return "redirect:/hdd";
    }

    private boolean saveRecord(HDD saveHDD, Model model) {
        try {
            hddRepo.save(saveHDD);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель HDD диску " + saveHDD.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}