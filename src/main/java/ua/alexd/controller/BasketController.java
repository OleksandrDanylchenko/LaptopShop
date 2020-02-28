package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Client;
import ua.alexd.domain.Employee;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.ClientRepo;
import ua.alexd.repos.EmployeeRepo;

import static ua.alexd.specification.BasketSpecification.*;
import static ua.alexd.util.DateTimeConverter.getDateTime;
import static ua.alexd.util.DateTimeConverter.getDateTimeStr;

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
        model.addAttribute("baskets", baskets);

        return "/list/basketList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        initializeDropDownChoices(model);
        return "add/basketAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam(required = false, defaultValue = "0") Integer employeeId,
                             @RequestParam(required = false, defaultValue = "0") Integer clientId,
                             @RequestParam String dateTimeStr,
                             @NotNull Model model) {
        if (isFieldsEmpty(dateTimeStr, model))
            return "/add/basketAdd";

        Employee employee = null;
        if (employeeRepo.findById(employeeId).isPresent())
            employee = employeeRepo.findById(employeeId).get();

        Client client = null;
        if (clientRepo.findById(clientId).isPresent())
            client = clientRepo.findById(clientId).get();

        var dateTime = getDateTime(dateTimeStr);

        var newBasket = new Basket(dateTime, employee, client);
        basketRepo.save(newBasket);

        return "redirect:/basket";
    }

    @NotNull
    @GetMapping("/edit/{editBasket}")
    private String editRecord(@NotNull @PathVariable Basket editBasket, @NotNull Model model) {
        var a = getDateTimeStr(editBasket.getDateTime());
        model.addAttribute("editBasket", editBasket)
                .addAttribute("dateTimeStr", getDateTimeStr(editBasket.getDateTime()));
        initializeDropDownChoices(model);
        return "/edit/basketEdit";
    }

    @NotNull
    @PostMapping("/edit/{editBasket}")
    private String editRecord(@PathVariable Basket editBasket,
                              @RequestParam(required = false, defaultValue = "0") Integer employeeId,
                              @RequestParam(required = false, defaultValue = "0") Integer clientId,
                              @RequestParam String dateTimeStr,
                              @NotNull Model model) {
        if (isFieldsEmpty(dateTimeStr, model))
            return "/edit/basketEdit";

        Employee employee = null;
        if (employeeRepo.findById(employeeId).isPresent())
            employee = employeeRepo.findById(employeeId).get();
        editBasket.setEmployee(employee);

        Client client = null;
        if (clientRepo.findById(clientId).isPresent())
            client = clientRepo.findById(clientId).get();
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

    private boolean isFieldsEmpty(String dateTimeStr, Model model) {
        if (dateTimeStr == null || dateTimeStr.isBlank()) {
            model.addAttribute("errorMessage", "Поля кошику не можуть бути пустими!");
            initializeDropDownChoices(model);
            return true;
        }
        return false;
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("clientIds", clientRepo.getAllIds())
                .addAttribute("employeeIds", employeeRepo.getAllIds());
    }
}