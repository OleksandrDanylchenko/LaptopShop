package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.GPU;
import ua.alexd.excelInteraction.imports.GPUExcelImporter;
import ua.alexd.repos.GPURepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.GPUSpecification.memoryEqual;
import static ua.alexd.specification.GPUSpecification.modelLike;

@Controller
@RequestMapping("/gpu")
public final class GPUController {
    private final GPURepo gpuRepo;
    private static Iterable<GPU> lastOutputtedGPUs;

    private final GPUExcelImporter excelImporter;

    public GPUController(GPURepo gpuRepo, GPUExcelImporter excelImporter) {
        this.gpuRepo = gpuRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var gpuSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var gpus = gpuRepo.findAll(gpuSpecification);
        lastOutputtedGPUs = gpus;
        siteModel.addAttribute("gpus", gpus);
        return "view/gpu/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newGPU") GPU newGPU, @NotNull Model model) {
        if (!saveRecord(newGPU)) {
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
        editGpu.setModel(editModel);
        editGpu.setMemory(editMemory);
        if (!saveRecord(editGpu)) {
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
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var GPUFilePath = "";
        try {
            GPUFilePath = saveUploadingFile(uploadingFile);
            var newGPUs = excelImporter.importFile(GPUFilePath);
            newGPUs.forEach(this::saveRecord);
            return "redirect:/gpu";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(GPUFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці відеокарт!");
            model.addAttribute("gpus", lastOutputtedGPUs);
            return "view/gpu/table";
        }
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
        gpuRepo.delete(delGpu);
        return "redirect:/gpu";
    }

    private boolean saveRecord(GPU saveGpu) {
        try {
            gpuRepo.save(saveGpu);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}