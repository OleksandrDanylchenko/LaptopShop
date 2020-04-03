package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Laptop;
import ua.alexd.excelInteraction.imports.LaptopExcelImporter;
import ua.alexd.repos.HardwareRepo;
import ua.alexd.repos.LabelRepo;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.TypeRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.LaptopSpecification.*;

@Controller
@RequestMapping("/laptop")
public class LaptopController {
    private final LaptopRepo laptopRepo;
    private static Iterable<Laptop> lastOutputtedLaptops;

    private final HardwareRepo hardwareRepo;
    private final TypeRepo typeRepo;
    private final LabelRepo labelRepo;

    private final LaptopExcelImporter excelImporter;

    public LaptopController(LaptopRepo laptopRepo, HardwareRepo hardwareRepo, TypeRepo typeRepo, LabelRepo labelRepo,
                            LaptopExcelImporter excelImporter) {
        this.laptopRepo = laptopRepo;
        this.hardwareRepo = hardwareRepo;
        this.typeRepo = typeRepo;
        this.labelRepo = labelRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String hardwareAssemblyName,
                              @RequestParam(required = false) String typeName,
                              @RequestParam(required = false) String labelBrand,
                              @RequestParam(required = false) String labelModel,
                              @NotNull Model model) {
        var laptopSpecification = Specification.where(hardwareAssemblyNameLike(hardwareAssemblyName))
                .and(typeNameEqual(typeName)).and(labelBrandEqual(labelBrand)).and(labelModelLike(labelModel));
        var laptops = laptopRepo.findAll(laptopSpecification);
        lastOutputtedLaptops = laptops;
        model.addAttribute("laptops", laptops);
        initializeDropDownChoices(model);
        return "view/laptop/table";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String hardwareAssemblyName, @RequestParam String typeName,
                             @RequestParam String labelModel, @NotNull Model model) {
        var hardware = hardwareRepo.findByAssemblyName(hardwareAssemblyName);
        var type = typeRepo.findByName(typeName).get(0);
        var label = labelRepo.findByModel(labelModel);
        var newLaptop = new Laptop(label, type, hardware);

        if (!saveRecord(newLaptop)) {
            model.addAttribute("errorMessage",
                    "Представлена нова модель ноутбука уже присутня в базі!");
            model.addAttribute("laptops", lastOutputtedLaptops);
            initializeDropDownChoices(model);
            return "view/laptop/table";
        }
        return "redirect:/laptop";
    }

    @NotNull
    @PostMapping("/edit/{editLaptop}")
    private String editRecord(@RequestParam String editAssemblyName, @RequestParam String editTypeName,
                              @RequestParam String editLabelModel, @NotNull @PathVariable Laptop editLaptop,
                              @NotNull Model model) {
        var hardware = hardwareRepo.findByAssemblyName(editAssemblyName);
        editLaptop.setHardware(hardware);
        var type = typeRepo.findByName(editTypeName).get(0);
        editLaptop.setType(type);
        var label = labelRepo.findByModel(editLabelModel);
        editLaptop.setLabel(label);

        if (!saveRecord(editLaptop)) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана модель ноутбука уже присутня в базі!");
            model.addAttribute("laptops", lastOutputtedLaptops);
            initializeDropDownChoices(model);
            return "view/laptop/table";
        }
        return "redirect:/laptop";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var laptopFilePath = "";
        try {
            laptopFilePath = saveUploadingFile(uploadingFile);
            var newLaptops = excelImporter.importFile(laptopFilePath);
            newLaptops.forEach(this::saveRecord);
            return "redirect:/laptop";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(laptopFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці ноутбуків!");
            model.addAttribute("laptops", lastOutputtedLaptops);
            initializeDropDownChoices(model);
            return "view/laptop/table";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Laptop.class.getSimpleName());
        model.addAttribute("tableName", "ноутбуків");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("laptops", lastOutputtedLaptops);
        return "laptopExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delLaptop}")
    private String deleteRecord(@NotNull @PathVariable Laptop delLaptop) {
        laptopRepo.delete(delLaptop);
        return "redirect:/laptop";
    }

    private boolean saveRecord(Laptop saveLaptop) {
        try {
            laptopRepo.save(saveLaptop);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("hardwareAssemblyNames", hardwareRepo.getAllAssemblyNames())
                .addAttribute("typeNames", typeRepo.getAllNames())
                .addAttribute("labelModels", labelRepo.getAllModels());
    }
}