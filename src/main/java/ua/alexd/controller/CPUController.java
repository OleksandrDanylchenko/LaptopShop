package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.CPU;
import ua.alexd.repos.CPURepo;

import static ua.alexd.specification.CPUSpecification.frequencyEqual;
import static ua.alexd.specification.CPUSpecification.modelLike;

@Controller
@RequestMapping("/cpu")
public class CPUController {
    private final CPURepo cpuRepo;

    public CPUController(CPURepo cpuRepo) {
        this.cpuRepo = cpuRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) String frequency,
                              @NotNull Model siteModel) {
        var cpuSpecification = Specification.where(modelLike(model)).and(frequencyEqual(frequency));
        var cpus = cpuRepo.findAll(cpuSpecification);

        siteModel.addAttribute("cpus", cpus);
        return "/list/cpuList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/cpuAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam(required = false) String model,
                             @RequestParam(required = false) String frequency,
                             @NotNull Model siteModel) {
        if (isFieldsEmpty(model, frequency, siteModel))
            return "add/cpuAdd";

        var newCpu = new CPU(model, frequency);
        if (!saveRecord(newCpu, siteModel))
            return "add/cpuAdd";

        return "redirect:/cpu";
    }

    @NotNull
    @GetMapping("/edit/{editCpu}")
    private String editRecord(@PathVariable CPU editCpu, @NotNull Model model) {
        model.addAttribute("editCpu", editCpu);
        return "/edit/cpuEdit";
    }

    @NotNull
    @PostMapping("/edit/{editCpu}")
    private String addRecord(@RequestParam String model, @RequestParam String frequency,
                             @NotNull @PathVariable CPU editCpu, @NotNull Model siteModel) {
        if (isFieldsEmpty(model, frequency, siteModel))
            return "edit/cpuEdit";

        editCpu.setModel(model);
        editCpu.setFrequency(frequency);
        if (!saveRecord(editCpu, siteModel))
            return "edit/cpuEdit";

        return "redirect:/cpu";
    }

    @NotNull
    @GetMapping("/delete/{delCpu}")
    private String deleteRecord(@NotNull @PathVariable CPU delCpu) {
        cpuRepo.delete(delCpu);
        return "redirect:/cpu";
    }

    private boolean isFieldsEmpty(String model, String frequency, Model siteModel) {
        if (frequency == null || model == null ||
                frequency.isBlank() || model.isBlank()) {
            siteModel.addAttribute("errorMessage", "Поля процесора не можуть бути пустими!");
            return true;
        }
        return false;
    }

    private boolean saveRecord(CPU saveCPU, Model model) {
        try {
            cpuRepo.save(saveCPU);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель процесора " + saveCPU.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}