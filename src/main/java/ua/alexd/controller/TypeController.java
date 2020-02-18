package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Type;
import ua.alexd.repos.TypeRepo;

@Controller
@RequestMapping("/type")
public class TypeController {
    private final TypeRepo typeRepo;

    public TypeController(TypeRepo typeRepo) {
        this.typeRepo = typeRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String name,
                              @NotNull Model model) {
        var types = name != null && !name.isEmpty()
                ? typeRepo.findByName(name)
                : typeRepo.findAll();

        model.addAttribute("types", types).addAttribute("name", name);
        return "list/typeList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/typeAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String name, @NotNull Model model) {
        if (isNameEmpty(name, model))
            return "add/shopAdd";

        var newType = new Type(name);
        typeRepo.save(newType);

        return "redirect:/type";
    }

    @NotNull
    @GetMapping("/edit/{editType}")
    private String editRecord(@PathVariable Type editType, @NotNull Model model) {
        model.addAttribute("editType", editType);
        return "/edit/typeEdit";
    }

    @NotNull
    @PostMapping("/edit/{editType}")
    private String editedRecord(@RequestParam String name,
                                @NotNull @PathVariable Type editType,
                                @NotNull Model model) {
        if (isNameEmpty(name, model))
            return "edit/typeEdit";

        editType.setName(name);
        typeRepo.save(editType);
        return "redirect:/type";
    }

    @NotNull
    @GetMapping("/delete/{delType}")
    private String deleteRecord(@NotNull @PathVariable Type delType) {
        typeRepo.delete(delType);
        return "redirect:/type";
    }


    private boolean isNameEmpty(String name, Model model) {
        if (name == null || name.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Назва типу не можу бути пустою!");
            return true;
        }
        return false;
    }
}