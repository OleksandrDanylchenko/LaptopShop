package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.GPU;
import ua.alexd.excelUtils.imports.GPUExcelImporter;
import ua.alexd.repos.GPURepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.inputUtils.inputValidator.stringContainsAlphabet;
import static ua.alexd.specification.GPUSpecification.memoryEqual;
import static ua.alexd.specification.GPUSpecification.modelLike;

@Controller
@RequestMapping("/gpu")
public class GPUController {
    private final GPURepo gpuRepo;
    private static Iterable<GPU> lastOutputtedGPUs;

    public GPUController(GPURepo gpuRepo) {
        this.gpuRepo = gpuRepo;
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
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull Model siteModel) {
        if (!stringContainsAlphabet(model)) {
            siteModel.addAttribute("errorMessage", "Модель нової відеокарти задано некоректно!");
            return "add/gpuAdd";
        }

        var newGpu = new GPU(model, memory);
        if (!saveRecord(newGpu, siteModel))
            return "add/gpuAdd";

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
        if (!stringContainsAlphabet(model)) {
            siteModel.addAttribute("errorMessage", "Модель змінюваної відеокарти задано некоректно!");
            return "edit/gpuEdit";
        }

        editGpu.setModel(model);
        editGpu.setMemory(memory);
        if (!saveRecord(editGpu, siteModel))
            return "edit/gpuEdit";

        return "redirect:/gpu";
    }

    @NotNull
    @GetMapping("/importExcel")
    private String importExcel(@NotNull Model model) {
        initializeImportAttributes(model);
        return "parts/excelFilesUpload";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var GPUFilePath = "";
        try {
            GPUFilePath = saveUploadingFile(uploadingFile);
            var newGPUs = new GPUExcelImporter().importFile(GPUFilePath);
            newGPUs.forEach(newType -> saveRecord(newType, model));
            return "redirect:/gpu";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(GPUFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці відеокарт!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
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

    private boolean saveRecord(GPU saveGpu, Model model) {
        try {
            gpuRepo.save(saveGpu);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage", "Модель відеокарти " + saveGpu.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}