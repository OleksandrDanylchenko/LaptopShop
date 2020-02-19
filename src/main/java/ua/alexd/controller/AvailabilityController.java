package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Availability;
import ua.alexd.domain.Client;
import ua.alexd.repos.AvailabilityRepo;
import ua.alexd.repos.LaptopRepo;
import ua.alexd.repos.ShopRepo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ua.alexd.specification.AvailabilitySpecification.*;

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
                              @RequestParam(required = false) String dateStartStr,
                              @RequestParam(required = false) String dateEndStr,
                              @NotNull Model model) throws ParseException {
        var dateStart = getDate(dateStartStr);
        var dateEnd = getDate(dateEndStr);
        var availabilitySpecification = Specification.where(fullPriceEqual(price)).and(quantityEqual(quantity))
                .and(laptopModelLike(laptopModel)).and(shopAddressLike(shopAddress)).and(dateStartEqual(dateStart))
                .and(dateEndEqual(dateEnd));
        var availabilities = availabilityRepo.findAll(availabilitySpecification);

        model.addAttribute("price", price).addAttribute("quantity", quantity)
                .addAttribute("laptopModel", laptopModel).addAttribute("shopAddress", shopAddress)
                .addAttribute("dateStartStr", dateStartStr).addAttribute("dateEndStr", dateEndStr)
                .addAttribute("availabilities", availabilities);
        return "/list/availabilityList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        model.addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("shopAddresses", shopRepo.getAllAddresses());
        return "add/availabilityAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam Integer price, @RequestParam Integer quantity,
                             @RequestParam String laptopModel, @RequestParam String shopAddress,
                             @RequestParam String dateStartStr, @RequestParam String dateEndStr,
                             @NotNull Model model) throws ParseException {
        if (isFieldsEmpty(laptopModel, shopAddress, dateStartStr, dateEndStr, model)) {
            model.addAttribute("price", price)
                    .addAttribute("quantity", quantity)
                    .addAttribute("laptopModel", laptopModel)
                    .addAttribute("shopAddress", shopAddress)
                    .addAttribute("dateStartStr", dateStartStr)
                    .addAttribute("dateEndStr", dateEndStr)
                    .addAttribute("laptopModels", laptopRepo.getAllModels())
                    .addAttribute("shopAddresses", shopRepo.getAllAddresses());
            return "add/availabilityAdd";
        }

        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var shop = shopRepo.findByAddress(shopAddress);
        var dateStart = getDate(dateStartStr);
        var dateEnd = getDate(dateEndStr);
        var newAvailability = new Availability(quantity, price, dateStart, dateEnd, shop, laptop);
        availabilityRepo.save(newAvailability);

        return "redirect:/availability";
    }

    @NotNull
    @GetMapping("/edit/{editAvailability}")
    private String editRecord(@NotNull @PathVariable Availability editAvailability, @NotNull Model model) {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        var dateStartStr = dateFormat.format(editAvailability.getDateStart());
        var dateEndStr = dateFormat.format(editAvailability.getDateEnd());
        model.addAttribute("editAvailability", editAvailability)
                .addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("shopAddresses", shopRepo.getAllAddresses())
                .addAttribute("dateStartStr", dateStartStr)
                .addAttribute("dateEndStr", dateEndStr);
        return "/edit/availabilityEdit";
    }

    @NotNull
    @PostMapping("/edit/{editAvailability}")
    private String saveEditedRecord(@PathVariable Availability editAvailability, @RequestParam Integer price,
                                    @RequestParam Integer quantity, @RequestParam String laptopModel,
                                    @RequestParam String shopAddress, @RequestParam String dateStartStr,
                                    @RequestParam String dateEndStr, @NotNull Model model) throws ParseException {
        if (isFieldsEmpty(laptopModel, shopAddress, dateStartStr, dateEndStr, model)) {
            model.addAttribute("laptopModels", laptopRepo.getAllModels())
                    .addAttribute("shopAddresses", shopRepo.getAllAddresses())
                    .addAttribute("dateStartStr", dateStartStr)
                    .addAttribute("dateEndStr", dateEndStr);
            return "/edit/availabilityEdit";
        }

        var dateStart = getDate(dateStartStr);
        editAvailability.setDateStart(dateStart);

        var dateEnd = getDate(dateEndStr);
        editAvailability.setDateEnd(dateEnd);

        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editAvailability.setLaptop(laptop);

        var shop = shopRepo.findByAddress(shopAddress);
        editAvailability.setShop(shop);

        editAvailability.setPrice(price);
        editAvailability.setQuantity(quantity);

        return "redirect:/availability";
    }

    @NotNull
    @GetMapping("/delete/{delAvailability}")
    private String deleteRecord(@NotNull @PathVariable Availability delAvailability) {
        availabilityRepo.delete(delAvailability);
        return "redirect:/availability";
    }

    private boolean isFieldsEmpty(String laptopModel, String shopAddress,
                                  String dateStartStr, String dateEndStr, Model model) {
        if (laptopModel == null || shopAddress == null || dateStartStr == null || dateEndStr == null ||
                laptopModel.isEmpty() || shopAddress.isEmpty() || dateStartStr.isEmpty() || dateEndStr.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Поля запису про наявність не можуть бути пустими!");
            return true;
        }
        return false;
    }

    // TODO Fix doubling
    @Nullable
    private static Date getDate(String dateStr) throws ParseException {
        final var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateStr == null || dateStr.isEmpty()
                ? null
                : new Date(dateFormat.parse(dateStr).getTime());
    }
}