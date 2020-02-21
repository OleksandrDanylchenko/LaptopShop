package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.HDD;
import ua.alexd.repos.HDDRepo;

import static ua.alexd.specification.HDDSpecification.memoryEqual;
import static ua.alexd.specification.HDDSpecification.modelLike;

@Controller
@RequestMapping("/hdd")
public class HDDController {
    private final HDDRepo hddRepo;

    public HDDController(HDDRepo hddRepo) {
        this.hddRepo = hddRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String model,
                              @RequestParam(required = false) Integer memory,
                              @NotNull Model siteModel) {
        var hddSpecification = Specification.where(modelLike(model)).and(memoryEqual(memory));
        var hdds = hddRepo.findAll(hddSpecification);

        siteModel.addAttribute("hdds", hdds);
        return "/list/hddList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/hddAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull Model siteModel) {
        if (isFieldsEmpty(model, memory, siteModel))
            return "add/hddAdd";

        var newHDD = new HDD(model, memory);
        if (!saveRecord(newHDD, siteModel))
            return "add/hddAdd";

        return "redirect:/hdd";
    }

    @NotNull
    @GetMapping("/edit/{editHDD}")
    private String editRecord(@PathVariable HDD editHDD, @NotNull Model model) {
        model.addAttribute("editHDD", editHDD);
        return "/edit/hddEdit";
    }

    @NotNull
    @PostMapping("/edit/{editHDD}")
    private String addRecord(@RequestParam String model, @RequestParam Integer memory,
                             @NotNull @PathVariable HDD editHDD, @NotNull Model siteModel) {
        if (isFieldsEmpty(model, memory, siteModel))
            return "edit/hddEdit";

        editHDD.setModel(model);
        editHDD.setMemory(memory);
        if (!saveRecord(editHDD, siteModel))
            return "edit/hddEdit";

        return "redirect:/hdd";
    }

    @NotNull
    @GetMapping("/delete/{delHDD}")
    private String deleteRecord(@NotNull @PathVariable HDD delHDD) {
        hddRepo.delete(delHDD);
        return "redirect:/hdd";
    }

    private boolean isFieldsEmpty(String model, Integer memory, Model siteModel) {
        if (memory == null || model == null || model.isEmpty()) {
            siteModel.addAttribute("errorMessage", "Поля HDD диску не можуть бути пустими!");
            return true;
        }
        return false;
    }

    private boolean saveRecord(HDD saveHDD, Model model) {
        try {
            hddRepo.save(saveHDD);
        } catch (Exception e) {
            model.addAttribute("errorMessage",
                    "Модель HDD диску " + saveHDD.getModel() + " уже присутня в базі");
            return false;
        }
        return true;
    }
}