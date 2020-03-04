package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.RAM;
import ua.alexd.repos.RAMRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.RAMExcelImporter.importRAMsFromExcel;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.RAMSpecification.memoryEqual;
import static ua.alexd.specification.RAMSpecification.modelLike;

@Controller
@RequestMapping("/ram")
public class RAMController {
    private final RAMRepo ramRepo;
    private static Iterable<RAM> lastOutputtedRams;

    public RAMController(RAMRepo ramRepo) {
        this.ramRepo = ramRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var ramSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var rams = ramRepo.findAll(ramSpecification);
        lastOutputtedRams = rams;
        siteModel.addAttribute("rams", rams);
        return "/list/ramList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/ramAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull Model siteModel) {
        if (isFieldsEmpty(model, siteModel))
            return "add/ramAdd";

        var newRam = new RAM(model, memory);
        if (!saveRecord(newRam, siteModel))
            return "add/ramAdd";

        return "redirect:/ram";
    }

    @NotNull
    @GetMapping("/edit/{editRam}")
    private String editRecord(@PathVariable RAM editRam, @NotNull Model model) {
        model.addAttribute("editRam", editRam);
        return "/edit/ramEdit";
    }

    @NotNull
    @PostMapping("/edit/{editRam}")
    private String editRecord(@RequestParam String model, @RequestParam Integer memory,
                              @NotNull @PathVariable RAM editRam, @NotNull Model siteModel) {
        if (isFieldsEmpty(model, siteModel))
            return "edit/ramEdit";

        editRam.setModel(model);
        editRam.setMemory(memory);
        if (!saveRecord(editRam, siteModel))
            return "edit/ramEdit";

        return "redirect:/ram";
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
            var newRAMs = importRAMsFromExcel(uploadedFilePath);
            newRAMs.forEach(newRAM -> saveRecord(newRAM, model));
            return "redirect:/ram";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(uploadedFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці оперативної пам'яті!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", "RAM");
        model.addAttribute("tableName", "оперативної пам'яті");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("rams", lastOutputtedRams);
        return "ramExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delRam}")
    private String deleteRecord(@NotNull @PathVariable RAM delRam) {
        ramRepo.delete(delRam);
        return "redirect:/ram";
    }

    private boolean isFieldsEmpty(String model, Model siteModel) {
        if (model == null || model.isBlank()) {
            siteModel.addAttribute("errorMessage",
                    "Поля оперативної пам'яті не можуть бути пустими!");
            return true;
        }
        return false;
    }

    private boolean saveRecord(RAM saveRAM, Model model) {
        try {
            ramRepo.save(saveRAM);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель оперативної пам'яті " + saveRAM.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}