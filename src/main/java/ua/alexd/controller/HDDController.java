package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.HDD;
import ua.alexd.excelUtils.imports.HDDExcelImporter;
import ua.alexd.repos.HDDRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.HDDSpecification.memoryEqual;
import static ua.alexd.specification.HDDSpecification.modelLike;

@Controller
@RequestMapping("/hdd")
public class HDDController {
    private final HDDRepo hddRepo;
    private static Iterable<HDD> lastOutputtedHDDs;

    public HDDController(HDDRepo hddRepo) {
        this.hddRepo = hddRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var hddSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var hdds = hddRepo.findAll(hddSpecification);
        lastOutputtedHDDs = hdds;
        siteModel.addAttribute("hdds", hdds);
        return "/list/hddList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/hddAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull Model siteModel) {
        if (isFieldsEmpty(model, siteModel))
            return "add/hddAdd";

        var newHDD = new HDD(model, memory);
        if (!saveRecord(newHDD, siteModel))
            return "add/hddAdd";

        return "redirect:/hdd";
    }

    @NotNull
    @GetMapping("/edit/{editHDD}")
    private String editRecord(@PathVariable HDD editHDD, @NotNull Model model) {
        model.addAttribute("editHDD", editHDD);
        return "/edit/hddEdit";
    }

    @NotNull
    @PostMapping("/edit/{editHDD}")
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull @PathVariable HDD editHDD, @NotNull Model siteModel) {
        if (isFieldsEmpty(model, siteModel))
            return "edit/hddEdit";

        editHDD.setModel(model);
        editHDD.setMemory(memory);
        if (!saveRecord(editHDD, siteModel))
            return "edit/hddEdit";

        return "redirect:/hdd";
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
            var newHDDs = HDDExcelImporter.importFile(uploadedFilePath);
            newHDDs.forEach(newType -> saveRecord(newType, model));
            return "redirect:/hdd";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(uploadedFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", "HDD");
        model.addAttribute("tableName", "HDD дисків");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("hdds", lastOutputtedHDDs);
        return "hddExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delHDD}")
    private String deleteRecord(@NotNull @PathVariable HDD delHDD) {
        hddRepo.delete(delHDD);
        return "redirect:/hdd";
    }

    private boolean isFieldsEmpty(String model, Model siteModel) {
        if (model == null || model.isBlank()) {
            siteModel.addAttribute("errorMessage", "Поля HDD диску не можуть бути пустими!");
            return true;
        }
        return false;
    }

    private boolean saveRecord(HDD saveHDD, Model model) {
        try {
            hddRepo.save(saveHDD);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель HDD диску " + saveHDD.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}