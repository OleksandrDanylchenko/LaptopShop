package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Buying;
import ua.alexd.excelInteraction.imports.BuyingExcelImporter;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.BuyingRepo;
import ua.alexd.repos.LaptopRepo;

import java.io.IOException;
import java.time.LocalDateTime;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.BuyingSpecification.*;

@Controller
@RequestMapping("/buying")
public class BuyingController {
    private final BuyingRepo buyingRepo;
    private static Iterable<Buying> lastOutputtedBuyings;

    private final BasketRepo basketRepo;
    private final LaptopRepo laptopRepo;

    private final BuyingExcelImporter excelImporter;

    public BuyingController(BuyingRepo buyingRepo, BasketRepo basketRepo, LaptopRepo laptopRepo,
                            BuyingExcelImporter excelImporter) {
        this.buyingRepo = buyingRepo;
        this.basketRepo = basketRepo;
        this.laptopRepo = laptopRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) Integer basketId,
                              @RequestParam(required = false) String laptopModel,
                              @RequestParam(required = false) Integer totalPrice,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                              @NotNull Model model) {
        var buyingSpecification = Specification.where(basketIdEqual(basketId))
                .and(laptopModelEqual(laptopModel)).and(totalPriceEqual(totalPrice)).and(dateTimeEqual(dateTime));
        var buyings = buyingRepo.findAll(buyingSpecification);
        lastOutputtedBuyings = buyings;
        model.addAttribute("buyings", buyings);
        initializeDropDownChoices(model);
        return "view/buying/table";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam Integer basketId, @RequestParam String laptopModel,
                             @RequestParam Integer totalPrice, @NotNull Model model) {
        Basket basket = null;
        if (basketRepo.findById(basketId).isPresent())
            basket = basketRepo.findById(basketId).get();
        var laptop = laptopRepo.findByLabelModel(laptopModel);
        var newBuying = new Buying(totalPrice, laptop, basket);
        buyingRepo.save(newBuying);
        return "redirect:/buying";
    }

    @NotNull
    @PostMapping("/edit/{editBuying}")
    private String editRecord(@RequestParam Integer editBasketId, @RequestParam String editLaptopModel,
                              @RequestParam Integer editTotalPrice, @PathVariable Buying editBuying,
                              @NotNull Model model) {
        Basket basket = null;
        if (basketRepo.findById(editBasketId).isPresent())
            basket = basketRepo.findById(editBasketId).get();
        editBuying.setBasket(basket);
        var laptop = laptopRepo.findByLabelModel(editLaptopModel);
        editBuying.setLaptop(laptop);
        editBuying.setTotalPrice(editTotalPrice);

        buyingRepo.save(editBuying);
        return "redirect:/buying";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var buyingFilePath = "";
        try {
            buyingFilePath = saveUploadingFile(uploadingFile);
            var newBuyings = excelImporter.importFile(buyingFilePath);
            newBuyings.forEach(buyingRepo::save);
            return "redirect:/buying";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(buyingFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці покупок!");
            model.addAttribute("buyings", lastOutputtedBuyings);
            initializeDropDownChoices(model);
            return "view/buying/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("buyings", lastOutputtedBuyings);
        return "buyingExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delBuying}")
    private String deleteRecord(@NotNull @PathVariable Buying delBuying) {
        buyingRepo.delete(delBuying);
        return "redirect:/buying";
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("basketIds", basketRepo.getAllIds())
                .addAttribute("laptopModels", laptopRepo.getAllModels());
    }
}