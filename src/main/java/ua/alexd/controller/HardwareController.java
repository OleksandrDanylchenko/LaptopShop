package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
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

        model.addAttribute("hardware", hardware)
                .addAttribute("displayModel", displayModel).addAttribute("displayDiagonal", displayDiagonal)
                .addAttribute("displayResolution", displayResolution).addAttribute("displayType", displayType)
                .addAttribute("cpuModel", cpuModel).addAttribute("cpuFrequency", cpuFrequency)
                .addAttribute("ramModel", ramModel).addAttribute("ramMemory", ramMemory)
                .addAttribute("ssdModel", ssdModel).addAttribute("ssdMemory", ssdMemory)
                .addAttribute("hddModel", hddModel).addAttribute("hddMemory", hddMemory)
                .addAttribute("gpuModel", gpuModel).addAttribute("gpuMemory", gpuMemory)
                .addAttribute("assemblyName", assemblyName);
        return "/list/hardwareList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        model.addAttribute("cpuModels", cpuRepo.getAllModels())
                .addAttribute("ramModels", ramRepo.getAllModels())
                .addAttribute("ssdModels", ssdRepo.getAllModels())
                .addAttribute("displayModels", displayRepo.getAllModels())
                .addAttribute("hddModels", hddRepo.getAllModels())
                .addAttribute("gpuModels", gpuRepo.getAllModels());
        return "add/hardwareAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String assemblyName, @RequestParam String cpuModel, @RequestParam String ramModel,
                             @RequestParam String ssdModel, @RequestParam String displayModel,
                             @RequestParam String hddModel, @RequestParam String gpuModel,
                             @NotNull Model model) {
        if (isFieldsEmpty(assemblyName, cpuModel, ramModel, ssdModel, displayModel, hddModel, gpuModel, model)) {
            model.addAttribute("cpuModels", cpuRepo.getAllModels())
                    .addAttribute("ramModels", ramRepo.getAllModels())
                    .addAttribute("ssdModels", ssdRepo.getAllModels())
                    .addAttribute("displayModels", displayRepo.getAllModels())
                    .addAttribute("hddModels", hddRepo.getAllModels())
                    .addAttribute("gpuModels", gpuRepo.getAllModels());
            return "add/hardwareAdd";
        }

        var cpu = cpuRepo.findByModel(cpuModel);
        var ram = ramRepo.findByModel(ramModel);
        var ssd = ssdRepo.findByModel(ssdModel);
        var hdd = hddRepo.findByModel(hddModel);
        var gpu = gpuRepo.findByModel(gpuModel);
        var display = displayRepo.findByModel(displayModel);
        var newHardware = new Hardware(assemblyName, cpu, gpu, ram, ssd, hdd, display);
        hardwareRepo.save(newHardware);

        return "redirect:/hardware";
    }

    @NotNull
    @GetMapping("/edit/{editHardware}")
    private String editRecord(@PathVariable Hardware editHardware, @NotNull Model model) {
        model.addAttribute("editHardware", editHardware)
                .addAttribute("cpuModels", cpuRepo.getAllModels())
                .addAttribute("ramModels", ramRepo.getAllModels())
                .addAttribute("ssdModels", ssdRepo.getAllModels())
                .addAttribute("displayModels", displayRepo.getAllModels())
                .addAttribute("hddModels", hddRepo.getAllModels())
                .addAttribute("gpuModels", gpuRepo.getAllModels());
        return "/edit/hardwareEdit";
    }

    @NotNull
    @PostMapping("/edit/{editHardware}")
    private String editRecord(@RequestParam String assemblyName, @RequestParam String cpuModel, @RequestParam String ramModel,
                             @RequestParam String ssdModel, @RequestParam String displayModel,
                             @RequestParam String hddModel, @RequestParam String gpuModel,
                             @PathVariable Hardware editHardware, @NotNull Model model) {
        if (isFieldsEmpty(assemblyName, cpuModel, ramModel, ssdModel, displayModel, hddModel, gpuModel, model))
            return "/edit/hardwareEdit";

        editHardware.setAssemblyName(assemblyName);

        var cpu = cpuRepo.findByModel(cpuModel);
        editHardware.setCpu(cpu);

        var ram = ramRepo.findByModel(ramModel);
        editHardware.setRam(ram);

        var ssd = ssdRepo.findByModel(ssdModel);
        editHardware.setSsd(ssd);

        var hdd = hddRepo.findByModel(hddModel);
        editHardware.setHdd(hdd);

        var gpu = gpuRepo.findByModel(gpuModel);
        editHardware.setGpu(gpu);

        hardwareRepo.save(editHardware);

        return "redirect:/hardware";
    }

    @NotNull
    @GetMapping("/delete/{delHardware}")
    private String deleteRecord(@NotNull @PathVariable Hardware delHardware) {
        hardwareRepo.delete(delHardware);
        return "redirect:/hardware";
    }

    private boolean isFieldsEmpty(String assemblyName, String cpuModel, String ramModel, String ssdModel,
                                  String displayModel, String hddModel, String gpuModel, @NotNull Model model) {
        if (assemblyName == null || cpuModel == null || ramModel == null || ssdModel == null ||
                displayModel == null || hddModel == null || gpuModel == null ||
                assemblyName.isEmpty() || cpuModel.isEmpty() || ramModel.isEmpty() || ssdModel.isEmpty() ||
                displayModel.isEmpty() || hddModel.isEmpty() || gpuModel.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Поля збірки не можуть бути пустими!");
            return true;
        }
        return false;
    }
}