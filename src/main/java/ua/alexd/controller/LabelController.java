package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Label;
import ua.alexd.repos.LabelRepo;

import static ua.alexd.specification.LabelSpecification.brandEqual;
import static ua.alexd.specification.LabelSpecification.modelLike;

@Controller
@RequestMapping("/label")
public class LabelController {
    private final LabelRepo labelRepo;

    public LabelController(LabelRepo labelRepo) {
        this.labelRepo = labelRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String brand,
                              @RequestParam(required = false) String model,
                              @NotNull Model siteModel) {
        var labelSpecification = Specification.where(brandEqual(brand)).and(modelLike(model));
        var labels = labelRepo.findAll(labelSpecification);

        siteModel.addAttribute("brand", brand);
        siteModel.addAttribute("model", model);
        siteModel.addAttribute("labels", labels);
        return "/list/labelList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/labelAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam(required = false) String brand,
                              @RequestParam(required = false) String model,
                              @NotNull Model siteModel) {
        if (isFieldsEmpty(brand, model, siteModel))
            return "add/labelAdd";

        var newLabel = new Label(brand, model);
        labelRepo.save(newLabel);

        var labels = labelRepo.findAll();
        siteModel.addAttribute("labels", labels);
        return "redirect:/label";
    }

    @NotNull
    @GetMapping("/edit/{editLabel}")
    private String editRecord(@PathVariable Label editLabel, @NotNull Model model) {
        model.addAttribute("editLabel", editLabel);
        return "/edit/labelEdit";
    }

    @NotNull
    @PostMapping("/edit/{editLabel}")
    private String addRecord(@RequestParam String brand, @RequestParam String model,
                             @NotNull @PathVariable Label editLabel, @NotNull Model siteModel) {
        if (isFieldsEmpty(brand, model, siteModel))
            return "edit/shopEdit";

        editLabel.setBrand(brand);
        editLabel.setModel(model);
        labelRepo.save(editLabel);
        return "redirect:/label";
    }

    @NotNull
    @GetMapping("/delete/{delLabel}")
    private String deleteRecord(@NotNull @PathVariable Label delLabel) {
        labelRepo.delete(delLabel);
        return "redirect:/label";
    }

    private boolean isFieldsEmpty(String brand, String model, Model siteModel) {
        if (brand == null || model == null ||
                brand.isEmpty() || model.isEmpty()) {
            siteModel.addAttribute("errorMessage",
                    "Поля найменування не можуть бути пустими!");
            return true;
        }
        return false;
    }
}