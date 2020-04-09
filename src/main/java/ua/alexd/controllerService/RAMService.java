package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.RAM;
import ua.alexd.excelInteraction.imports.RAMExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.RAMRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.RAMSpecification.memoryEqual;
import static ua.alexd.specification.RAMSpecification.modelLike;

@Service
@Lazy
public class RAMService {
    private final RAMRepo ramRepo;

    private final RAMExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public RAMService(RAMRepo ramRepo, RAMExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.ramRepo = ramRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<RAM> loadRAMTable(String model, Integer memory) {
        var ramSpecification = createRAMSpecification(model, memory);
        return ramRepo.findAll(ramSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<RAM> createRAMSpecification(String model, Integer memory) {
        return Specification.where(modelLike(model)).and(memoryEqual(memory));
    }

    public boolean addRAMRecord(RAM newRAM) {
        return saveRecord(newRAM);
    }

    public boolean editRANRecord(String editModel, Integer editMemory, @NotNull RAM editRam) {
        editRam.setModel(editModel);
        editRam.setMemory(editMemory);
        return saveRecord(editRam);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var RAMFilePath = "";
        try {
            RAMFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newRAMs = excelImporter.importFile(RAMFilePath);
            newRAMs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(RAMFilePath);
            return false;
        }
    }

    private boolean saveRecord(RAM saveRAM) {
        try {
            ramRepo.save(saveRAM);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(RAM delRam) {
        ramRepo.delete(delRam);
    }
}