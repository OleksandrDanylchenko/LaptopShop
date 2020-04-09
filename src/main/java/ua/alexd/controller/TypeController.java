package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.TypeService;
import ua.alexd.domain.Type;

@Controller
@RequestMapping("/type")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class TypeController {
    private final TypeService typeService;
    private Iterable<Type> lastOutputtedTypes;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String typeName, @NotNull Model model) {
        var types = typeService.loadTypeTable(typeName);
        lastOutputtedTypes = types;
        model.addAttribute("types", types);
        return "view/type/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@NotNull @ModelAttribute("newType") Type newType, @NotNull Model model) {
        var isNewTypeSaved = typeService.addTypeRecord(newType);
        if (!isNewTypeSaved) {
            model.addAttribute("errorMessage",
                    "Представлена нова назва типу уже присутня в базі!");
            model.addAttribute("types", lastOutputtedTypes);
            return "view/type/table";
        }
        return "redirect:/type";
    }

    @NotNull
    @PostMapping("/edit/{editType}")
    public String editRecord(@RequestParam String editName, @NotNull @PathVariable Type editType, @NotNull Model model) {
        var isEditTypeSaved = typeService.editTypeRecord(editName, editType);
        if (!isEditTypeSaved) {
            model.addAttribute("errorMessage",
                    "Представлена змінювана назва типу уже присутня в базі!");
            model.addAttribute("types", lastOutputtedTypes);
            return "view/type/table";
        }
        return "redirect:/type";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = typeService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці типів!");
            model.addAttribute("types", lastOutputtedTypes);
            return "view/type/table";
        }
        return "redirect:/type";
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("types", lastOutputtedTypes);
        return "typeExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delType}")
    public String deleteRecord(@NotNull @PathVariable Type delType) {
        typeService.deleteRecord(delType);
        return "redirect:/type";
    }
}