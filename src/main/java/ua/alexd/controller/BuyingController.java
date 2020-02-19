package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Buying;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.BuyingRepo;
import ua.alexd.repos.LaptopRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ua.alexd.specification.BuyingSpecification.*;

@Controller
@RequestMapping("/buying")
public class BuyingController {
    private final BuyingRepo buyingRepo;
    private final BasketRepo basketRepo;
    private final LaptopRepo laptopRepo;

    public BuyingController(BuyingRepo buyingRepo, BasketRepo basketRepo, LaptopRepo laptopRepo) {
        this.buyingRepo = buyingRepo;
        this.basketRepo = basketRepo;
        this.laptopRepo = laptopRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) Integer basketId,
                              @RequestParam(required = false) String laptopModel,
                              @RequestParam(required = false) Integer totalPrice,
                              @RequestParam(required = false) String dateTimeStr,
                              @NotNull Model model) {
        var dateTime = getDateTime(dateTimeStr);
        var buyingSpecification = Specification.where(basketIdEqual(basketId))
                .and(laptopModelEqual(laptopModel)).and(totalPriceEqual(totalPrice)).and(dateTimeEqual(dateTime));
        var buyings = buyingRepo.findAll(buyingSpecification);

        model.addAttribute("basketId", basketId).addAttribute("laptopModel", laptopModel)
                .addAttribute("totalPrice", totalPrice).addAttribute("dateTimeStr", dateTimeStr)
                .addAttribute("buyings", buyings);

        return "/list/buyingList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        model.addAttribute("basketIds", basketRepo.getAllIds())
                .addAttribute("laptopModels", laptopRepo.getAllModels());
        return "add/buyingAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam Integer basketId, @RequestParam String laptopModel,
                             @RequestParam Integer totalPrice, @NotNull Model model) {
        if (isFieldsEmpty(basketId, laptopModel, totalPrice, model))
            return "add/buyingAdd";

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        var basket = basketRepo.findById(basketId).get();
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var newBuying = new Buying(totalPrice, laptop, basket);
        buyingRepo.save(newBuying);

        return "redirect:/buying";
    }

    @NotNull
    @GetMapping("/edit/{editBuying}")
    private String editRecord(@NotNull @PathVariable Buying editBuying, @NotNull Model model) {
        model.addAttribute("editBuying", editBuying)
                .addAttribute("basketIds", basketRepo.getAllIds())
                .addAttribute("laptopModels", laptopRepo.getAllModels())
                .addAttribute("dateTimeStr", getDateTimeStr(editBuying.getBasket().getDateTime()));
        return "/edit/buyingEdit";
    }

    @NotNull
    @PostMapping("/edit/{editBuying}")
    private String editRecord(@PathVariable Buying editBuying, @RequestParam Integer basketId,
                              @RequestParam String laptopModel, @RequestParam Integer totalPrice,
                              @NotNull Model model) {
        if (isFieldsEmpty(basketId, laptopModel, totalPrice, model))
            return "/edit/buyingEdit";

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        var basket = basketRepo.findById(basketId).get();
        editBuying.setBasket(basket);

        var laptop = laptopRepo.findByLabelModel(laptopModel);
        editBuying.setLaptop(laptop);

        editBuying.setTotalPrice(totalPrice);

        return "redirect:/buying";
    }

    @NotNull
    @GetMapping("/delete/{delBuying}")
    private String deleteRecord(@NotNull @PathVariable Buying delBuying) {
        buyingRepo.delete(delBuying);
        return "redirect:/buying";
    }

    private boolean isFieldsEmpty(Integer basketId, String laptopModel, Integer totalPrice, Model model) {
        if (basketId == null || totalPrice == null || laptopModel == null || laptopModel.isEmpty() ||
                basketRepo.findById(basketId).isEmpty()) {
            model.addAttribute("errorMessage",
                    "Поля покупки не можуть бути пустими!")
                    .addAttribute("basketIds", basketRepo.getAllIds())
                    .addAttribute("laptopModels", laptopRepo.getAllModels());
            return true;
        }
        return false;
    }

// TODO Fix doubling
    @NotNull
    private String getDateTimeStr(@NotNull LocalDateTime dateTime) {
        var dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");
        return dateTime.format(dateTimeFormat);
    }

    @Nullable
    private LocalDateTime getDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty())
            return null;
        var dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateTimeStr, dateTimeFormat);
    }
}