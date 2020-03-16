package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
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
public class GPUController {
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
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var gpuSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var gpus = gpuRepo.findAll(gpuSpecification);
        lastOutputtedGPUs = gpus;
        siteModel.addAttribute("gpus", gpus);
        return "/list/gpuList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/gpuAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@NotNull @ModelAttribute("newGPU") GPU newGPU, @NotNull Model model) {
        if (!saveRecord(newGPU)) {
            model.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "add/gpuAdd";
        }
        return "redirect:/gpu";
    }

    @NotNull
    @GetMapping("/edit/{editGpu}")
    private String editRecord(@PathVariable GPU editGpu, @NotNull Model model) {
        model.addAttribute("editGpu", editGpu);
        return "/edit/gpuEdit";
    }

    @NotNull
    @PostMapping("/edit/{editGpu}")
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull @PathVariable GPU editGpu, @NotNull Model siteModel) {
        editGpu.setModel(model);
        editGpu.setMemory(memory);
        if (!saveRecord(editGpu)) {
            siteModel.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "edit/gpuEdit";
        }
        return "redirect:/gpu";
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
        var GPUFilePath = "";
        try {
            GPUFilePath = saveUploadingFile(uploadingFile);
            var newGPUs = excelImporter.importFile(GPUFilePath);
            newGPUs.forEach(this::saveRecord);
            return "redirect:/gpu";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(GPUFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці відеокарт!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", GPU.class.getSimpleName());
        model.addAttribute("tableName", "відеокарт");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("gpus", lastOutputtedGPUs);
        return "gpuExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delGpu}")
    private String deleteRecord(@NotNull @PathVariable GPU delGpu) {
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