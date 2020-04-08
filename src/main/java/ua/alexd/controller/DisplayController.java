package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.DisplayService;
import ua.alexd.domain.Display;

@Controller
@RequestMapping("/display")
public class DisplayController {
    private final DisplayService displayService;
    private Iterable<Display> lastOutputtedDisplay;

    public DisplayController(DisplayService displayService) {
        this.displayService = displayService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) String type,
                             @RequestParam(required = false) String diagonal,
                             @RequestParam(required = false) String resolution,
                             @NotNull Model siteModel) {
        var displays = displayService.loadDisplayTable(model, type, diagonal, resolution);
        lastOutputtedDisplay = displays;
        siteModel.addAttribute("displays", displays);
        return "view/display/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newDisplay") Display newDisplay, @NotNull Model model) {
        var isNewDisplaySaved = displayService.addDisplayRecord(newDisplay);
        if (!isNewDisplaySaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель дисплею уже присутня в базі!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "view/display/table";
        }
        return "redirect:/display";
    }

    @NotNull
    @PostMapping("/edit/{editDisplay}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel, @RequestParam String editType,
                             @RequestParam String editDiagonal, @RequestParam String editResolution,
                             @NotNull @PathVariable Display editDisplay, @NotNull Model model) {
        var isEditDisplaySaved = displayService.editDisplayRecord(editModel, editType, editDiagonal, editResolution, editDisplay);
        if (!isEditDisplaySaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель дисплею уже присутня в базі!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "view/display/table";
        }
        return "redirect:/display";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isEditDisplaySaved = displayService.importExcelRecords(uploadingFile);
        if (!isEditDisplaySaved) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці дисплеїв!");
            model.addAttribute("displays", lastOutputtedDisplay);
            return "view/display/table";
        }
        return "redirect:/display";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("displays", lastOutputtedDisplay);
        return "displayExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delDisplay}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable Display delDisplay) {
        displayService.deleteRecord(delDisplay);
        return "redirect:/display";
    }


}