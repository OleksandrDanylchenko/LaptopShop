package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Laptop;
import ua.alexd.excelInteraction.imports.LaptopExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.HardwareRepo;
import ua.alexd.repos.LabelRepo;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.TypeRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.LaptopSpecification.*;

@Service
public class LaptopService {
    private final LaptopRepo laptopRepo;

    private final HardwareRepo hardwareRepo;
    private final TypeRepo typeRepo;
    private final LabelRepo labelRepo;

    private final LaptopExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public LaptopService(LaptopRepo laptopRepo, HardwareRepo hardwareRepo, TypeRepo typeRepo, LabelRepo labelRepo,
                         LaptopExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.laptopRepo = laptopRepo;
        this.hardwareRepo = hardwareRepo;
        this.typeRepo = typeRepo;
        this.labelRepo = labelRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Laptop> loadLaptopTable(String hardwareAssemblyName, String typeName, String labelBrand,
                                            String labelModel, Model model) {
        var laptopSpecification = createLaptopSpecification(hardwareAssemblyName, typeName, labelBrand, labelModel);
        var laptops = laptopRepo.findAll(laptopSpecification);
        initializeLaptopChoices(model);
        return laptops;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Laptop> createLaptopSpecification(String hardwareAssemblyName, String typeName,
                                                            String labelBrand, String labelModel) {
        return Specification.where(hardwareAssemblyNameLike(hardwareAssemblyName))
                .and(typeNameEqual(typeName)).and(labelBrandEqual(labelBrand)).and(labelModelLike(labelModel));
    }

    public boolean addLaptopRecord(String hardwareAssemblyName, String typeName, String labelModel, Model model) {
        initializeLaptopChoices(model);
        var hardware = hardwareRepo.findByAssemblyName(hardwareAssemblyName);
        var type = typeRepo.findByName(typeName).get(0);
        var label = labelRepo.findByModel(labelModel);
        var newLaptop = new Laptop(label, type, hardware);
        return saveRecord(newLaptop);
    }

    public boolean editLaptopRecord(String editAssemblyName, String editTypeName, String editLabelModel,
                                    @NotNull Laptop editLaptop, Model model) {
        initializeLaptopChoices(model);
        var hardware = hardwareRepo.findByAssemblyName(editAssemblyName);
        editLaptop.setHardware(hardware);
        var type = typeRepo.findByName(editTypeName).get(0);
        editLaptop.setType(type);
        var label = labelRepo.findByModel(editLabelModel);
        editLaptop.setLabel(label);
        return saveRecord(editLaptop);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initializeLaptopChoices(model);
        var laptopFilePath = "";
        try {
            laptopFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newLaptops = excelImporter.importFile(laptopFilePath);
            newLaptops.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(laptopFilePath);
            return false;
        }
    }

    private boolean saveRecord(Laptop saveLaptop) {
        try {
            laptopRepo.save(saveLaptop);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Laptop delLaptop) {
        laptopRepo.delete(delLaptop);
    }

    private void initializeLaptopChoices(@NotNull Model model) {
        model.addAttribute("hardwareAssemblyNames", hardwareRepo.getAllAssemblyNames())
                .addAttribute("typeNames", typeRepo.getAllNames())
                .addAttribute("labelModels", labelRepo.getAllModels());
    }
}