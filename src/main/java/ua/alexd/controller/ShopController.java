package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Shop;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private final ShopRepo shopRepo;
    private final EmployeeRepo employeeRepo;

    public ShopController(final ShopRepo shopRepo, EmployeeRepo employeeRepo) {
        this.shopRepo = shopRepo;
        this.employeeRepo = employeeRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String address,
                              @NotNull Model model) {
        var shops = address != null && !address.isEmpty()
                ? shopRepo.findByAddress(address)
                : shopRepo.findAll();

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
    private String addRecord(@RequestParam String address, @NotNull Model model) {
        if (isAddressEmpty(address, model))
            return "add/shopAdd";

        var newShop = new Shop(address);
        if (!saveRecord(newShop, model))
            return "add/shopAdd";

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
    private String editRecord(@RequestParam String address,
                              @NotNull @PathVariable Shop editShop,
                              @NotNull Model model) {
        if (isAddressEmpty(address, model))
            return "edit/shopEdit";

        editShop.setAddress(address);
        if (!saveRecord(editShop, model))
            return "edit/shopEdit";

        return "redirect:/shop";
    }

    @NotNull
    @GetMapping("/delete/{delShop}")
    private String deleteRecord(@NotNull @PathVariable Shop delShop) {
        dismissEmployees(delShop);
        shopRepo.delete(delShop);
        return "redirect:/shop";
    }

    private boolean isAddressEmpty(String address, Model model) {
        if (address == null || address.isBlank()) {
            model.addAttribute("errorMessage", "Адреса магазину не можу бути пустою!");
            return true;
        }
        return false;
    }


    private boolean saveRecord(Shop saveShop, Model model) {
        try {
            shopRepo.save(saveShop);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Адреса \"" + saveShop.getAddress() + "\" уже присутня в базі");
            return false;
        } catch (InvalidDataAccessResourceUsageException ignored) {
            model.addAttribute("errorMessage",
                    "Уведена адреса задовга для зберігання");
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