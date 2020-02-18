package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.SSD;
import ua.alexd.repos.SSDRepo;

import static ua.alexd.specification.SSDSpecification.memoryEqual;
import static ua.alexd.specification.SSDSpecification.modelLike;

@Controller
@RequestMapping("/ssd")
public class SSDController {
    private final SSDRepo ssdRepo;

    public SSDController(SSDRepo ssdRepo) {
        this.ssdRepo = ssdRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var ssdSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var ssds = ssdRepo.findAll(ssdSpecification);

        siteModel.addAttribute("model", model).addAttribute("memory", memory)
                .addAttribute("ssds", ssds);
        return "/list/ssdList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/ssdAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull Model siteModel) {
        if (isFieldsEmpty(model, memory, siteModel)) {
            siteModel.addAttribute("model", model).addAttribute("memory", memory);
            return "add/ssdAdd";
        }

        var newSSD = new SSD(model, memory);
        ssdRepo.save(newSSD);

        return "redirect:/ssd";
    }

    @NotNull
    @GetMapping("/edit/{editSSD}")
    private String editRecord(@PathVariable SSD editSSD, @NotNull Model model) {
        model.addAttribute("editSSD", editSSD);
        return "/edit/ssdEdit";
    }

    @NotNull
    @PostMapping("/edit/{editSSD}")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull @PathVariable SSD editSSD, @NotNull Model siteModel) {
        if (isFieldsEmpty(model, memory, siteModel))
            return "edit/ssdEdit";

        editSSD.setModel(model);
        editSSD.setMemory(memory);
        ssdRepo.save(editSSD);
        return "redirect:/ssd";
    }

    @NotNull
    @GetMapping("/delete/{delSSD}")
    private String deleteRecord(@NotNull @PathVariable SSD delSSD) {
        ssdRepo.delete(delSSD);
        return "redirect:/ssd";
    }

    private boolean isFieldsEmpty(String model, Integer memory, Model siteModel) {
        if (memory == null || model == null || model.isEmpty()) {
            siteModel.addAttribute("errorMessage",
                    "Поля SSD диску не можуть бути пустими!");
            return true;
        }
        return false;
    }
}