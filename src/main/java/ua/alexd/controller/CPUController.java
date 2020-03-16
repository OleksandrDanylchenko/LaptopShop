package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.CPU;
import ua.alexd.excelInteraction.imports.CPUExcelImporter;
import ua.alexd.repos.CPURepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.CPUSpecification.frequencyEqual;
import static ua.alexd.specification.CPUSpecification.modelLike;

@Controller
@RequestMapping("/cpu")
public class CPUController {
    private final CPURepo cpuRepo;
    private static Iterable<CPU> lastOutputtedCPUs;

    private final CPUExcelImporter excelImporter;

    public CPUController(CPURepo cpuRepo, CPUExcelImporter excelImporter) {
        this.cpuRepo = cpuRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) String frequency,
                              @NotNull Model siteModel) {
        var cpuSpecification = Specification.where(modelLike(model)).and(frequencyEqual(frequency));
        var cpus = cpuRepo.findAll(cpuSpecification);
        lastOutputtedCPUs = cpus;
        siteModel.addAttribute("cpus", cpus);
        return "/list/cpuList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/cpuAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@NotNull @ModelAttribute("newCPU") CPU newCPU, @NotNull Model model) {
        if (!saveRecord(newCPU)) {
            model.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "add/cpuAdd";
        }
        return "redirect:/cpu";
    }

    @NotNull
    @GetMapping("/edit/{editCpu}")
    private String editRecord(@PathVariable CPU editCpu, @NotNull Model model) {
        model.addAttribute("editCpu", editCpu);
        return "/edit/cpuEdit";
    }

    @NotNull
    @PostMapping("/edit/{editCpu}")
    private String addRecord(@RequestParam String model, @RequestParam String frequency,
                             @NotNull @PathVariable CPU editCpu, @NotNull Model siteModel) {
        editCpu.setModel(model);
        editCpu.setFrequency(frequency);
        if (!saveRecord(editCpu)) {
            siteModel.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "edit/cpuEdit";
        }
        return "redirect:/cpu";
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
        var cpuFilePath = "";
        try {
            cpuFilePath = saveUploadingFile(uploadingFile);
            var newCPUs = excelImporter.importFile(cpuFilePath);
            newCPUs.forEach(this::saveRecord);
            return "redirect:/cpu";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(cpuFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці процесорів!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", CPU.class.getSimpleName());
        model.addAttribute("tableName", "процесорів");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("cpus", lastOutputtedCPUs);
        return "cpuExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delCpu}")
    private String deleteRecord(@NotNull @PathVariable CPU delCpu) {
        cpuRepo.delete(delCpu);
        return "redirect:/cpu";
    }

    private boolean saveRecord(CPU saveCPU) {
        try {
            cpuRepo.save(saveCPU);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}