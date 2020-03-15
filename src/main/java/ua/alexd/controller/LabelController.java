package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Label;
import ua.alexd.excelUtils.imports.LabelExcelImporter;
import ua.alexd.repos.LabelRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.LabelSpecification.brandEqual;
import static ua.alexd.specification.LabelSpecification.modelLike;

@Controller
@RequestMapping("/label")
public class LabelController {
    private final LabelRepo labelRepo;
    private static Iterable<Label> lastOutputtedLabel;

    private final LabelExcelImporter excelImporter;

    public LabelController(LabelRepo labelRepo, LabelExcelImporter excelImporter) {
        this.labelRepo = labelRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String brand,
                              @RequestParam(required = false) String model,
                              @NotNull Model siteModel) {
        var labelSpecification = Specification.where(brandEqual(brand)).and(modelLike(model));
        var labels = labelRepo.findAll(labelSpecification);
        lastOutputtedLabel = labels;
        siteModel.addAttribute("labels", labels);
        return "/list/labelList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/labelAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@NotNull @ModelAttribute("newLabel") Label newLabel, @NotNull Model model) {
        if (!saveRecord(newLabel)) {
            model.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "add/labelAdd";
        }
        return "redirect:/label";
    }

    @NotNull
    @GetMapping("/edit/{editLabel}")
    private String editRecord(@PathVariable Label editLabel, @NotNull Model model) {
        model.addAttribute("editLabel", editLabel);
        return "/edit/labelEdit";
    }

    @NotNull
    @PostMapping("/edit/{editLabel}")
    private String editRecord(@RequestParam String brand, @RequestParam String model,
                              @NotNull @PathVariable Label editLabel, @NotNull Model siteModel) {
        editLabel.setBrand(brand);
        editLabel.setModel(model);
        if (!saveRecord(editLabel)) {
            siteModel.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "add/labelEdit";
        }
        return "redirect:/label";
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
        var labelFilePath = "";
        try {
            labelFilePath = saveUploadingFile(uploadingFile);
            var newLabels = excelImporter.importFile(labelFilePath);
            newLabels.forEach(this::saveRecord);
            return "redirect:/label";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(labelFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці найменувань!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Label.class.getSimpleName());
        model.addAttribute("tableName", "найменувань");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("labels", lastOutputtedLabel);
        return "labelExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delLabel}")
    private String deleteRecord(@NotNull @PathVariable Label delLabel) {
        labelRepo.delete(delLabel);
        return "redirect:/label";
    }

    private boolean saveRecord(Label saveLabel) {
        try {
            labelRepo.save(saveLabel);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}