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
    private String addRecord(@NotNull @ModelAttribute("newHDD") HDD newHDD,
                             @NotNull Model model) {
        if (!saveRecord(newHDD)) {
            model.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "add/hddAdd";
        }
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
        editHDD.setModel(model);
        editHDD.setMemory(memory);
        if (!saveRecord(editHDD)) {
            siteModel.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "edit/hddEdit";
        }
        return "redirect:/hdd";
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
        var HDDFilePath = "";
        try {
            HDDFilePath = saveUploadingFile(uploadingFile);
            var newHDDs = excelImporter.importFile(HDDFilePath);
            newHDDs.forEach(this::saveRecord);
            return "redirect:/hdd";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(HDDFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці HDD дисків!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
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

    private boolean saveRecord(HDD saveHDD) {
        try {
            hddRepo.save(saveHDD);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}