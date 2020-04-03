package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Basket;
import ua.alexd.domain.Client;
import ua.alexd.domain.Employee;
import ua.alexd.excelInteraction.imports.BasketExcelImporter;
import ua.alexd.graphService.BasketGraphService;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.ClientRepo;
import ua.alexd.repos.EmployeeRepo;

import java.io.IOException;
import java.time.LocalDateTime;

import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.BasketSpecification.*;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private final BasketRepo basketRepo;
    private static Iterable<Basket> lastOutputtedBaskets;

    private final ClientRepo clientRepo;
    private final EmployeeRepo employeeRepo;

    private final BasketExcelImporter excelImporter;
    private final BasketGraphService basketGraphService;

    public BasketController(BasketRepo basketRepo, ClientRepo clientRepo, EmployeeRepo employeeRepo,
                            BasketExcelImporter excelImporter, BasketGraphService basketGraphService) {
        this.basketRepo = basketRepo;
        this.clientRepo = clientRepo;
        this.employeeRepo = employeeRepo;
        this.excelImporter = excelImporter;
        this.basketGraphService = basketGraphService;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) Integer employeeId,
                              @RequestParam(required = false) String employeeFirstName,
                              @RequestParam(required = false) String employeeSecondName,
                              @RequestParam(required = false) String employeeShopAddress,
                              @RequestParam(required = false) Integer clientId,
                              @RequestParam(required = false) String clientFirstName,
                              @RequestParam(required = false) String clientSecondName,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                              @NotNull Model model) {
        var basketSpecification = Specification.where(employeeIdEqual(employeeId))
                .and(clientIdEqual(clientId))
                .and(employeeFirstNameEqual(employeeFirstName))
                .and(employeeSecondNameEqual(employeeSecondName))
                .and(employeeShopAddressEqual(employeeShopAddress))
                .and(clientFirstNameEqual(clientFirstName))
                .and(clientSecondNameEqual(clientSecondName))
                .and(dateTimeEqual(dateTime));
        var baskets = basketRepo.findAll(basketSpecification);
        lastOutputtedBaskets = baskets;
        model.addAttribute("baskets", baskets);
        initializeDropDownChoices(model);
        return "view/basket/table";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam Integer employeeId,
                             @RequestParam Integer clientId,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                             @NotNull Model model) {
        Employee employee = null;
        if (employeeRepo.findById(employeeId).isPresent())
            employee = employeeRepo.findById(employeeId).get();

        Client client = null;
        if (clientRepo.findById(clientId).isPresent())
            client = clientRepo.findById(clientId).get();

        var newBasket = new Basket(dateTime, employee, client);
        basketRepo.save(newBasket);

        return "redirect:/basket";
    }

    @NotNull
    @PostMapping("/edit/{editBasket}")
    private String editRecord(@RequestParam Integer editEmployeeId, @RequestParam Integer editClientId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime editDateTime,
                              @PathVariable Basket editBasket, @NotNull Model model) {
        Employee employee = null;
        if (employeeRepo.findById(editEmployeeId).isPresent())
            employee = employeeRepo.findById(editEmployeeId).get();
        editBasket.setEmployee(employee);

        Client client = null;
        if (clientRepo.findById(editClientId).isPresent())
            client = clientRepo.findById(editClientId).get();
        editBasket.setClient(client);
        editBasket.setDateTime(editDateTime);

        basketRepo.save(editBasket);

        return "redirect:/basket";
    }

    @NotNull
    @GetMapping("/graph")
    private String graphBaskets(@NotNull Model model) {
        var employeesDataPoints = basketGraphService.getEmployeesDataPoints();
        var clientsDataPoints = basketGraphService.getClientsDataPoints();
        model.addAttribute("employeesDataPoints", employeesDataPoints);
        model.addAttribute("clientsDataPoints", clientsDataPoints);
        return "graph/basketGraphs";
    }

    @NotNull
    @GetMapping("/importExcel")
    private String importExcel(@NotNull Model model) {
        initializeImportAttributes(model);
        return "excel/excelFilesUpload";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var basketFilePath = "";
        try {
            basketFilePath = saveUploadingFile(uploadingFile);
            var newBaskets = excelImporter.importFile(basketFilePath);
            newBaskets.forEach(basketRepo::save);
            return "redirect:/basket";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(basketFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці кошиків!");
            initializeImportAttributes(model);
            return "excel/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Basket.class.getSimpleName());
        model.addAttribute("tableName", "кошиків");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("baskets", lastOutputtedBaskets);
        return "basketExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delBasket}")
    private String deleteRecord(@NotNull @PathVariable Basket delBasket) {
        basketRepo.delete(delBasket);
        return "redirect:/basket";
    }

    private void initializeDropDownChoices(@NotNull Model model) {
        model.addAttribute("clientIds", clientRepo.getAllIds())
                .addAttribute("employeeIds", employeeRepo.getAllIds());
    }
}