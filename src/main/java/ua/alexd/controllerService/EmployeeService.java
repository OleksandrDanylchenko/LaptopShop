package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Employee;
import ua.alexd.excelInteraction.imports.EmployeeExcelImporter;
import ua.alexd.excelInteraction.imports.UploadedFilesManager;
import ua.alexd.repos.EmployeeRepo;
import ua.alexd.repos.ShopRepo;

import java.io.IOException;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.specification.EmployeeSpecification.*;

@Service
@Lazy
public class EmployeeService {
    private final EmployeeRepo employeeRepo;

    private final ShopRepo shopRepo;

    private final EmployeeExcelImporter excelImporter;
    private final UploadedFilesManager filesManager;

    public EmployeeService(EmployeeRepo employeeRepo, ShopRepo shopRepo, EmployeeExcelImporter excelImporter,
                           UploadedFilesManager filesManager) {
        this.employeeRepo = employeeRepo;
        this.shopRepo = shopRepo;
        this.excelImporter = excelImporter;
        this.filesManager = filesManager;
    }

    public Iterable<Employee> loadEmployeeTable(String firstName, String secondName, String shopAddress,
                                                String isWorking, Model model) {
        var employeesSpecification = createEmployeeSpecification(firstName, secondName, shopAddress, isWorking);
        var employees = employeeRepo.findAll(employeesSpecification);
        initEmployeeChoices(model);
        return employees;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<Employee> createEmployeeSpecification(String firstName, String secondName,
                                                                String shopAddress, String isWorking) {
        return Specification.where(firstNameEqual(firstName)).and(secondNameEqual(secondName))
                .and(shopAddressLike(shopAddress)).and(isWorkingEqual(isWorking));
    }

    public void addEmployeeRecord(String firstName, String secondName, String shopAddress) {
        var shop = shopAddress != null ? shopRepo.findByAddress(shopAddress).get(0) : null;
        var newEmployee = new Employee(firstName, secondName, shop, true);
        employeeRepo.save(newEmployee);
    }

    public void editEmployeeRecord(String firstName, String secondName, String shopAddress,
                                   @NotNull String isWorking, @NotNull Employee editEmployee) {
        editEmployee.setFirstName(firstName);
        editEmployee.setSecondName(secondName);
        var employeeShop = shopRepo.findByAddress(shopAddress).get(0);
        editEmployee.setShop(employeeShop);
        editEmployee.setActive(isWorking.equals("Працюючий"));
        employeeRepo.save(editEmployee);
    }

    public boolean importExcelRecords(MultipartFile uploadingFile, Model model) {
        initEmployeeChoices(model);
        var employeeFilePath = "";
        try {
            employeeFilePath = filesManager.saveUploadingFile(uploadingFile);
            var newEmployees = excelImporter.importFile(employeeFilePath);
            newEmployees.forEach(employeeRepo::save);
            return true;
        } catch (IllegalArgumentException | IOException ignored) {
            deleteNonValidFile(employeeFilePath);
            return false;
        }
    }

    public void deleteRecord(Employee delEmployee) {
        employeeRepo.delete(delEmployee);
    }

    private void initEmployeeChoices(@NotNull Model model) {
        var shopsAddresses = shopRepo.getAllAddresses();
        model.addAttribute("shopAddresses", shopsAddresses);
    }
}