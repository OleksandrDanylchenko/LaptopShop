package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.HDDService;
import ua.alexd.domain.HDD;

@Controller
@RequestMapping("/hdd")
public class HDDController {
    private final HDDService hddService;
    private Iterable<HDD> lastOutputtedHDDs;

    public HDDController(HDDService hddService) {
        this.hddService = hddService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) Integer memory,
                             @NotNull Model siteModel) {
        var hdds = hddService.loadHDDTable(model, memory);
        lastOutputtedHDDs = hdds;
        siteModel.addAttribute("hdds", hdds);
        return "view/hdd/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newHDD") HDD newHDD,
                            @NotNull Model model) {
        var isNewHDDSaved = hddService.addHDDRecord(newHDD);
        if (!isNewHDDSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель HDD диску уже присутня в базі!");
            model.addAttribute("hdds", lastOutputtedHDDs);
            return "view/hdd/table";
        }
        return "redirect:/hdd";
    }

    @NotNull
    @PostMapping("/edit/{editHDD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel, @RequestParam Integer editMemory,
                             @NotNull @PathVariable HDD editHDD, @NotNull Model model) {
        var isEditHDDSaved = hddService.editHDDRecord(editModel, editMemory, editHDD);
        if (!isEditHDDSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель HDD диску уже присутня в базі!");
            model.addAttribute("hdds", lastOutputtedHDDs);
            return "view/hdd/table";
        }
        return "redirect:/hdd";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = hddService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці HDD дисків!");
            model.addAttribute("hdds", lastOutputtedHDDs);
            return "view/hdd/table";
        }
        return "redirect:/hdd";
    }


    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("hdds", lastOutputtedHDDs);
        return "hddExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delHDD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable HDD delHDD) {
        hddService.deleteRecord(delHDD);
        return "redirect:/hdd";
    }
}