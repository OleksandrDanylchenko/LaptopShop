package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.CPUService;
import ua.alexd.domain.CPU;

@Controller
@RequestMapping("/cpu")
public class CPUController {
    private final CPUService cpuService;
    private Iterable<CPU> lastOutputtedCPUs;

    public CPUController(CPUService cpuService) {
        this.cpuService = cpuService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) String frequency,
                             @NotNull Model siteModel) {
        var cpus = cpuService.loadCPUTable(model, frequency);
        lastOutputtedCPUs = cpus;
        siteModel.addAttribute("cpus", cpus);
        return "view/cpu/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newCPU") CPU newCPU, @NotNull Model model) {
        var isNewCPUSaved = cpuService.addCPURecord(newCPU);
        if (!isNewCPUSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель процесору уже присутня в базі!");
            model.addAttribute("cpus", lastOutputtedCPUs);
            return "view/cpu/table";
        }
        return "redirect:/cpu";
    }

    @NotNull
    @PostMapping("/edit/{editCpu}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@RequestParam String editModel, @RequestParam String editFrequency,
                            @NotNull @PathVariable CPU editCpu, @NotNull Model model) {
        var isEditCPUSaved = cpuService.editCPURecord(editModel, editFrequency, editCpu);
        if (!isEditCPUSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель процесору уже присутня в базі!");
            model.addAttribute("cpus", lastOutputtedCPUs);
            return "view/cpu/table";
        }
        return "redirect:/cpu";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = cpuService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці процесорів!");
            model.addAttribute("cpus", lastOutputtedCPUs);
            return "view/cpu/table";
        }
        return "redirect:/cpu";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("cpus", lastOutputtedCPUs);
        return "cpuExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delCpu}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable CPU delCpu) {
        cpuService.deleteRecord(delCpu);
        return "redirect:/cpu";
    }
}