package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.alexd.controllerService.ClientService;
import ua.alexd.domain.Client;

import java.sql.Date;

@Controller
@RequestMapping("/client")
@PreAuthorize("hasAnyAuthority('MANAGER', 'CEO')")
public class ClientController {
    private final ClientService clientService;
    private Iterable<Client> lastOutputtedClients;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String secondName,
                             @RequestParam(required = false, defaultValue = "0001-01-01") Date dateReg,
                             @NotNull Model model) {
        var clients = clientService.loadClientTable(firstName, secondName, dateReg);
        lastOutputtedClients = clients;
        model.addAttribute("clients", clients);
        return "view/client/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@NotNull @ModelAttribute("newClient") Client newClient) {
        clientService.addClientRecord(newClient);
        return "redirect:/client";
    }

    @NotNull
    @PostMapping("/edit/{editClient}")
    public String editRecord(@RequestParam String editFirstName, @RequestParam String editSecondName,
                             @RequestParam Date editDateReg, @NotNull @PathVariable Client editClient) {
        clientService.editClientRecord(editFirstName, editSecondName, editDateReg, editClient);
        return "redirect:/client";
    }

    @NotNull
    @PostMapping("/importExcel")
    public String importExcel(@NotNull @RequestParam MultipartFile uploadingFile, @NotNull Model model) {
        var isRecordsImported = clientService.importExcelRecords(uploadingFile);
        if (!isRecordsImported) {
            model.addAttribute("errorMessage",
                    "Завантажено некоректний файл для таблиці клієнтів!");
            model.addAttribute("clients", lastOutputtedClients);
            return "view/client/table";
        }
        return "redirect:/client";
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
        clientService.deleteRecord(delClient);
        return "redirect:/client";
    }
}