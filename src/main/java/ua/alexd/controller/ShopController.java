package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Shop;
import ua.alexd.excelUtils.imports.ShopExcelImporter;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

import java.io.IOException;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopRepo shopRepo;
    private static Iterable<Shop> lastOutputtedShops;

    private final EmployeeRepo employeeRepo;

    private final ShopExcelImporter excelImporter;

    public ShopController(ShopRepo shopRepo, EmployeeRepo employeeRepo, ShopExcelImporter excelImporter) {
        this.shopRepo = shopRepo;
        this.employeeRepo = employeeRepo;
        this.excelImporter = excelImporter;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String address, @NotNull Model model) {
        var shops = address != null && !address.isEmpty()
                ? shopRepo.findByAddress(address)
                : shopRepo.findAll();
        lastOutputtedShops = shops;
        model.addAttribute("shops", shops);
        return "list/shopList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord() {
        return "add/shopAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@NotNull @ModelAttribute("newShop") Shop newShop, @NotNull Model model) {
        if (!saveRecord(newShop)) {
            model.addAttribute("Представлена адреса уже уже присутня в базі!");
            return "add/shopAdd";
        }
        return "redirect:/shop";
    }

    @NotNull
    @GetMapping("/edit/{editShop}")
    private String editRecord(@PathVariable Shop editShop, @NotNull Model model) {
        model.addAttribute("editShop", editShop);
        return "/edit/shopEdit";
    }

    @NotNull
    @PostMapping("/edit/{editShop}")
    private String editRecord(@RequestParam String address, @NotNull @PathVariable Shop editShop, @NotNull Model model) {
        editShop.setAddress(address);
        if (!saveRecord(editShop)) {
            model.addAttribute("Представлена адреса уже уже присутня в базі!");
            return "edit/shopEdit";
        }
        return "redirect:/shop";
    }

    @NotNull
    @GetMapping("/importExcel")
    private String importExcel(@NotNull Model model) {
        initializeImportAttributes(model);
        return "excel/excelFilesUpload";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var shopFilePath = "";
        try {
            shopFilePath = saveUploadingFile(uploadingFile);
            var newShops = excelImporter.importFile(shopFilePath);
            newShops.forEach(this::saveRecord);
            return "redirect:/shop";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(shopFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці магазинів!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Shop.class.getSimpleName());
        model.addAttribute("tableName", "магазинів");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("shops", lastOutputtedShops);
        return "shopExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delShop}")
    private String deleteRecord(@NotNull @PathVariable Shop delShop) {
        dismissEmployees(delShop);
        shopRepo.delete(delShop);
        return "redirect:/shop";
    }

    private boolean saveRecord(Shop saveShop) {
        try {
            shopRepo.save(saveShop);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    private void dismissEmployees(@NotNull Shop delShop) {
        var employees = delShop.getShopEmployees();
        employees.forEach(e -> e.setActive(false));
        for (var employee : employees)
            employeeRepo.save(employee);
    }
}