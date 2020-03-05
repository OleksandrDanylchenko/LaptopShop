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

    public LabelController(LabelRepo labelRepo) {
        this.labelRepo = labelRepo;
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
    private String addRecord(@RequestParam(required = false) String brand,
                             @RequestParam(required = false) String model,
                             @NotNull Model siteModel) {
        if (isFieldsEmpty(brand, model)) {
            siteModel.addAttribute("errorMessage", "Поля нового найменування не можуть бути пустими!");
            return "add/labelAdd";
        }

        var newLabel = new Label(brand, model);
        if (!saveRecord(newLabel, siteModel))
            return "add/labelAdd";

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
        if (isFieldsEmpty(brand, model)) {
            siteModel.addAttribute("errorMessage", "Поля змінюваного найменування не можуть бути пустими!");
            return "edit/labelEdit";
        }

        editLabel.setBrand(brand);
        editLabel.setModel(model);
        if (!saveRecord(editLabel, siteModel))
            return "add/labelEdit";

        return "redirect:/label";
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
        var labelFilePath = "";
        try {
            labelFilePath = saveUploadingFile(uploadingFile);
            var newLabels = new LabelExcelImporter().importFile(labelFilePath);
            newLabels.forEach(newType -> saveRecord(newType, model));
            return "redirect:/label";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(labelFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці найменувань!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
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

    public static boolean isFieldsEmpty(String brand, String model) {
        return brand == null || model == null ||
                brand.isBlank() || model.isBlank();
    }

    private boolean saveRecord(Label saveLabel, Model model) {
        try {
            labelRepo.save(saveLabel);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель " + saveLabel.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}