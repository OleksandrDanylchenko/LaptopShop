package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.GPU;
import ua.alexd.excelInteraction.imports.GPUExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.GPURepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.GPUSpecification.memoryEqual;
import static ua.alexd.specification.GPUSpecification.modelLike;

@Service
@Lazy
public class GpuService {
    private final GPURepo gpuRepo;

    private final GPUExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public GpuService(GPURepo gpuRepo, GPUExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.gpuRepo = gpuRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<GPU> loadGPUTable(String model, Integer memory) {
        var gpuSpecification = createCPUSpecification(model, memory);
        return gpuRepo.findAll(gpuSpecification);
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<GPU> createCPUSpecification(String model, Integer memory) {
        return Specification.where(modelLike(model)).and(memoryEqual(memory));
    }

    public boolean addGPURecord(GPU newGPU) {
        return saveRecord(newGPU);
    }

    public boolean editGPURecord(String model, Integer memory, @NotNull GPU editGpu) {
        editGpu.setModel(model);
        editGpu.setMemory(memory);
        return saveRecord(editGpu);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile) {
        var GPUFilePath = "";
        try {
            GPUFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newGPUs = excelImporter.importFile(GPUFilePath);
            newGPUs.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(GPUFilePath);
            return false;
        }
    }

    private boolean saveRecord(GPU saveGpu) {
        try {
            gpuRepo.save(saveGpu);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(GPU delGpu) {
        gpuRepo.delete(delGpu);
    }
}