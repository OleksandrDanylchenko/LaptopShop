package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.SSD;
import ua.alexd.excelInteraction.imports.SSDExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.SSDRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.SSDSpecification.memoryEqual;
import static ua.alexd.specification.SSDSpecification.modelLike;

@Service
@Lazy
public class SSDService {
    private final SSDRepo ssdRepo;

    private final SSDExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public SSDService(SSDRepo ssdRepo, SSDExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.ssdRepo = ssdRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<SSD> loadSSDTable(String model, Integer memory) {
        var ssdSpecification = createSSDSpecification(model, memory);
        return ssdRepo.findAll(ssdSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<SSD> createSSDSpecification(String model, Integer memory) {
        return Specification.where(modelLike(model)).and(memoryEqual(memory));
    }

    public boolean addSSDRecord(SSD newSSD) {
        return saveRecord(newSSD);
    }

    public boolean editSSDRecord(String model, Integer memory, @NotNull SSD editSSD) {
        editSSD.setModel(model);
        editSSD.setMemory(memory);
        return saveRecord(editSSD);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var SSDFilePath = "";
        try {
            SSDFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newSSDs = excelImporter.importFile(SSDFilePath);
            newSSDs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(SSDFilePath);
            return false;
        }
    }

    private boolean saveRecord(SSD saveSSD) {
        try {
            ssdRepo.save(saveSSD);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(SSD delSSD) {
        ssdRepo.delete(delSSD);
    }
}