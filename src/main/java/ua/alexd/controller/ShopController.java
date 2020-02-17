package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Shop;
import ua.alexd.repos.ShopRepo;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopRepo shopRepo;

    public ShopController(ShopRepo shopRepo) {
        this.shopRepo = shopRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false, defaultValue = "") String address,
                              @NotNull Model model) {
        Iterable<Shop> shops;
        if (address != null && !address.isEmpty())
            shops = shopRepo.findByAddress(address);
        else
            shops = shopRepo.findAll();

        model.addAttribute("shops", shops);
        model.addAttribute("address", address);

        return "list/shopList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        return "add/shopAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String address, @NotNull Model model) {
        if (isAddressEmpty(address, model))
            return "add/shopAdd";

        var newShop = new Shop(address);
        shopRepo.save(newShop);

        var shops = shopRepo.findAll();
        model.addAttribute("shops", shops);

        return "/list/shopList";
    }

    @NotNull
    @GetMapping("/edit/{editShop}")
    private String editRecord(@PathVariable Shop editShop, @NotNull Model model) {
        model.addAttribute("editShop", editShop);
        return "/edit/shopEdit";
    }

    @NotNull
    @PostMapping("/edit/{editShop}")
    private String editedRecord(@RequestParam String address,
                                @NotNull @PathVariable("editShop") Shop editShop,
                                @NotNull Model model) {
        if (isAddressEmpty(address, model))
            return "edit/shopEdit";

        editShop.setAddress(address);
        shopRepo.save(editShop);
        return "redirect:/shop";
    }

    @NotNull
    @GetMapping("/delete/{delShop}")
    private String deleteRecord(@NotNull @PathVariable("delShop") Shop delShop) {
        shopRepo.delete(delShop);
        return "redirect:/shop";
    }

    private boolean isAddressEmpty(String address, Model model) {
        if (address == null || address.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Адреса магазину не можу бути пустою!");
            return true;
        }
        return false;
    }
}
