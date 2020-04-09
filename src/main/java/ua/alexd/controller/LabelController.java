package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.LabelService;
import ua.alexd.domain.Label;

@Controller
@RequestMapping("/label")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class LabelController {
    private final LabelService labelService;
    private Iterable<Label> lastOutputtedLabel;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String brand,
                             @RequestParam(required = false) String model,
                             @NotNull Model siteModel) {
        var labels = labelService.loadLabelTable(brand, model);
        lastOutputtedLabel = labels;
        siteModel.addAttribute("labels", labels);
        return "view/label/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@NotNull @ModelAttribute("newLabel") Label newLabel, @NotNull Model model) {
        var isNewLabelSaved = labelService.addLabelRecord(newLabel);
        if (!isNewLabelSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель уже присутня в базі!");
            model.addAttribute("labels", lastOutputtedLabel);
            return "view/label/table";
        }
        return "redirect:/label";
    }

    @NotNull
    @PostMapping("/edit/{editLabel}")
    public String editRecord(@RequestParam String editBrand, @RequestParam String editModel,
                             @NotNull @PathVariable Label editLabel, @NotNull Model model) {
        var isEditLabelSaved = labelService.editLabelRecord(editBrand, editModel, editLabel);
        if (!isEditLabelSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель уже присутня в базі!");
            model.addAttribute("labels", lastOutputtedLabel);
            return "view/label/table";
        }
        return "redirect:/label";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = labelService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці найменувань!");
            model.addAttribute("labels", lastOutputtedLabel);
            return "view/label/table";
        }
        return "redirect:/label";
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
        labelService.deleteRecord(delLabel);
        return "redirect:/label";
    }
}