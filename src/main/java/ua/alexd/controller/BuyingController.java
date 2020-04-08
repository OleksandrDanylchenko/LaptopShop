package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.BuyingService;
import ua.alexd.domain.Buying;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/buying")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class BuyingController {
    private final BuyingService buyingService;
    private Iterable<Buying> lastOutputtedBuyings;

    public BuyingController(BuyingService buyingService) {
        this.buyingService = buyingService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) Integer basketId,
                             @RequestParam(required = false) String laptopModel,
                             @RequestParam(required = false) Integer totalPrice,
                             @RequestParam(required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                             @NotNull Model model) {
        var buyings = buyingService.loadBuyingTable(basketId, laptopModel, totalPrice, dateTime, model);
        lastOutputtedBuyings = buyings;
        model.addAttribute("buyings", buyings);
        return "view/buying/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@RequestParam Integer basketId, @RequestParam String laptopModel,
                            @RequestParam Integer totalPrice) {
        buyingService.addBuyingRecord(basketId, laptopModel, totalPrice);
        return "redirect:/buying";
    }

    @NotNull
    @PostMapping("/edit/{editBuying}")
    public String editRecord(@RequestParam Integer editBasketId, @RequestParam String editLaptopModel,
                             @RequestParam Integer editTotalPrice, @PathVariable Buying editBuying) {
        buyingService.editBuyingRecord(editBasketId, editLaptopModel, editTotalPrice, editBuying);
        return "redirect:/buying";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = buyingService.importExcelRecords(uploadingFile, model);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці покупок!");
            model.addAttribute("buyings", lastOutputtedBuyings);
            return "view/buying/table";
        }
        return "redirect:/buying";
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("buyings", lastOutputtedBuyings);
        return "buyingExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delBuying}")
    public String deleteRecord(@NotNull @PathVariable Buying delBuying) {
        buyingService.deleteRecord(delBuying);
        return "redirect:/buying";
    }
}