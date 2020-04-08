package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Label;
import ua.alexd.excelInteraction.imports.LabelExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.LabelRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.LabelSpecification.brandEqual;
import static ua.alexd.specification.LabelSpecification.modelLike;

@Controller
@RequestMapping("/label")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class LabelController {
    private final LabelRepo labelRepo;
    private static Iterable<Label> lastOutputtedLabel;

    private final LabelExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public LabelController(LabelRepo labelRepo, LabelExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.labelRepo = labelRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String brand,
                             @RequestParam(required = false) String model,
                             @NotNull Model siteModel) {
        var labelSpecification = Specification.where(brandEqual(brand)).and(modelLike(model));
        var labels = labelRepo.findAll(labelSpecification);
        lastOutputtedLabel = labels;
        siteModel.addAttribute("labels", labels);
        return "view/label/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@NotNull @ModelAttribute("newLabel") Label newLabel, @NotNull Model model) {
        if (!saveRecord(newLabel)) {
            model.addAttribute("errorMessage", "Представлена нова модель уже присутня в базі!");
            model.addAttribute("labels", lastOutputtedLabel);
            return "view/label/table";
        }
        return "redirect:/label";
    }

    @NotNull
    @PostMapping("/edit/{editLabel}")
    public String editRecord(@RequestParam String editBrand, @RequestParam String editModel,
                             @NotNull @PathVariable Label editLabel, @NotNull Model model) {
        editLabel.setBrand(editBrand);
        editLabel.setModel(editModel);
        if (!saveRecord(editLabel)) {
            model.addAttribute("errorMessage", "Представлена змінювана модель уже присутня в базі!");
            model.addAttribute("labels", lastOutputtedLabel);
            return "view/label/table";
        }
        return "redirect:/label";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var labelFilePath = "";
        try {
            labelFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newLabels = excelImporter.importFile(labelFilePath);
            newLabels.forEach(this::saveRecord);
            return "redirect:/label";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(labelFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці найменувань!");
            model.addAttribute("labels", lastOutputtedLabel);
            return "view/label/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("labels", lastOutputtedLabel);
        return "labelExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delLabel}")
    public String deleteRecord(@NotNull @PathVariable Label delLabel) {
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