package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Hardware;
import ua.alexd.repos.*;

import static ua.alexd.specification.HardwareSpecification.*;

@Controller
@RequestMapping("/hardware")
public class HardwareController {
    private final HardwareRepo hardwareRepo;
    private final CPURepo cpuRepo;
    private final RAMRepo ramRepo;
    private final SSDRepo ssdRepo;
    private final DisplayRepo displayRepo;
    private final HDDRepo hddRepo;
    private final GPURepo gpuRepo;

    public HardwareController(HardwareRepo hardwareRepo, CPURepo cpuRepo, RAMRepo ramRepo, SSDRepo ssdRepo,
                              DisplayRepo displayRepo, HDDRepo hddRepo, GPURepo gpuRepo) {
        this.hardwareRepo = hardwareRepo;
        this.cpuRepo = cpuRepo;
        this.ramRepo = ramRepo;
        this.ssdRepo = ssdRepo;
        this.displayRepo = displayRepo;
        this.hddRepo = hddRepo;
        this.gpuRepo = gpuRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String displayModel,
                              @RequestParam(required = false) String displayDiagonal,
                              @RequestParam(required = false) String displayResolution,
                              @RequestParam(required = false) String displayType,
                              @RequestParam(required = false) String cpuModel,
                              @RequestParam(required = false) String cpuFrequency,
                              @RequestParam(required = false) String ramModel,
                              @RequestParam(required = false) Integer ramMemory,
                              @RequestParam(required = false) String ssdModel,
                              @RequestParam(required = false) Integer ssdMemory,
                              @RequestParam(required = false) String hddModel,
                              @RequestParam(required = false) Integer hddMemory,
                              @RequestParam(required = false) String gpuModel,
                              @RequestParam(required = false) Integer gpuMemory,
                              @RequestParam(required = false) String assemblyName,
                              @NotNull Model model) {
        var hardwareSpecification = Specification
                .where(displayModelLike(displayModel)).and(displayDiagonalEqual(displayDiagonal))
                .and(displayResolutionEqual(displayResolution)).and(displayTypeEqual(displayType))
                .and(cpuModelLike(cpuModel)).and(cpuFrequencyEqual(cpuFrequency))
                .and(gpuModelLike(gpuModel)).and(gpuMemoryEqual(gpuMemory))
                .and(ramModelLike(ramModel)).and(ramMemoryEqual(ramMemory))
                .and(ssdModelLike(ssdModel)).and(ssdMemoryEqual(ssdMemory))
                .and(hddModelLike(hddModel)).and(hddMemoryEqual(hddMemory))
                .and(assemblyNameEqual(assemblyName));
        var hardware = hardwareRepo.findAll(hardwareSpecification);

        model.addAttribute("hardware", hardware);
        return "/list/hardwareList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        initializeDropDownChoices(model);
        return "add/hardwareAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam(required = false) String assemblyName,
                             @RequestParam(required = false) String cpuModel,
                             @RequestParam(required = false) String ramModel,
                             @RequestParam(required = false) String ssdModel,
                             @RequestParam(required = false) String displayModel,
                             @RequestParam(required = false) String hddModel,
                             @RequestParam(required = false) String gpuModel,
                             @NotNull Model model) {
        if (isFieldsEmpty(assemblyName, model))
            return "add/hardwareAdd";

        var cpu = cpuRepo.findByModel(cpuModel);
        var ram = ramRepo.findByModel(ramModel);
        var ssd = ssdRepo.findByModel(ssdModel);
        var hdd = hddRepo.findByModel(hddModel);
        var gpu = gpuRepo.findByModel(gpuModel);
        var display = displayRepo.findByModel(displayModel);

        var newHardware = new Hardware(assemblyName, cpu, gpu, ram, ssd, hdd, display);
        if (!saveRecord(newHardware, model))
            return "add/hardwareAdd";

        return "redirect:/hardware";
    }

    @NotNull
    @GetMapping("/edit/{editHardware}")
    private String editRecord(@PathVariable Hardware editHardware, @NotNull Model model) {
        model.addAttribute("editHardware", editHardware);
        initializeDropDownChoices(model);
        return "/edit/hardwareEdit";
    }

    @NotNull
    @PostMapping("/edit/{editHardware}")
    private String editRecord(@RequestParam String assemblyName,
                              @RequestParam(required = false) String cpuModel,
                              @RequestParam(required = false) String ramModel,
                              @RequestParam(required = false) String ssdModel,
                              @RequestParam(required = false) String displayModel,
                              @RequestParam(required = false) String hddModel,
                              @RequestParam(required = false) String gpuModel,
                              @PathVariable Hardware editHardware, @NotNull Model model) {
        if (isFieldsEmpty(assemblyName, model))
            return "/edit/hardwareEdit";

        editHardware.setAssemblyName(assemblyName);

        var cpu = cpuRepo.findByModel(cpuModel);
        editHardware.setCpu(cpu);

        var gpu = gpuRepo.findByModel(gpuModel);
        editHardware.setGpu(gpu);

        var ram = ramRepo.findByModel(ramModel);
        editHardware.setRam(ram);

        var ssd = ssdRepo.findByModel(ssdModel);
        editHardware.setSsd(ssd);

        var hdd = hddRepo.findByModel(hddModel);
        editHardware.setHdd(hdd);

        var display = displayRepo.findByModel(displayModel);
        editHardware.setDisplay(display);

        if (!saveRecord(editHardware, model))
            return "/edit/hardwareEdit";

        return "redirect:/hardware";
    }

    @NotNull
    @GetMapping("/delete/{delHardware}")
    private String deleteRecord(@NotNull @PathVariable Hardware delHardware) {
        hardwareRepo.delete(delHardware);
        return "redirect:/hardware";
    }

    private boolean isFieldsEmpty(String assemblyName, @NotNull Model model) {
        if (assemblyName == null || assemblyName.isBlank()) {
            model.addAttribute("errorMessage", "Назва збірки не може бути пустою!");
            initializeDropDownChoices(model);
            return true;
        }
        return false;
    }

    private boolean saveRecord(Hardware saveHardware, Model model) {
        try {
            hardwareRepo.save(saveHardware);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Збірка " + saveHardware.getAssemblyName() + " уже присутня в базі");
            initializeDropDownChoices(model);
            return false;
        }
        return true;
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("cpuModels", cpuRepo.getAllModels())
                .addAttribute("ramModels", ramRepo.getAllModels())
                .addAttribute("ssdModels", ssdRepo.getAllModels())
                .addAttribute("displayModels", displayRepo.getAllModels())
                .addAttribute("hddModels", hddRepo.getAllModels())
                .addAttribute("gpuModels", gpuRepo.getAllModels());
    }
}