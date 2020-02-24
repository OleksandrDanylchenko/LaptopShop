package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Availability;
import ua.alexd.repos.AvailabilityRepo;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.ShopRepo;

import java.sql.Date;
import java.text.ParseException;

import static ua.alexd.specification.AvailabilitySpecification.*;
import static ua.alexd.util.DateTimeConverter.isNonValidDate;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {
    private final AvailabilityRepo availabilityRepo;
    private final LaptopRepo laptopRepo;
    private final ShopRepo shopRepo;

    public AvailabilityController(AvailabilityRepo availabilityRepo, LaptopRepo laptopRepo, ShopRepo shopRepo) {
        this.availabilityRepo = availabilityRepo;
        this.laptopRepo = laptopRepo;
        this.shopRepo = shopRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) Integer price,
                              @RequestParam(required = false) Integer quantity,
                              @RequestParam(required = false) String laptopModel,
                              @RequestParam(required = false) String shopAddress,
                              @RequestParam(required = false, defaultValue = "0001-01-01") Date dateStart,
                              @RequestParam(required = false, defaultValue = "0001-01-01") Date dateEnd,
                              @NotNull Model model) throws ParseException {
        if (isNonValidDate(dateStart))
            dateStart = null;
        if (isNonValidDate(dateEnd))
            dateEnd = null;
        var availabilitySpecification = Specification.where(fullPriceEqual(price)).and(quantityEqual(quantity))
                .and(laptopModelLike(laptopModel)).and(shopAddressLike(shopAddress)).and(dateStartEqual(dateStart))
                .and(dateEndEqual(dateEnd));
        var availabilities = availabilityRepo.findAll(availabilitySpecification);
        model.addAttribute("availabilities", availabilities);

        return "/list/availabilityList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        initializeDropDownChoices(model);
        return "add/availabilityAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam Integer price, @RequestParam Integer quantity,
                             @RequestParam String laptopModel, @RequestParam String shopAddress,
                             @RequestParam(defaultValue = "0001-01-01") Date dateStart,
                             @RequestParam(defaultValue = "0001-01-01") Date dateEnd,
                             @NotNull Model model) throws ParseException {
        if (isNonValidDate(dateStart))
            dateStart = null;
        if (isNonValidDate(dateEnd))
            dateEnd = null;
        if (isFieldsEmpty(laptopModel, shopAddress, price, quantity, dateStart, dateEnd, model)) {
            initializeDropDownChoices(model);
            return "add/availabilityAdd";
        }

        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var shop = shopRepo.findByAddress(shopAddress).get(0);
        var newAvailability = new Availability(quantity, price, dateStart, dateEnd, shop, laptop);
        if (!saveRecord(newAvailability, model))
            return "add/availabilityAdd";

        return "redirect:/availability";
    }

    @NotNull
    @GetMapping("/edit/{editAvailability}")
    private String editRecord(@NotNull @PathVariable Availability editAvailability, @NotNull Model model) {
        model.addAttribute("editAvailability", editAvailability);
        initializeDropDownChoices(model);
        return "/edit/availabilityEdit";
    }

    @NotNull
    @PostMapping("/edit/{editAvailability}")
    private String editRecord(@PathVariable Availability editAvailability, @RequestParam Integer price,
                              @RequestParam Integer quantity, @RequestParam String laptopModel,
                              @RequestParam String shopAddress,
                              @RequestParam(defaultValue = "0001-01-01") Date dateStart,
                              @RequestParam(defaultValue = "0001-01-01") Date dateEnd,
                              @NotNull Model model) throws ParseException {
        if (isNonValidDate(dateStart))
            dateStart = null;
        if (isNonValidDate(dateEnd))
            dateEnd = null;
        if (isFieldsEmpty(laptopModel, shopAddress, price, quantity, dateStart, dateEnd, model))
            return "/edit/availabilityEdit";

        editAvailability.setDateStart(dateStart);
        editAvailability.setDateEnd(dateEnd);

        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editAvailability.setLaptop(laptop);

        var shop = shopRepo.findByAddress(shopAddress).get(0);
        editAvailability.setShop(shop);

        editAvailability.setPrice(price);
        editAvailability.setQuantity(quantity);

        if (!saveRecord(editAvailability, model))
            return "edit/availabilityEdit";

        return "redirect:/availability";
    }

    @NotNull
    @GetMapping("/delete/{delAvailability}")
    private String deleteRecord(@NotNull @PathVariable Availability delAvailability) {
        availabilityRepo.delete(delAvailability);
        return "redirect:/availability";
    }

    private boolean isFieldsEmpty(String laptopModel, String shopAddress, Integer price, Integer quantity,
                                  Date dateStart, Date dateEnd, Model model) {
        if (laptopModel == null || shopAddress == null || dateStart == null || dateEnd == null ||
                price == null || quantity == null || laptopModel.isBlank() || shopAddress.isBlank()) {
            model.addAttribute("errorMessage", "Поля запису про наявність не можуть бути пустими!");
            initializeDropDownChoices(model);
            return true;
        }
        return false;
    }

    private boolean saveRecord(Availability saveAvailability, Model model) {
        try {
            availabilityRepo.save(saveAvailability);
        } catch (DataIntegrityViolationException ignored) {
            model.addAttribute("errorMessage",
                    "Модель ноутбуку " + saveAvailability.getLaptop().getLabel().getModel()
                            + " уже присутня в базі");
            initializeDropDownChoices(model);
            return false;
        }
        return true;
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("shopAddresses", shopRepo.getAllAddresses());
    }
}