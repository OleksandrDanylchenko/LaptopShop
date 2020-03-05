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
import ua.alexd.excelUtils.imports.BasketExcelImporter;
import ua.alexd.repos.BasketRepo;
import ua.alexd.repos.ClientRepo;
import ua.alexd.repos.EmployeeRepo;

import java.io.IOException;
import java.time.LocalDateTime;

import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.BasketSpecification.*;

@Controller
@RequestMapping("/basket")
public class BasketController {
    private final BasketRepo basketRepo;
    private static Iterable<Basket> lastOutputtedBaskets;

    private final ClientRepo clientRepo;
    private final EmployeeRepo employeeRepo;

    public BasketController(BasketRepo basketRepo, ClientRepo clientRepo, EmployeeRepo employeeRepo) {
        this.basketRepo = basketRepo;
        this.clientRepo = clientRepo;
        this.employeeRepo = employeeRepo;
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
        model.addAttribute("baskets", baskets);
        lastOutputtedBaskets = baskets;
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
                             @RequestParam(required = false)
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
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
    @GetMapping("/edit/{editBasket}")
    private String editRecord(@NotNull @PathVariable Basket editBasket, @NotNull Model model) {
        model.addAttribute("editBasket", editBasket);
        initializeDropDownChoices(model);
        return "/edit/basketEdit";
    }

    @NotNull
    @PostMapping("/edit/{editBasket}")
    private String editRecord(@PathVariable Basket editBasket,
                              @RequestParam(required = false, defaultValue = "0") Integer employeeId,
                              @RequestParam(required = false, defaultValue = "0") Integer clientId,
                              @RequestParam(required = false)
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                              @NotNull Model model) {
        Employee employee = null;
        if (employeeRepo.findById(employeeId).isPresent())
            employee = employeeRepo.findById(employeeId).get();
        editBasket.setEmployee(employee);

        Client client = null;
        if (clientRepo.findById(clientId).isPresent())
            client = clientRepo.findById(clientId).get();
        editBasket.setClient(client);

        editBasket.setDateTime(dateTime);

        basketRepo.save(editBasket);

        return "redirect:/basket";
    }

    @NotNull
    @GetMapping("/importExcel")
    private String importExcel(@NotNull Model model) {
        initializeImportAttributes(model);
        return "parts/excelFilesUpload";
    }

    @NotNull
    @PostMapping("/importExcel")
    private String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var basketFilePath = "";
        try {
            basketFilePath = saveUploadingFile(uploadingFile);
            var newBaskets = BasketExcelImporter.importFiles(basketFilePath, employeeRepo, clientRepo);
            newBaskets.forEach(basketRepo::save);
            return "redirect:/basket";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(basketFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці кошиків!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
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