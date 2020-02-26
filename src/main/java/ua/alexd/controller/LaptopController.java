package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Laptop;
import ua.alexd.repos.HardwareRepo;
import ua.alexd.repos.LabelRepo;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.TypeRepo;

import java.util.List;

import static ua.alexd.specification.LaptopSpecification.*;

@Controller
@RequestMapping("/laptop")
public class LaptopController {
    private final LaptopRepo laptopRepo;
    private final HardwareRepo hardwareRepo;
    private final TypeRepo typeRepo;
    private final LabelRepo labelRepo;

    public LaptopController(LaptopRepo laptopRepo, HardwareRepo hardwareRepo, TypeRepo typeRepo, LabelRepo labelRepo) {
        this.laptopRepo = laptopRepo;
        this.hardwareRepo = hardwareRepo;
        this.typeRepo = typeRepo;
        this.labelRepo = labelRepo;
    }

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

        model.addAttribute("laptops", laptops);
        return "/list/laptopList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        initializeAddingChoices(model);
        return "add/laptopAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam(required = false) String hardwareAssemblyName,
                             @RequestParam(required = false) String typeName,
                             @RequestParam(required = false) String labelModel,
                             @NotNull Model model) {
        if (isFieldsEmpty(hardwareAssemblyName, typeName, labelModel, model))
            return "add/laptopAdd";

        var hardware = hardwareRepo.findByAssemblyName(hardwareAssemblyName);
        var type = typeRepo.findByName(typeName).get(0);
        var label = labelRepo.findByModel(labelModel);
        var newLaptop = new Laptop(label, type, hardware);

        if (!saveRecord(newLaptop, model))
            return "add/laptopAdd";

        return "redirect:/laptop";
    }

    @NotNull
    @GetMapping("/edit/{editLaptop}")
    private String editRecord(@PathVariable Laptop editLaptop, @NotNull Model model) {
        model.addAttribute("editLaptop", editLaptop);
        initializeEditingChoices(editLaptop, model);
        return "/edit/laptopEdit";
    }

    @NotNull
    @PostMapping("/edit/{editLaptop}")
    private String editRecord(@RequestParam(required = false) String hardwareAssemblyName,
                              @RequestParam(required = false) String typeName,
                              @RequestParam(required = false) String labelModel,
                              @PathVariable Laptop editLaptop, @NotNull Model model) {
        if (isFieldsEmpty(hardwareAssemblyName, typeName, labelModel, model))
            return "edit/labelEdit";

        var hardware = hardwareRepo.findByAssemblyName(hardwareAssemblyName);
        editLaptop.setHardware(hardware);

        var type = typeRepo.findByName(typeName).get(0);
        editLaptop.setType(type);

        var label = labelRepo.findByModel(labelModel);
        editLaptop.setLabel(label);

        if (!saveRecord(editLaptop, model))
            return "edit/laptopEdit";

        return "redirect:/laptop";
    }

    @NotNull
    @GetMapping("/delete/{delLaptop}")
    private String deleteRecord(@NotNull @PathVariable Laptop delLaptop) {
        laptopRepo.delete(delLaptop);
        return "redirect:/laptop";
    }

    private boolean isFieldsEmpty(String hardwareAssemblyName, String typeName, String labelModel, Model model) {
        if (hardwareAssemblyName == null || typeName == null || labelModel == null ||
                typeName.isBlank() || labelModel.isBlank() || hardwareAssemblyName.isBlank()) {
            model.addAttribute("errorMessage",
                    "Поля ноутбуку не можуть бути пустими!");
            initializeAddingChoices(model);
            return true;
        }
        return false;
    }

    // TODO remove after testing
    private boolean saveRecord(Laptop saveLaptop, Model model) {
        try {
            laptopRepo.save(saveLaptop);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель ноутбуку " + saveLaptop.getLabel().getModel() + " уже присутня в базі");
            initializeAddingChoices(model);
            return false;
        }
        return true;
    }

    private void initializeEditingChoices(@NotNull Laptop editLaptop, @NotNull Model model) {
        var addedLaptopModels = laptopRepo.getAllModels();
        addedLaptopModels.remove(editLaptop.getLabel().getModel());
        initializeDropDownChoices(addedLaptopModels, model);
    }

    private void initializeAddingChoices(@NotNull Model model) {
        initializeDropDownChoices(laptopRepo.getAllModels(), model);
    }

    private void initializeDropDownChoices(List<String> addedLaptopModels, @NotNull Model model) {
        var availableModels = labelRepo.getAllModels();
        availableModels.removeAll(addedLaptopModels);

        model.addAttribute("hardwareAssemblyNames", hardwareRepo.getAllAssemblyNames())
                .addAttribute("typeNames", typeRepo.getAllNames())
                .addAttribute("labelModels", availableModels);
    }
}