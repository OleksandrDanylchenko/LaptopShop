package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.GpuService;
import ua.alexd.domain.GPU;

@Controller
@RequestMapping("/gpu")
public class GPUController {
    private final GpuService gpuService;
    private Iterable<GPU> lastOutputtedGPUs;

    public GPUController(GpuService gpuService) {
        this.gpuService = gpuService;
    }

    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) Integer memory,
                             @NotNull Model siteModel) {
        var gpus = gpuService.loadGPUTable(model, memory);
        lastOutputtedGPUs = gpus;
        siteModel.addAttribute("gpus", gpus);
        return "view/gpu/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newGPU") GPU newGPU, @NotNull Model model) {
        var isNewGPUSaved = gpuService.addGPURecord(newGPU);
        if (!isNewGPUSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель відеокарти уже присутня в базі!");
            model.addAttribute("gpus", lastOutputtedGPUs);
            return "view/gpu/table";
        }
        return "redirect:/gpu";
    }

    @NotNull
    @PostMapping("/edit/{editGpu}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel, @RequestParam Integer editMemory,
                             @NotNull @PathVariable GPU editGpu, @NotNull Model model) {
        var isEditGPUSaved = gpuService.editGPURecord(editModel, editMemory, editGpu);
        if (!isEditGPUSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель відеокарти уже присутня в базі!");
            model.addAttribute("gpus", lastOutputtedGPUs);
            return "view/gpu/table";
        }
        return "redirect:/gpu";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = gpuService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці відеокарт!");
            model.addAttribute("gpus", lastOutputtedGPUs);
            return "view/gpu/table";
        }
        return "redirect:/gpu";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("gpus", lastOutputtedGPUs);
        return "gpuExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delGpu}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable GPU delGpu) {
        gpuService.deleteRecord(delGpu);
        return "redirect:/gpu";
    }
}