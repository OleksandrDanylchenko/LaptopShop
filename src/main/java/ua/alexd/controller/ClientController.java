package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.domain.Client;
import ua.alexd.excelInteraction.imports.ClientExcelImporter;
import ua.alexd.repos.ClientRepo;

import java.io.IOException;
import java.sql.Date;

import static ua.alexd.dateTimeService.DateTimeChecker.isNonValidDate;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.deleteNonValidFile;
import static ua.alexd.excelInteraction.imports.UploadedFilesManager.saveUploadingFile;
import static ua.alexd.specification.ClientSpecification.*;

@Controller
@RequestMapping("/client")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
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
    public String getRecords(@RequestParam(required = false) String firstName,
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
        return "view/client/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@NotNull @ModelAttribute("newClient") Client newClient, @NotNull Model model) {
        clientRepo.save(newClient);
        return "redirect:/client";
    }

    @NotNull
    @PostMapping("/edit/{editClient}")
    public String editRecord(@RequestParam String editFirstName, @RequestParam String editSecondName,
                              @RequestParam Date editDateReg, @NotNull @PathVariable Client editClient,
                              @NotNull Model model) {
        editClient.setFirstName(editFirstName);
        editClient.setSecondName(editSecondName);
        editClient.setDateReg(editDateReg);
        clientRepo.save(editClient);
        return "redirect:/client";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model)
            throws IOException {
        var clientFilePath = "";
        try {
            clientFilePath = saveUploadingFile(uploadingFile);
            var newClients = excelImporter.importFile(clientFilePath);
            newClients.forEach(clientRepo::save);
            return "redirect:/client";
        } catch (IllegalArgumentException ignored) {
            deleteNonValidFile(clientFilePath);
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці клієнтів!");
            model.addAttribute("clients", lastOutputtedClients);
            return "view/client/table";
        }
    }

    @NotNull
    @GetMapping("/exportExcel")
    public String exportExcel(@NotNull Model model) {
        model.addAttribute("clients", lastOutputtedClients);
        return "clientExcelView";
    }

    @NotNull
    @GetMapping("/delete/{delClient}")
    public String deleteRecord(@NotNull @PathVariable Client delClient) {
        clientRepo.delete(delClient);
        return "redirect:/client";
    }
}