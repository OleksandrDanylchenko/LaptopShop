package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.LaptopService;
import ua.alexd.domain.Laptop;

@Controller
@RequestMapping("/laptop")
public class LaptopController {
    private final LaptopService laptopService;
    private Iterable<Laptop> lastOutputtedLaptops;

    public LaptopController(LaptopService laptopService) {
        this.laptopService = laptopService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String hardwareAssemblyName,
                             @RequestParam(required = false) String typeName,
                             @RequestParam(required = false) String labelBrand,
                             @RequestParam(required = false) String labelModel,
                             @NotNull Model model) {
        var laptops = laptopService.loadLaptopTable(hardwareAssemblyName, typeName, labelBrand, labelModel, model);
        lastOutputtedLaptops = laptops;
        model.addAttribute("laptops", laptops);
        return "view/laptop/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@RequestParam String hardwareAssemblyName, @RequestParam String typeName,
                            @RequestParam String labelModel, @NotNull Model model) {
        var isNewLaptopSaved = laptopService.addLaptopRecord(hardwareAssemblyName, typeName, labelModel, model);
        if (!isNewLaptopSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель ноутбука уже присутня в базі!");
            model.addAttribute("laptops", lastOutputtedLaptops);
            return "view/laptop/table";
        }
        return "redirect:/laptop";
    }

    @NotNull
    @PostMapping("/edit/{editLaptop}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editAssemblyName, @RequestParam String editTypeName,
                             @RequestParam String editLabelModel, @NotNull @PathVariable Laptop editLaptop,
                             @NotNull Model model) {
        var isEditLaptopSaved = laptopService.editLaptopRecord(editAssemblyName, editTypeName, editLabelModel,
                editLaptop, model);
        if (!isEditLaptopSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель ноутбука уже присутня в базі!");
            model.addAttribute("laptops", lastOutputtedLaptops);
            return "view/laptop/table";
        }
        return "redirect:/laptop";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = laptopService.importExcelRecords(uploadingFile, model);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці ноутбуків!");
            model.addAttribute("laptops", lastOutputtedLaptops);
            return "view/laptop/table";
        }
        return "redirect:/laptop";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("laptops", lastOutputtedLaptops);
        return "laptopExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delLaptop}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable Laptop delLaptop) {
        laptopService.deleteRecord(delLaptop);
        return "redirect:/laptop";
    }
}