package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Basket;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.ClientRepo;
import ua.alexd.repos.EmployeeRepo;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ua.alexd.specification.BasketSpecification.*;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private final BasketRepo basketRepo;
    private final ClientRepo clientRepo;
    private final EmployeeRepo employeeRepo;

    public BasketController(BasketRepo basketRepo, ClientRepo clientRepo, EmployeeRepo employeeRepo) {
        this.basketRepo = basketRepo;
        this.clientRepo = clientRepo;
        this.employeeRepo = employeeRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) Integer employeeId,
                              @RequestParam(required = false) String employeeFirstName,
                              @RequestParam(required = false) String employeeSecondName,
                              @RequestParam(required = false) String employeeShopAddress,
                              @RequestParam(required = false) Integer clientId,
                              @RequestParam(required = false) String clientFirstName,
                              @RequestParam(required = false) String clientSecondName,
                              @RequestParam(required = false) String dateTimeStr,
                              @NotNull Model model) {
        var dateTime = getDateTime(dateTimeStr);
        var basketSpecification = Specification.where(employeeIdEqual(employeeId))
                .and(clientIdEqual(clientId))
                .and(employeeFirstNameEqual(employeeFirstName))
                .and(employeeSecondNameEqual(employeeSecondName))
                .and(employeeShopAddressEqual(employeeShopAddress))
                .and(clientFirstNameEqual(clientFirstName))
                .and(clientSecondNameEqual(clientSecondName))
                .and(dateTimeEqual(dateTime));
        var baskets = basketRepo.findAll(basketSpecification);

        model.addAttribute("employeeId", employeeId)
                .addAttribute("clientId", clientId)
                .addAttribute("employeeFirstName", employeeFirstName)
                .addAttribute("employeeSecondName", employeeSecondName)
                .addAttribute("employeeShopAddress", employeeShopAddress)
                .addAttribute("clientFirstName", clientFirstName)
                .addAttribute("clientSecondName", clientSecondName)
                .addAttribute("dateTimeStr", dateTimeStr)
                .addAttribute("baskets", baskets);

        return "/list/basketList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        model.addAttribute("clientIds", clientRepo.getAllIds())
                .addAttribute("employeeIds", employeeRepo.getAllIds());
        return "add/basketAdd";
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam Integer employeeId, @RequestParam Integer clientId,
                             @RequestParam String dateTimeStr, @NotNull Model model) {
        if (isFieldsEmpty(employeeId, clientId, dateTimeStr, model))
            return "/add/basketAdd";

        var dateTime = getDateTime(dateTimeStr);
        var employee = employeeRepo.findById(employeeId).get();
        var client = clientRepo.findById(clientId).get();
        var newBasket = new Basket(dateTime, employee, client);
        basketRepo.save(newBasket);

        return "redirect:/basket";
    }

    @NotNull
    @GetMapping("/edit/{editBasket}")
    private String editRecord(@NotNull @PathVariable Basket editBasket, @NotNull Model model) {
        model.addAttribute("editBasket", editBasket)
                .addAttribute("clientIds", clientRepo.getAllIds())
                .addAttribute("employeeIds", employeeRepo.getAllIds())
                .addAttribute("dateTimeStr", getDateTimeStr(editBasket.getDateTime()));
        return "/edit/basketEdit";
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @NotNull
    @PostMapping("/edit/{editBasket}")
    private String saveEditedRecord(@PathVariable Basket editBasket, @RequestParam Integer employeeId,
                                    @RequestParam Integer clientId, @RequestParam String dateTimeStr,
                                    @NotNull Model model) {
        if (isFieldsEmpty(employeeId, clientId, dateTimeStr, model))
            return "/edit/basketEdit";

        var employee = employeeRepo.findById(employeeId).get();
        editBasket.setEmployee(employee);

        var client = clientRepo.findById(clientId).get();
        editBasket.setClient(client);

        var dateTime = getDateTime(dateTimeStr);
        editBasket.setDateTime(dateTime);

        basketRepo.save(editBasket);

        return "redirect:/basket";
    }

    @NotNull
    @GetMapping("/delete/{delBasket}")
    private String deleteRecord(@NotNull @PathVariable Basket delBasket) {
        basketRepo.delete(delBasket);
        return "redirect:/basket";
    }

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

    private boolean isFieldsEmpty(Integer employeeId, Integer clientId, String dateTimeStr, Model model) {
        if (employeeId == null || clientId == null || dateTimeStr == null || dateTimeStr.isEmpty() ||
                employeeRepo.findById(employeeId).isEmpty() || clientRepo.findById(clientId).isEmpty()) {
            model.addAttribute("errorMessage",
                    "Поля кошику не можуть бути пустими!")
                    .addAttribute("clientIds", clientRepo.getAllIds())
                    .addAttribute("employeeIds", employeeRepo.getAllIds())
                    .addAttribute("dateTimeStr", dateTimeStr);
            return true;
        }
        return false;
    }
}