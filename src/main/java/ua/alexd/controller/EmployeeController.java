package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Employee;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

import java.util.ArrayList;

//TODO Add posts
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
    private String getRecords(@NotNull @RequestParam(required = false, defaultValue = "") String firstName,
                              @RequestParam(required = false, defaultValue = "") String secondName,
                              @NotNull Model model) {
        Iterable<Employee> employees;

        if (!firstName.isEmpty() && secondName.isEmpty())
            employees = employeeRepo.findByFirstName(firstName);
        else if (!secondName.isEmpty() && firstName.isEmpty())
            employees = employeeRepo.findBySecondName(secondName);
        else if (!firstName.isEmpty() && !secondName.isEmpty())
            employees = employeeRepo.findByFirstNameAndSecondName(firstName, secondName);
        else
            employees = employeeRepo.findAll();

        var shops = shopRepo.findAll();
        var shopsIds = new ArrayList<Integer>();
        for (var shop : shops)
            shopsIds.add(shop.getId());

        model.addAttribute("employees", employees);
        model.addAttribute("shopIds", shopsIds);
        model.addAttribute("firstName", firstName);
        model.addAttribute("secondName", secondName);
        return "employee";
    }

    @NotNull
    @PostMapping
    private String addRecord(@RequestParam String firstName, @RequestParam String secondName, @RequestParam int shopId,
                             @NotNull Model model) {
        var shop = shopRepo.findById(shopId);
        if (shop.isPresent()) {
            var newEmployee = new Employee(firstName, secondName, shop.get());
            employeeRepo.save(newEmployee);
        }

        var employees = employeeRepo.findAll();
        var shops = shopRepo.findAll();
        var shopsIds = new ArrayList<Integer>();
        for (var s : shops)
            shopsIds.add(s.getId());

        model.addAttribute("employees", employees);
        model.addAttribute("shopIds", shopsIds);
        return "employee";
    }

    //TODO
//    @NotNull
//    @PostMapping("/edit/{editEmployee}")
//    private String saveEditedRecord(
//                                    @NotNull @PathVariable("editShop") Shop editShop) {
//        editShop.setAddress(address);
//        shopRepo.save(editShop);
//        return "redirect:/shop";
//    }

    @NotNull
    @GetMapping("/delete/{delEmployee}")
    private String deleteRecord(@NotNull @PathVariable("delEmployee") Employee delEmployee) {
        employeeRepo.delete(delEmployee);
        return "redirect:/employee";
    }
}