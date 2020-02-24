package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
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

        model.addAttribute("types", types);
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
        var newType = new Type(name);
        if (!saveRecord(newType, model))
            return "add/typeAdd";

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
        editType.setName(name);
        if (!saveRecord(editType, model))
            return "edit/typeEdit";

        return "redirect:/type";
    }

    @NotNull
    @GetMapping("/delete/{delType}")
    private String deleteRecord(@NotNull @PathVariable Type delType) {
        typeRepo.delete(delType);
        return "redirect:/type";
    }

    private boolean saveRecord(Type saveType, Model model) {
        try {
            typeRepo.save(saveType);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage", "Тип " + saveType.getName() + " уже присутній в базі");
            return false;
        }
        return true;
    }
}