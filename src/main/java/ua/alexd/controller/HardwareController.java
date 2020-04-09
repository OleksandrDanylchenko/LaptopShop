package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.HardwareService;
import ua.alexd.domain.Hardware;

@Controller
@RequestMapping("/hardware")
public class HardwareController {
    private final HardwareService hardwareService;
    private Iterable<Hardware> lastOutputtedHardware;

    public HardwareController(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String displayModel,
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
        var hardware = hardwareService.loadHardwareTable(displayModel, displayDiagonal, displayResolution, displayType,
                cpuModel, cpuFrequency, ramModel, ramMemory, ssdModel, ssdMemory, hddModel, hddMemory, gpuModel,
                gpuMemory, assemblyName, model);
        lastOutputtedHardware = hardware;
        model.addAttribute("hardware", hardware);
        return "view/hardware/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@RequestParam String assemblyName, @RequestParam String cpuModel,
                            @RequestParam String ramModel, @RequestParam String ssdModel,
                            @RequestParam String displayModel, @RequestParam String hddModel,
                            @RequestParam String gpuModel, @NotNull Model model) {
        var isNewHardwareSaved = hardwareService.addHardwareRecord(assemblyName, cpuModel, ramModel, ssdModel,
                displayModel, hddModel, gpuModel, model);
        if (!isNewHardwareSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова назва збірки уже присутня в базі!");
            model.addAttribute("hardware", lastOutputtedHardware);
            return "view/hardware/table";
        }
        return "redirect:/hardware";
    }

    @NotNull
    @PostMapping("/edit/{editHardware}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editAssemblyName, @RequestParam String editCpuModel,
                             @RequestParam String editRamModel, @RequestParam String editSsdModel,
                             @RequestParam String editDisplayModel, @RequestParam String editHddModel,
                             @RequestParam String editGpuModel, @NotNull @PathVariable Hardware editHardware,
                             @NotNull Model model) {
        var isEditHardwareSaved = hardwareService.editHardwareRecord(editAssemblyName, editCpuModel, editRamModel,
                editSsdModel, editDisplayModel, editHddModel, editGpuModel, editHardware, model);
        if (!isEditHardwareSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана назва збірки уже присутня в базі!");
            model.addAttribute("hardware", lastOutputtedHardware);
            return "view/hardware/table";
        }
        return "redirect:/hardware";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = hardwareService.importExcelRecords(uploadingFile, model);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці збірок!");
            model.addAttribute("hardware", lastOutputtedHardware);
            return "view/hardware/table";
        }
        return "redirect:/hardware";
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("hardware", lastOutputtedHardware);
        return "hardwareExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delHardware}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable Hardware delHardware) {
        hardwareService.deleteRecord(delHardware);
        return "redirect:/hardware";
    }
}