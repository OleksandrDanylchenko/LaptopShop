package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.SSD;
import ua.alexd.excelUtils.imports.SSDExcelImporter;
import ua.alexd.repos.SSDRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.SSDSpecification.memoryEqual;
import static ua.alexd.specification.SSDSpecification.modelLike;

@Controller
@RequestMapping("/ssd")
public class SSDController {
    private final SSDRepo ssdRepo;
    private static Iterable<SSD> lastOutputtedSSDs;

    private final SSDExcelImporter excelImporter;

    public SSDController(SSDRepo ssdRepo, SSDExcelImporter excelImporter) {
        this.ssdRepo = ssdRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var ssdSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var ssds = ssdRepo.findAll(ssdSpecification);
        lastOutputtedSSDs = ssds;
        siteModel.addAttribute("ssds", ssds);
        return "/list/ssdList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/ssdAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@NotNull @ModelAttribute("newSSD") SSD newSSD,
                             @NotNull Model model) {
        if (!saveRecord(newSSD)) {
            model.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "add/ssdAdd";
        }
        return "redirect:/ssd";
    }

    @NotNull
    @GetMapping("/edit/{editSSD}")
    private String editRecord(@PathVariable SSD editSSD, @NotNull Model model) {
        model.addAttribute("editSSD", editSSD);
        return "/edit/ssdEdit";
    }

    @NotNull
    @PostMapping("/edit/{editSSD}")
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull @PathVariable SSD editSSD, @NotNull Model siteModel) {
        editSSD.setModel(model);
        editSSD.setMemory(memory);
        if (!saveRecord(editSSD)) {
            siteModel.addAttribute("errorMessage", "Представлена модель уже присутня в базі!");
            return "edit/ssdEdit";
        }
        return "redirect:/ssd";
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
        var SSDFilePath = "";
        try {
            SSDFilePath = saveUploadingFile(uploadingFile);
            var newSSDs = excelImporter.importFile(SSDFilePath);
            newSSDs.forEach(this::saveRecord);
            return "redirect:/ssd";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(SSDFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці SSD дисків!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", SSD.class.getSimpleName());
        model.addAttribute("tableName", "SSD дисків");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("ssds", lastOutputtedSSDs);
        return "ssdExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delSSD}")
    private String deleteRecord(@NotNull @PathVariable SSD delSSD) {
        ssdRepo.delete(delSSD);
        return "redirect:/ssd";
    }

    private boolean saveRecord(SSD saveSSD) {
        try {
            ssdRepo.save(saveSSD);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}