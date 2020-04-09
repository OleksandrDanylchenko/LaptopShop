package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.ShopService;
import ua.alexd.domain.Shop;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopService shopService;
    private Iterable<Shop> lastOutputtedShops;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String address, @NotNull Model model) {
        var shops = shopService.loadShopTable(address);
        lastOutputtedShops = shops;
        model.addAttribute("shops", shops);
        return "view/shop/table";
    }

    @NotNull
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String addRecord(@NotNull @ModelAttribute("newShop") Shop newShop, @NotNull Model model) {
        var isNewShopSaved = shopService.addShopRecord(newShop);
        if (!isNewShopSaved) {
            model.addAttribute("Представлена нова адреса магазину уже уже присутня в базі!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
    }

    @NotNull
    @PostMapping("/edit/{editShop}")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String editRecord(@RequestParam String editAddress, @NotNull @PathVariable Shop editShop,
                             @NotNull Model model) {
        var isEditShopSaved = shopService.editShopRecord(editAddress, editShop);
        if (!isEditShopSaved) {
            model.addAttribute("Представлена змінювана адреса магазину уже присутня в базі!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
    }

    @NotNull
    @PostMapping("/importExcel")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = shopService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці магазинів!");
            model.addAttribute("shops", lastOutputtedShops);
            return "view/shop/table";
        }
        return "redirect:/shop";
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
        shopService.deleteRecord(delShop);
        return "redirect:/shop";
    }
}