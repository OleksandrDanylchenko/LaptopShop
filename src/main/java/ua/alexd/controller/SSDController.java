package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.SSD;
import ua.alexd.excelInteraction.imports.SSDExcelImporter;
import ua.alexd.repos.SSDRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
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
    @PreAuthorize("isAuthenticated()")
    public String getRecords(@RequestParam(required = false) String model,
                             @RequestParam(required = false) Integer memory,
                             @NotNull Model siteModel) {
        var ssdSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var ssds = ssdRepo.findAll(ssdSpecification);
        lastOutputtedSSDs = ssds;
        siteModel.addAttribute("ssds", ssds);
        return "view/ssd/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newSSD") SSD newSSD, @NotNull Model model) {
        if (!saveRecord(newSSD)) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель SSD диску уже присутня в базі!");
            model.addAttribute("ssds", lastOutputtedSSDs);
            return "view/ssd/table";
        }
        return "redirect:/ssd";
    }

    @NotNull
    @PostMapping("/edit/{editSSD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editModel, @RequestParam Integer editMemory,
                             @NotNull @PathVariable SSD editSSD, @NotNull Model siteModel) {
        editSSD.setModel(editModel);
        editSSD.setMemory(editMemory);
        if (!saveRecord(editSSD)) {
            siteModel.addAttribute("errorMessage",
                    "Представлена змінювана модель SSD диску уже присутня в базі!");
            siteModel.addAttribute("ssds", lastOutputtedSSDs);
            return "view/ssd/table";
        }
        return "redirect:/ssd";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var SSDFilePath = "";
        try {
            SSDFilePath = saveUploadingFile(uploadingFile);
            var newSSDs = excelImporter.importFile(SSDFilePath);
            newSSDs.forEach(this::saveRecord);
            return "redirect:/ssd";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(SSDFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці SSD дисків!");
            model.addAttribute("ssds", lastOutputtedSSDs);
            return "view/ssd/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("ssds", lastOutputtedSSDs);
        return "ssdExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delSSD}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable SSD delSSD) {
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