package ua.alexd.controller;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Client;
import ua.alexd.excelUtils.imports.ClientExcelImporter;
import ua.alexd.repos.ClientRepo;

import java.io.IOException;
import java.sql.Date;

import static ua.alexd.dateTimeUtils.DateTimeChecker.isNonValidDate;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelUtils.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.ClientSpecification.*;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientRepo clientRepo;
    private static Iterable<Client> lastOutputtedClients;

    private final ClientExcelImporter excelImporter;

    public ClientController(ClientRepo clientRepo, ClientExcelImporter excelImporter) {
        this.clientRepo = clientRepo;
        this.excelImporter = excelImporter;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String secondName,
                              @RequestParam(required = false, defaultValue = "0001-01-01") Date dateReg,
                              @NotNull Model model) {
        if (isNonValidDate(dateReg))
            dateReg = null;
        var clientSpecification = Specification.where(firstNameEqual(firstName))
                .and(secondNameEqual(secondName)).and(dateRegEqual(dateReg));
        var clients = clientRepo.findAll(clientSpecification);
        lastOutputtedClients = clients;
        model.addAttribute("clients", clients);
        return "/list/clientList";
    }

    @NotNull
    @GetMapping("/add")
    private String addRecord(@NotNull Model model) {
        return "add/clientAdd";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String firstName, @RequestParam String secondName,
                             @RequestParam Date dateReg, @NotNull Model model) {
        if (isFieldsValid(firstName, secondName, dateReg)) {
            model.addAttribute("errorMessage", "Ім'я чи прізвище нового клієнта задано некоректно!");
            return "add/clientAdd";
        }

        var newClient = new Client(firstName, secondName, dateReg);
        clientRepo.save(newClient);

        return "redirect:/client";
    }


    @NotNull
    @GetMapping("/edit/{editClient}")
    private String editRecord(@NotNull @PathVariable Client editClient, @NotNull Model model) {
        model.addAttribute("editClient", editClient);
        return "/edit/clientEdit";
    }

    @NotNull
    @PostMapping("/edit/{editClient}")
    private String editRecord(@RequestParam String firstName, @RequestParam String secondName,
                              @PathVariable Client editClient, @RequestParam Date dateReg,
                              @NotNull Model model) {
        if (isFieldsValid(firstName, secondName, dateReg)) {
            model.addAttribute("errorMessage", "Ім'я чи прізвище змінюваного клієнта задано некоректно!");
            return "/edit/clientEdit";
        }

        editClient.setFirstName(firstName);
        editClient.setSecondName(secondName);
        editClient.setDateReg(dateReg);
        clientRepo.save(editClient);

        return "redirect:/client";
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
        var clientFilePath = "";
        try {
            clientFilePath = saveUploadingFile(uploadingFile);
            var newClients = excelImporter.importFile(clientFilePath);
            newClients.forEach(clientRepo::save);
            return "redirect:/client";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(clientFilePath);
            model.addAttribute("errorMessage", "Завантажено некоректний файл для таблиці клієнтів!");
            initializeImportAttributes(model);
            return "parts/excelFilesUpload";
        }
    }

    private static void initializeImportAttributes(@NotNull Model model) {
        model.addAttribute("controllerName", Client.class.getSimpleName());
        model.addAttribute("tableName", "клієнтів");
    }

    @NotNull
    @GetMapping("/exportExcel")
    private String exportExcel(@NotNull Model model) {
        model.addAttribute("clients", lastOutputtedClients);
        return "clientExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delClient}")
    private String deleteRecord(@NotNull @PathVariable Client delClient) {
        clientRepo.delete(delClient);
        return "redirect:/client";
    }

    public static boolean isFieldsValid(String firstName, String secondName, Date dateReg) {
        return !StringUtils.isAlpha(firstName) || !StringUtils.isAlpha(secondName) || dateReg == null;
    }
}