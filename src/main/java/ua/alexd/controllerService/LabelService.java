package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Label;
import ua.alexd.excelInteraction.imports.LabelExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.LabelRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.LabelSpecification.brandEqual;
import static ua.alexd.specification.LabelSpecification.modelLike;

@Service
@Lazy
public class LabelService {
    private final LabelRepo labelRepo;

    private final LabelExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public LabelService(LabelRepo labelRepo, LabelExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.labelRepo = labelRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Label> loadLabelTable(String brand, String model) {
        var labelSpecification = createLabelSpecification(brand, model);
        return labelRepo.findAll(labelSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Label> createLabelSpecification(String brand, String model) {
        return Specification.where(brandEqual(brand)).and(modelLike(model));
    }

    public boolean addLabelRecord(Label newLabel) {
        return saveRecord(newLabel);
    }

    public boolean editLabelRecord(String brand, String model, @NotNull Label editLabel) {
        editLabel.setBrand(brand);
        editLabel.setModel(model);
        return saveRecord(editLabel);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var labelFilePath = "";
        try {
            labelFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newLabels = excelImporter.importFile(labelFilePath);
            newLabels.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(labelFilePath);
            return false;
        }
    }

    private boolean saveRecord(Label saveLabel) {
        try {
            labelRepo.save(saveLabel);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Label delLabel) {
        labelRepo.delete(delLabel);
    }
}