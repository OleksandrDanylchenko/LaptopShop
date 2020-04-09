package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.HDD;
import ua.alexd.excelInteraction.imports.HDDExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.HDDRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.HDDSpecification.memoryEqual;
import static ua.alexd.specification.HDDSpecification.modelLike;

@Service
@Lazy
public class HDDService {
    private final HDDRepo hddRepo;

    private final HDDExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public HDDService(HDDRepo hddRepo, HDDExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.hddRepo = hddRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<HDD> loadHDDTable(String model, Integer memory) {
        var hddSpecification = createHDDSpecification(model, memory);
        return hddRepo.findAll(hddSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<HDD> createHDDSpecification(String model, Integer memory) {
        return Specification.where(modelLike(model)).and(memoryEqual(memory));
    }

    public boolean addHDDRecord(HDD newHDD) {
        return saveRecord(newHDD);
    }

    public boolean editHDDRecord(String model, Integer memory, @NotNull HDD editHDD) {
        editHDD.setModel(model);
        editHDD.setMemory(memory);
        return saveRecord(editHDD);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var HDDFilePath = "";
        try {
            HDDFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newHDDs = excelImporter.importFile(HDDFilePath);
            newHDDs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(HDDFilePath);
            return false;
        }
    }

    private boolean saveRecord(HDD saveHDD) {
        try {
            hddRepo.save(saveHDD);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(HDD delHDD) {
        hddRepo.delete(delHDD);
    }
}