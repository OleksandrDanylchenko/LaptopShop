package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Display;
import ua.alexd.repos.DisplayRepo;

import static ua.alexd.specification.DisplaySpecification.*;

@Controller
@RequestMapping("/display")
public class DisplayController {
    private final DisplayRepo displayRepo;

    public DisplayController(DisplayRepo displayRepo) {
        this.displayRepo = displayRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String type, @RequestParam(required = false) String diagonal,
                              @RequestParam(required = false) String resolution, @NotNull Model model) {
        var displaySpecification = Specification.where(typeEqual(type))
                .and(diagonalEqual(diagonal)).and(resolutionEqual(resolution));
        var displays = displayRepo.findAll(displaySpecification);

        model.addAttribute("displays", displays);
        model.addAttribute("type", type);
        model.addAttribute("diagonal", diagonal);
        model.addAttribute("resolution", resolution);
        return "/list/displayList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/displayAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam(required = false) String type, @RequestParam(required = false) String diagonal,
                             @RequestParam(required = false) String resolution, @NotNull Model model) {
        if (isFieldsEmpty(type, diagonal, resolution, model))
            return "add/displayAdd";

        var newDisplay = new Display(type, diagonal, resolution);
        displayRepo.save(newDisplay);

        var displays = displayRepo.findAll();
        model.addAttribute("displays", displays);
        return "redirect:/display";
    }

    @NotNull
    @GetMapping("/edit/{editDisplay}")
    private String editRecord(@NotNull @PathVariable Display editDisplay, @NotNull Model model) {
        model.addAttribute("editDisplay", editDisplay);
        return "/edit/displayEdit";
    }

    @NotNull
    @PostMapping("/edit/{editDisplay}")
    private String saveEditedRecord(@RequestParam String type, @RequestParam String diagonal,
                                    @RequestParam String resolution,
                                    @NotNull @PathVariable Display editDisplay, @NotNull Model model) {
        if (isFieldsEmpty(type, diagonal, resolution, model))
            return "edit/displayEdit";

        editDisplay.setType(type);
        editDisplay.setDiagonal(diagonal);
        editDisplay.setResolution(resolution);
        displayRepo.save(editDisplay);
        return "redirect:/display";
    }

    @NotNull
    @GetMapping("/delete/{delDisplay}")
    private String deleteRecord(@NotNull @PathVariable Display delDisplay) {
        displayRepo.delete(delDisplay);
        return "redirect:/display";
    }

    private boolean isFieldsEmpty(String type, String diagonal, String resolution, Model model) {
        if (type == null || diagonal == null || resolution == null ||
                type.isEmpty() || diagonal.isEmpty() || resolution.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Поля дисплею не можуть бути пустими!");
            return true;
        }
        return false;
    }
}