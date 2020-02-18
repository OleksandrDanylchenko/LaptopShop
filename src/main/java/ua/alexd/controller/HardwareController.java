package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.alexd.repos.HardwareRepo;

import static ua.alexd.specification.hardwareSpecification.*;

@Controller
@RequestMapping("/hardware")
public class HardwareController {
    private final HardwareRepo hardwareRepo;

    public HardwareController(HardwareRepo hardwareRepo) {
        this.hardwareRepo = hardwareRepo;
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
                .addAttribute("ramModel", ramMemory).addAttribute("ramMemory", ramMemory)
                .addAttribute("ssdModel", ssdModel).addAttribute("ssdMemory", ssdMemory)
                .addAttribute("hddModel", hddModel).addAttribute("hddMemory", hddMemory)
                .addAttribute("gpuModel", gpuModel).addAttribute("gpuMemory", gpuMemory)
                .addAttribute("assemblyName", assemblyName);
        return "/list/hardwareList";
    }
}
