package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.SSD;
import ua.alexd.repos.SSDRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.SSDExcelImport.importSSDsFromExcel;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.SSDSpecification.memoryEqual;
import static ua.alexd.specification.SSDSpecification.modelLike;

@Controller
@RequestMapping("/ssd")
public class SSDController {
    private final SSDRepo ssdRepo;
    private static Iterable<SSD> lastOutputtedSSDs;

    public SSDController(SSDRepo ssdRepo) {
        this.ssdRepo = ssdRepo;
    }

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
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull Model siteModel) {
        if (isFieldsEmpty(model, siteModel))
            return "add/ssdAdd";

        var newSSD = new SSD(model, memory);
        if (!saveRecord(newSSD, siteModel))
            return "add/ssdAdd";

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
        if (isFieldsEmpty(model, siteModel))
            return "edit/ssdEdit";

        editSSD.setModel(model);
        editSSD.setMemory(memory);
        if (!saveRecord(editSSD, siteModel))
            return "edit/ssdEdit";

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
        var uploadedFilePath = "";
        try {
            uploadedFilePath = saveUploadingFile(uploadingFile);
            var newSSDs = importSSDsFromExcel(uploadedFilePath);
            newSSDs.forEach(newSSD -> saveRecord(newSSD, model));
            return "redirect:/ssd";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(uploadedFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці SSD дисків!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", "SSD");
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

    private boolean isFieldsEmpty(String model, Model siteModel) {
        if (model == null || model.isBlank()) {
            siteModel.addAttribute("errorMessage", "Поля SSD диску не можуть бути пустими!");
            return true;
        }
        return false;
    }

    private boolean saveRecord(SSD saveSSD, Model model) {
        try {
            ssdRepo.save(saveSSD);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель дисплею " + saveSSD.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}