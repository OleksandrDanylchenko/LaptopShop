package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Hardware;
import ua.alexd.excelInteraction.imports.HardwareExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.*;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.HardwareSpecification.*;

@Service
@Lazy
public class HardwareService {
    private final HardwareRepo hardwareRepo;

    private final CPURepo cpuRepo;
    private final RAMRepo ramRepo;
    private final SSDRepo ssdRepo;
    private final DisplayRepo displayRepo;
    private final HDDRepo hddRepo;
    private final GPURepo gpuRepo;

    private final HardwareExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public HardwareService(HardwareRepo hardwareRepo, CPURepo cpuRepo, RAMRepo ramRepo,
                           SSDRepo ssdRepo, DisplayRepo displayRepo, HDDRepo hddRepo,
                           GPURepo gpuRepo, HardwareExcelImporter excelImporter, UploadedFilesManager filesManager) {
        this.hardwareRepo = hardwareRepo;
        this.cpuRepo = cpuRepo;
        this.ramRepo = ramRepo;
        this.ssdRepo = ssdRepo;
        this.displayRepo = displayRepo;
        this.hddRepo = hddRepo;
        this.gpuRepo = gpuRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Hardware> loadHardwareTable(String displayModel, String displayDiagonal, String displayResolution,
                                                String displayType, String cpuModel, String cpuFrequency, String ramModel,
                                                Integer ramMemory, String ssdModel, Integer ssdMemory, String hddModel,
                                                Integer hddMemory, String gpuModel, Integer gpuMemory, String assemblyName,
                                                Model model) {
        var hardwareSpecification = createHardwareSpecification(displayModel, displayDiagonal, displayResolution,
                displayType, cpuModel, cpuFrequency, ramModel, ramMemory, ssdModel, ssdMemory, hddModel, hddMemory,
                gpuModel, gpuMemory, assemblyName);
        var hardware = hardwareRepo.findAll(hardwareSpecification);
        initializeHardwareChoices(model);
        return hardware;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Hardware> createHardwareSpecification(String displayModel, String displayDiagonal,
                                                                String displayResolution, String displayType,
                                                                String cpuModel, String cpuFrequency, String ramModel,
                                                                Integer ramMemory, String ssdModel, Integer ssdMemory,
                                                                String hddModel, Integer hddMemory, String gpuModel,
                                                                Integer gpuMemory, String assemblyName) {
        return Specification
                .where(displayModelLike(displayModel)).and(displayDiagonalEqual(displayDiagonal))
                .and(displayResolutionEqual(displayResolution)).and(displayTypeEqual(displayType))
                .and(cpuModelLike(cpuModel)).and(cpuFrequencyEqual(cpuFrequency))
                .and(gpuModelLike(gpuModel)).and(gpuMemoryEqual(gpuMemory))
                .and(ramModelLike(ramModel)).and(ramMemoryEqual(ramMemory))
                .and(ssdModelLike(ssdModel)).and(ssdMemoryEqual(ssdMemory))
                .and(hddModelLike(hddModel)).and(hddMemoryEqual(hddMemory))
                .and(assemblyNameEqual(assemblyName));
    }

    public boolean addHardwareRecord(String assemblyName, String cpuModel, String ramModel, String ssdModel,
                                     String displayModel, String hddModel, String gpuModel, Model model) {
        var cpu = cpuRepo.findByModel(cpuModel);
        var ram = ramRepo.findByModel(ramModel);
        var ssd = ssdRepo.findByModel(ssdModel);
        var hdd = hddRepo.findByModel(hddModel);
        var gpu = gpuRepo.findByModel(gpuModel);
        var display = displayRepo.findByModel(displayModel);
        var newHardware = new Hardware(assemblyName, cpu, gpu, ram, ssd, hdd, display);
        initializeHardwareChoices(model);
        return saveRecord(newHardware);
    }

    public boolean editHardwareRecord(String editAssemblyName, String editCpuModel, String editRamModel,
                                      String editSsdModel, String editDisplayModel, String editHddModel,
                                      String editGpuModel, @NotNull Hardware editHardware,
                                      Model model) {
        editHardware.setAssemblyName(editAssemblyName);
        var cpu = cpuRepo.findByModel(editCpuModel);
        editHardware.setCpu(cpu);
        var gpu = gpuRepo.findByModel(editGpuModel);
        editHardware.setGpu(gpu);
        var ram = ramRepo.findByModel(editRamModel);
        editHardware.setRam(ram);
        var ssd = ssdRepo.findByModel(editSsdModel);
        editHardware.setSsd(ssd);
        var hdd = hddRepo.findByModel(editHddModel);
        editHardware.setHdd(hdd);
        var display = displayRepo.findByModel(editDisplayModel);
        editHardware.setDisplay(display);
        initializeHardwareChoices(model);
        return saveRecord(editHardware);
    }

    public boolean importExcelRecords(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        initializeHardwareChoices(model);
        var hardwareFilePath = "";
        try {
            hardwareFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newHardware = excelImporter.importFile(hardwareFilePath);
            newHardware.forEach(this::saveRecord);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(hardwareFilePath);
            return false;
        }
    }

    private boolean saveRecord(Hardware saveHardware) {
        try {
            hardwareRepo.save(saveHardware);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(Hardware delHardware) {
        hardwareRepo.delete(delHardware);
    }

    private void initializeHardwareChoices(@NotNull Model model) {
        model.addAttribute("cpuModels", cpuRepo.getAllModels())
                .addAttribute("ramModels", ramRepo.getAllModels())
                .addAttribute("ssdModels", ssdRepo.getAllModels())
                .addAttribute("displayModels", displayRepo.getAllModels())
                .addAttribute("hddModels", hddRepo.getAllModels())
                .addAttribute("gpuModels", gpuRepo.getAllModels());
    }
}