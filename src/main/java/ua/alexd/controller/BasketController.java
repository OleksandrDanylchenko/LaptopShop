package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.BasketService;
import ua.alexd.domain.Basket;
import ua.alexd.graphService.BasketGraphService;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/basket")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class BasketController {
    private final BasketService basketService;
    private Iterable<Basket> lastOutputtedBaskets;

    private final BasketGraphService graphService;

    public BasketController(BasketService basketService, BasketGraphService graphService) {
        this.basketService = basketService;
        this.graphService = graphService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) Integer employeeId,
                             @RequestParam(required = false) String employeeFirstName,
                             @RequestParam(required = false) String employeeSecondName,
                             @RequestParam(required = false) String employeeShopAddress,
                             @RequestParam(required = false) Integer clientId,
                             @RequestParam(required = false) String clientFirstName,
                             @RequestParam(required = false) String clientSecondName,
                             @RequestParam(required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                             @NotNull Model model) {
        var baskets = basketService.loadBasketTable(employeeId, employeeFirstName, employeeSecondName,
                employeeShopAddress, clientId, clientFirstName, clientSecondName, dateTime, model);
        lastOutputtedBaskets = baskets;
        model.addAttribute("baskets", baskets);
        return "view/basket/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@RequestParam Integer employeeId,
                            @RequestParam Integer clientId,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        basketService.addBasketRecord(employeeId, clientId, dateTime);
        return "redirect:/basket";
    }

    @NotNull
    @PostMapping("/edit/{editBasket}")
    public String editRecord(@RequestParam Integer editEmployeeId, @RequestParam Integer editClientId,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime editDateTime,
                             @PathVariable Basket editBasket) {
        basketService.editBasketRecord(editEmployeeId, editClientId, editDateTime, editBasket);
        return "redirect:/basket";
    }

    @NotNull
    @GetMapping("/graph")
    public String graphBaskets(@NotNull Model model) {
        var employeesDataPoints = graphService.getEmployeesDataPoints();
        var clientsDataPoints = graphService.getClientsDataPoints();
        model.addAttribute("employeesDataPoints", employeesDataPoints);
        model.addAttribute("clientsDataPoints", clientsDataPoints);
        return "graph/basketGraphs";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = basketService.importExcelRecords(uploadingFile, model);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці кошиків!");
            model.addAttribute("baskets", lastOutputtedBaskets);
            return "view/basket/table";
        }
        return "redirect:/basket";
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("baskets", lastOutputtedBaskets);
        return "basketExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delBasket}")
    public String deleteRecord(@NotNull @PathVariable Basket delBasket) {
        basketService.deleteRecord(delBasket);
        return "redirect:/basket";
    }
}