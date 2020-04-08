package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Employee;
import ua.alexd.excelInteraction.imports.EmployeeExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.EmployeeSpecification.*;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class EmployeeController {
    private final EmployeeRepo employeeRepo;
    private static Iterable<Employee> lastOutputtedEmployees;

    private final ShopRepo shopRepo;

    private final EmployeeExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public EmployeeController(EmployeeRepo employeeRepo, ShopRepo shopRepo, EmployeeExcelImporter excelImporter,
                              UploadedFilesManager filesManager) {
        this.employeeRepo = employeeRepo;
        this.shopRepo = shopRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String secondName,
                             @RequestParam(required = false) String shopAddress,
                             @RequestParam(required = false) String isWorking,
                             @NotNull Model model) {
        var employeesSpecification = Specification.where(firstNameEqual(firstName)).and(secondNameEqual(secondName))
                .and(shopAddressLike(shopAddress)).and(isWorkingEqual(isWorking));
        var employees = employeeRepo.findAll(employeesSpecification);
        lastOutputtedEmployees = employees;
        model.addAttribute("employees", employees);
        initDropDownChoices(model);
        return "view/employee/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@RequestParam String firstName, @RequestParam String secondName,
                            @RequestParam String shopAddress, @NotNull Model model) {
        var shop = shopAddress != null ? shopRepo.findByAddress(shopAddress).get(0) : null;
        var newEmployee = new Employee(firstName, secondName, shop, true);
        employeeRepo.save(newEmployee);
        return "redirect:/employee";
    }

    @NotNull
    @PostMapping("/edit/{editEmployee}")
    public String editRecord(@NotNull @PathVariable Employee editEmployee,
                             @RequestParam String editFirstName, @RequestParam String editSecondName,
                             @RequestParam String editShopAddress, @NotNull @RequestParam String editIsWorking,
                             @NotNull Model model) {
        editEmployee.setFirstName(editFirstName);
        editEmployee.setSecondName(editSecondName);
        var employeeShop = shopRepo.findByAddress(editShopAddress).get(0);
        editEmployee.setShop(employeeShop);
        editEmployee.setActive(editIsWorking.equals("Працюючий"));
        employeeRepo.save(editEmployee);
        return "redirect:/employee";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var employeeFilePath = "";
        try {
            employeeFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newEmployees = excelImporter.importFile(employeeFilePath);
            newEmployees.forEach(employeeRepo::save);
            return "redirect:/employee";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(employeeFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці співробітників!");
            model.addAttribute("employees", lastOutputtedEmployees);
            initDropDownChoices(model);
            return "view/employee/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("employees", lastOutputtedEmployees);
        return "employeeExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delEmployee}")
    public String deleteRecord(@NotNull @PathVariable Employee delEmployee) {
        employeeRepo.delete(delEmployee);
        return "redirect:/employee";
    }

    private void initDropDownChoices(@NotNull Model model) {
        var shopsAddresses = shopRepo.getAllAddresses();
        model.addAttribute("shopAddresses", shopsAddresses);
    }
}