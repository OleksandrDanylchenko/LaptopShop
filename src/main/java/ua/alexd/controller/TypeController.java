package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Type;
import ua.alexd.excelUtils.imports.TypeExcelImporter;
import ua.alexd.repos.TypeRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.inputUtils.inputValidator.stringContainsAlphabet;

@Controller
@RequestMapping("/type")
public class TypeController {
    private final TypeRepo typeRepo;
    private static Iterable<Type> lastOutputtedTypes;

    private final TypeExcelImporter excelImporter;

    public TypeController(TypeRepo typeRepo, TypeExcelImporter excelImporter) {
        this.typeRepo = typeRepo;
        this.excelImporter = excelImporter;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String typeName, @NotNull Model model) {
        var types = typeName != null && !typeName.isEmpty()
                ? typeRepo.findByName(typeName)
                : typeRepo.findAll();
        lastOutputtedTypes = types;
        model.addAttribute("types", types);
        return "list/typeList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/typeAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String name, @NotNull Model model) {
        if (!stringContainsAlphabet(name)) {
            model.addAttribute("errorMessage", "Назва нового типу задано некоректно!");
            return "add/typeAdd";
        }

        var newType = new Type(name);
        if (!saveRecord(newType, model))
            return "add/typeAdd";

        return "redirect:/type";
    }

    @NotNull
    @GetMapping("/edit/{editType}")
    private String editRecord(@PathVariable Type editType, @NotNull Model model) {
        model.addAttribute("editType", editType);
        return "/edit/typeEdit";
    }

    @NotNull
    @PostMapping("/edit/{editType}")
    private String editRecord(@RequestParam String name, @NotNull @PathVariable Type editType, @NotNull Model model) {
        if (!stringContainsAlphabet(name)) {
            model.addAttribute("errorMessage", "Назва змінюваного типу задано некоректно!");
            return "edit/typeEdit";
        }

        editType.setName(name);
        if (!saveRecord(editType, model))
            return "edit/typeEdit";

        return "redirect:/type";
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
        var typeFilePath = "";
        try {
            typeFilePath = saveUploadingFile(uploadingFile);
            var newTypes = excelImporter.importFile(typeFilePath);
            newTypes.forEach(newType -> saveRecord(newType, model));
            return "redirect:/type";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(typeFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці типів!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Type.class.getSimpleName());
        model.addAttribute("tableName", "типів");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("types", lastOutputtedTypes);
        return "typeExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delType}")
    private String deleteRecord(@NotNull @PathVariable Type delType) {
        typeRepo.delete(delType);
        return "redirect:/type";
    }

    private boolean saveRecord(Type saveType, Model model) {
        try {
            typeRepo.save(saveType);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage", "Тип " + saveType.getName() + " уже присутній в базі");
            return false;
        }
        return true;
    }
}