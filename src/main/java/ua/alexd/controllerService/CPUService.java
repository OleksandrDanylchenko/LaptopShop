package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.CPU;
import ua.alexd.excelInteraction.imports.CPUExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.CPURepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.CPUSpecification.frequencyEqual;
import static ua.alexd.specification.CPUSpecification.modelLike;

@Service
@Lazy
public class CPUService {
    private final CPURepo cpuRepo;

    private final CPUExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public CPUService(CPURepo cpuRepo, CPUExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.cpuRepo = cpuRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<CPU> loadCPUTable(String model, String frequency) {
        var cpuSpecification = createCPUSpecification(model, frequency);
        return cpuRepo.findAll(cpuSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<CPU> createCPUSpecification(String model, String frequency) {
        return Specification.where(modelLike(model)).and(frequencyEqual(frequency));
    }

    public boolean addCPURecord(CPU newCPU) {
        return saveRecord(newCPU);
    }

    public boolean editCPURecord(String editModel, String editFrequency, @NotNull CPU editCpu) {
        editCpu.setModel(editModel);
        editCpu.setFrequency(editFrequency);
        return saveRecord(editCpu);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var cpuFilePath = "";
        try {
            cpuFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newCPUs = excelImporter.importFile(cpuFilePath);
            newCPUs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(cpuFilePath);
            return false;
        }
    }

    private boolean saveRecord(CPU saveCPU) {
        try {
            cpuRepo.save(saveCPU);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(CPU delCpu) {
        cpuRepo.delete(delCpu);
    }
}