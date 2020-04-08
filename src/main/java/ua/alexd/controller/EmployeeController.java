package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.EmployeeService;
import ua.alexd.domain.Employee;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class EmployeeController {
    private final EmployeeService employeeService;
    private Iterable<Employee> lastOutputtedEmployees;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String secondName,
                             @RequestParam(required = false) String shopAddress,
                             @RequestParam(required = false) String isWorking,
                             @NotNull Model model) {
        var employees = employeeService.loadEmployeeTable(firstName, secondName, shopAddress, isWorking, model);
        lastOutputtedEmployees = employees;
        model.addAttribute("employees", employees);
        return "view/employee/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@RequestParam String firstName, @RequestParam String secondName,
                            @RequestParam String shopAddress, @NotNull Model model) {
        employeeService.addEmployeeRecord(firstName, secondName, shopAddress);
        return "redirect:/employee";
    }

    @NotNull
    @PostMapping("/edit/{editEmployee}")
    public String editRecord(@RequestParam String editFirstName, @RequestParam String editSecondName,
                             @RequestParam String editShopAddress, @NotNull @RequestParam String editIsWorking,
                             @NotNull @PathVariable Employee editEmployee, @NotNull Model model) {
        employeeService.editEmployeeRecord(editFirstName, editSecondName, editShopAddress, editIsWorking, editEmployee);
        return "redirect:/employee";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = employeeService.importExcelRecords(uploadingFile, model);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці співробітників!");
            model.addAttribute("employees", lastOutputtedEmployees);
            return "view/employee/table";
        }
        return "redirect:/employee";
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
        employeeService.deleteRecord(delEmployee);
        return "redirect:/employee";
    }
}