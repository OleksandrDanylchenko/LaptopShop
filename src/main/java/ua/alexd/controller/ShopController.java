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
    private String getRecords(@RequestParam(required = false, defaultValue = "") String filterAddress,
                              @NotNull Model model) {
        Iterable<Shop> shops;

        if (filterAddress != null && !filterAddress.isEmpty())
            shops = shopRepo.findByAddress(filterAddress);
        else
            shops = shopRepo.findAll();
        model.addAttribute("shops", shops);
        model.addAttribute("filterAddress", filterAddress);
        return "shop";
    }

    @NotNull
    @PostMapping
    private String addRecord(@RequestParam String address, @NotNull Model model) {
        var newShop = new Shop(address);
        shopRepo.save(newShop);

        var shops = shopRepo.findAll();
        model.addAttribute("shops", shops);

        return "shop";
    }

    @NotNull
    @GetMapping("/edit/{editShop}")
    private String editRecord(@PathVariable Shop editShop, @NotNull Model model) {
        model.addAttribute("editShop", editShop);
        return "shopEdit";
    }

    @NotNull
    @PostMapping("/edit/{editShop}")
    private String saveEditedRecord(@NotNull @RequestParam String address,
                                    @NotNull @PathVariable("editShop") Shop editShop) {
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
}
