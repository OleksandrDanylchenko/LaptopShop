package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.RAMService;
import ua.alexd.domain.RAM;

@Controller
@RequestMapping("/ram")
public class RAMController {
    private final RAMService ramService;
    private Iterable<RAM> lastOutputtedRams;

    public RAMController(RAMService ramService) {
        this.ramService = ramService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) Integer memory,
                             @NotNull Model siteModel) {
        var rams = ramService.loadRAMTable(model, memory);
        lastOutputtedRams = rams;
        siteModel.addAttribute("rams", rams);
        return "view/ram/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newRAM") RAM newRAM, @NotNull Model model) {
        var isNewRAMSaved = ramService.addRAMRecord(newRAM);
        if (!isNewRAMSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель оперативної пам'яті уже присутня в базі!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @PostMapping("/edit/{editRam}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel, @RequestParam Integer editMemory,
                             @NotNull @PathVariable RAM editRam, @NotNull Model model) {
        var isEditRAMSaved = ramService.editRANRecord(editModel, editMemory, editRam, model);
        if (!isEditRAMSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель оперативної пам'яті уже присутня в базі!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = ramService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці оперативної пам'яті!");
            model.addAttribute("rams", lastOutputtedRams);
            return "view/ram/table";
        }
        return "redirect:/ram";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("rams", lastOutputtedRams);
        return "ramExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delRam}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable RAM delRam) {
        ramService.deleteRecord(delRam);
        return "redirect:/ram";
    }
}