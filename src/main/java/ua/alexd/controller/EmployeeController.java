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
                              @NotNull Model model) {
        var employeesSpecification = Specification.where(firstNameEqual(firstName))
                .and(secondNameEqual(secondName)).and(shopAddressLike(shopAddress));
        var employees = employeeRepo.findAll(employeesSpecification);

        var shopsAddresses = shopRepo.getAllAddresses();

        model.addAttribute("employees", employees);
        model.addAttribute("firstName", firstName);
        model.addAttribute("secondName", secondName);
        model.addAttribute("shopAddress", shopAddress);
        model.addAttribute("shopAddresses", shopsAddresses);
        return "/list/employeeList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        var shopsAddresses = shopRepo.getAllAddresses();
        model.addAttribute("shopAddresses", shopsAddresses);
        return "add/employeeAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String firstName, @RequestParam String secondName,
                             @RequestParam String shopAddress, @NotNull Model model) {
        if (isFieldsEmpty(firstName, secondName, shopAddress, model))
            return "add/employeeAdd";

        var shop = shopRepo.findByAddress(shopAddress);
        var newEmployee = new Employee(firstName, secondName, shop.get(0));
        employeeRepo.save(newEmployee);

        var employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "redirect:/employee";
    }

    @NotNull
    @GetMapping("/edit/{editEmployee}")
    private String editRecord(@PathVariable Employee editEmployee, @NotNull Model model) {
        var shopsAddresses = shopRepo.getAllAddresses();
        model.addAttribute("editEmployee", editEmployee);
        model.addAttribute("shopAddresses", shopsAddresses);
        return "/edit/employeeEdit";
    }

    @NotNull
    @PostMapping("/edit/{editEmployee}")
    private String saveEditedRecord(@NotNull @PathVariable Employee editEmployee,
                                    @RequestParam String firstName, @RequestParam String secondName,
                                    @RequestParam String shopAddress, @NotNull Model model) {
        if (isFieldsEmpty(firstName, secondName, shopAddress, model))
            return "/edit/employeeEdit";

        editEmployee.setFirstName(firstName);
        editEmployee.setSecondName(secondName);
        var employeeShop = shopRepo.findByAddress(shopAddress);
        editEmployee.setShop(employeeShop.get(0));
        employeeRepo.save(editEmployee);
        return "redirect:/employee";
    }

    @NotNull
    @GetMapping("/delete/{delEmployee}")
    private String deleteRecord(@NotNull @PathVariable Employee delEmployee) {
        employeeRepo.delete(delEmployee);
        return "redirect:/employee";
    }

    private boolean isFieldsEmpty(String firstName, String secondName, String shopAddress, Model model) {
        if (firstName == null || secondName == null || shopAddress == null ||
                firstName.isEmpty() || secondName.isEmpty() || shopAddress.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Поля співробітника не можуть бути пустими!");
            var shopsAddresses = shopRepo.getAllAddresses();
            model.addAttribute("shopAddresses", shopsAddresses);
            return true;
        }
        return false;
    }
}