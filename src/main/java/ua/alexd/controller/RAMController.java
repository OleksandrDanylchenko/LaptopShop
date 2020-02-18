package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.GPU;
import ua.alexd.domain.RAM;
import ua.alexd.repos.RAMRepo;

import static ua.alexd.specification.RAMSpecification.memoryEqual;
import static ua.alexd.specification.RAMSpecification.modelLike;


@Controller
@RequestMapping("/ram")
public class RAMController {
    private final RAMRepo ramRepo;

    public RAMController(RAMRepo ramRepo) {
        this.ramRepo = ramRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var ramSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var rams = ramRepo.findAll(ramSpecification);

        siteModel.addAttribute("model", model).addAttribute("memory", memory)
                .addAttribute("rams", rams);
        return "/list/ramList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/ramAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull Model siteModel) {
        if (isFieldsEmpty(model, memory, siteModel)) {
            siteModel.addAttribute("model", model).addAttribute("memory", memory);
            return "add/ramAdd";
        }

        var newRam = new RAM(model, memory);
        ramRepo.save(newRam);

        return "redirect:/ram";
    }

    @NotNull
    @GetMapping("/edit/{editRam}")
    private String editRecord(@PathVariable RAM editRam, @NotNull Model model) {
        model.addAttribute("editRam", editRam);
        return "/edit/ramEdit";
    }

    @NotNull
    @PostMapping("/edit/{editRam}")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull @PathVariable RAM editRam, @NotNull Model siteModel) {
        if (isFieldsEmpty(model, memory, siteModel))
            return "edit/ramEdit";

        editRam.setModel(model);
        editRam.setMemory(memory);
        ramRepo.save(editRam);
        return "redirect:/ram";
    }

    @NotNull
    @GetMapping("/delete/{delRam}")
    private String deleteRecord(@NotNull @PathVariable RAM delRam) {
        ramRepo.delete(delRam);
        return "redirect:/ram";
    }

    private boolean isFieldsEmpty(String model, Integer memory, Model siteModel) {
        if (memory == null || model == null || model.isEmpty()) {
            siteModel.addAttribute("errorMessage",
                    "Поля оперативної пам'яті не можуть бути пустими!");
            return true;
        }
        return false;
    }
}