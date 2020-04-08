package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Display;
import ua.alexd.excelInteraction.imports.DisplayExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.DisplayRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.DisplaySpecification.*;

@Service
@Lazy
public class DisplayService {
    private final DisplayRepo displayRepo;

    private final DisplayExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public DisplayService(DisplayRepo displayRepo, DisplayExcelImporter excelImporter,
                          UploadedFilesManager filesManager) {
        this.displayRepo = displayRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Display> loadDisplayTable(String model, String type, String diagonal, String resolution) {
        var displaySpecification = createDisplaySpecification(model, type, diagonal, resolution);
        return displayRepo.findAll(displaySpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Display> createDisplaySpecification(String model, String type,
                                                              String diagonal, String resolution) {
        return Specification.where(modelLike(model)).and(typeEqual(type))
                .and(diagonalEqual(diagonal)).and(resolutionEqual(resolution));
    }

    public boolean addDisplayRecord(Display newDisplay) {
        return saveRecord(newDisplay);
    }

    public boolean editDisplayRecord(String model, String type, String diagonal,
                                     String resolution, @NotNull Display editDisplay) {
        editDisplay.setModel(model);
        editDisplay.setType(type);
        editDisplay.setDiagonal(diagonal);
        editDisplay.setResolution(resolution);
        return saveRecord(editDisplay);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var displayFilePath = "";
        try {
            displayFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newDisplays = excelImporter.importFile(displayFilePath);
            newDisplays.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(displayFilePath);
            return false;
        }
    }

    private boolean saveRecord(Display saveDisplay) {
        try {
            displayRepo.save(saveDisplay);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Display delDisplay) {
        displayRepo.delete(delDisplay);
    }

}