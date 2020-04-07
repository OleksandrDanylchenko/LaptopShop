package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Shop;
import ua.alexd.excelInteraction.imports.ShopExcelImporter;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.ShopSpecification.addressLike;

@Controller
@RequestMapping("/shop")
public final class ShopController {
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
    public String getRecords(@RequestParam(required = false) String address, @NotNull Model model) {
        var shopSpecification = Specification.where(addressLike(address));
        var shops = shopRepo.findAll(shopSpecification);
        lastOutputtedShops = shops;
        model.addAttribute("shops", shops);
        return "view/shop/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newShop") Shop newShop, @NotNull Model model) {
        if (!saveRecord(newShop)) {
            model.addAttribute("Представлена нова адреса магазину уже уже присутня в базі!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
    }

    @NotNull
    @PostMapping("/edit/{editShop}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editAddress, @NotNull @PathVariable Shop editShop, @NotNull Model model) {
        editShop.setAddress(editAddress);
        if (!saveRecord(editShop)) {
            model.addAttribute("Представлена змінювана адреса магазину уже присутня в базі!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var shopFilePath = "";
        try {
            shopFilePath = saveUploadingFile(uploadingFile);
            var newShops = excelImporter.importFile(shopFilePath);
            newShops.forEach(this::saveRecord);
            return "redirect:/shop";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(shopFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці магазинів!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    @PreAuthorize("isAuthenticated()")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("shops", lastOutputtedShops);
        return "shopExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delShop}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String deleteRecord(@NotNull @PathVariable Shop delShop) {
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