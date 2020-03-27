package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Display;
import ua.alexd.excelInteraction.imports.DisplayExcelImporter;
import ua.alexd.repos.DisplayRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.DisplaySpecification.*;

@Controller
@RequestMapping("/display")
public class DisplayController {
    private final DisplayRepo displayRepo;
    private Iterable<Display> lastOutputtedDisplay;

    private final DisplayExcelImporter excelImporter;

    public DisplayController(DisplayRepo displayRepo, DisplayExcelImporter excelImporter) {
        this.displayRepo = displayRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) String type,
                              @RequestParam(required = false) String diagonal,
                              @RequestParam(required = false) String resolution,
                              @NotNull Model siteModel) {
        var displaySpecification = Specification.where(modelLike(model)).and(typeEqual(type))
                .and(diagonalEqual(diagonal)).and(resolutionEqual(resolution));
        var displays = displayRepo.findAll(displaySpecification);
        lastOutputtedDisplay = displays;
        siteModel.addAttribute("displays", displays);
        return "/display/table";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@NotNull @ModelAttribute("newDisplay") Display newDisplay, @NotNull Model model) {
        if (!saveRecord(newDisplay)) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель дисплею уже присутня в базі!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "/display/table";
        }
        return "redirect:/display";
    }

    @NotNull
    @PostMapping("/edit/{editDisplay}")
    private String editRecord(@RequestParam String editModel, @RequestParam String editType,
                              @RequestParam String editDiagonal, @RequestParam String editResolution,
                              @NotNull @PathVariable Display editDisplay, @NotNull Model model) {
        editDisplay.setModel(editModel);
        editDisplay.setType(editType);
        editDisplay.setDiagonal(editDiagonal);
        editDisplay.setResolution(editResolution);
        if (!saveRecord(editDisplay)) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель дисплею уже присутня в базі!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "edit/displayEdit";
        }
        return "redirect:/display";
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
        var displayFilePath = "";
        try {
            displayFilePath = saveUploadingFile(uploadingFile);
            var newDisplays = excelImporter.importFile(displayFilePath);
            newDisplays.forEach(this::saveRecord);
            return "redirect:/display";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(displayFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці дисплеїв!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Display.class.getSimpleName());
        model.addAttribute("tableName", "дисплеїв");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("displays", lastOutputtedDisplay);
        return "displayExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delDisplay}")
    private String deleteRecord(@NotNull @PathVariable Display delDisplay) {
        displayRepo.delete(delDisplay);
        return "redirect:/display";
    }

    private boolean saveRecord(Display saveDisplay) {
        try {
            displayRepo.save(saveDisplay);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}