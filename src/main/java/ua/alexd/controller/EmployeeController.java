package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Employee;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

import static ua.alexd.specification.EmployeeSpecification.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeRepo employeeRepo;
    private final ShopRepo shopRepo;

    public EmployeeController(EmployeeRepo employeeRepo, ShopRepo shopRepo) {
        this.employeeRepo = employeeRepo;
        this.shopRepo = shopRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String secondName,
                              @RequestParam(required = false) String shopAddress,
                              @RequestParam(required = false) String isActive,
                              @NotNull Model model) {
        var employeesSpecification = Specification.where(firstNameEqual(firstName)).and(secondNameEqual(secondName))
                .and(shopAddressLike(shopAddress)).and(isActiveEqual(isActive));
        var employees = employeeRepo.findAll(employeesSpecification);

        model.addAttribute("employees", employees);
        return "/list/employeeList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        initDropDownChoices(model);
        return "add/employeeAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String firstName, @RequestParam String secondName,
                             @RequestParam String shopAddress, String isActive, @NotNull Model model) {
        if (isFieldsEmpty(firstName, secondName, model))
            return "add/employeeAdd";

        var shop = shopAddress != null ? shopRepo.findByAddress(shopAddress).get(0) : null;
        var newEmployee = new Employee(firstName, secondName, shop, true);
        employeeRepo.save(newEmployee);

        return "redirect:/employee";
    }

    @NotNull
    @GetMapping("/edit/{editEmployee}")
    private String editRecord(@PathVariable Employee editEmployee, @NotNull Model model) {
        model.addAttribute("editEmployee", editEmployee);
        initDropDownChoices(model);
        return "/edit/employeeEdit";
    }

    @NotNull
    @PostMapping("/edit/{editEmployee}")
    private String editRecord(@NotNull @PathVariable Employee editEmployee,
                              @RequestParam String firstName, @RequestParam String secondName,
                              @RequestParam String shopAddress, @RequestParam String isActive,
                              @NotNull Model model) {
        if (isFieldsEmpty(firstName, secondName, model))
            return "/edit/employeeEdit";

        editEmployee.setFirstName(firstName);
        editEmployee.setSecondName(secondName);
        var employeeShop = shopRepo.findByAddress(shopAddress).get(0);
        editEmployee.setShop(employeeShop);
        editEmployee.setActive(isActive.equals("Так"));
        employeeRepo.save(editEmployee);

        return "redirect:/employee";
    }

    @NotNull
    @GetMapping("/delete/{delEmployee}")
    private String deleteRecord(@NotNull @PathVariable Employee delEmployee) {
        employeeRepo.delete(delEmployee);
        return "redirect:/employee";
    }

    private boolean isFieldsEmpty(String firstName, String secondName, Model model) {
        if (firstName == null || secondName == null ||
                firstName.isBlank() || secondName.isBlank()) {
            model.addAttribute("errorMessage",
                    "Поля співробітника не можуть бути пустими!");
            initDropDownChoices(model);
            return true;
        }
        return false;
    }

    private void initDropDownChoices(@NotNull Model model) {
        var shopsAddresses = shopRepo.getAllAddresses();
        model.addAttribute("shopAddresses", shopsAddresses);
    }
}