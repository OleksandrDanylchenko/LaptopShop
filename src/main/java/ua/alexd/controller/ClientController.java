package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.domain.Client;
import ua.alexd.repos.ClientRepo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static ua.alexd.specification.ClientSpecification.*;

@Controller
@RequestMapping("/client")
public class ClientController {
    private final ClientRepo clientRepo;

    public ClientController(ClientRepo clientRepo) {
        this.clientRepo = clientRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String secondName,
                              @RequestParam(required = false) String dateRegStr,
                              @NotNull Model model) throws ParseException {
        var dateReg = dateRegStr == null || dateRegStr.isEmpty()
                ? null
                : new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateRegStr).getTime());

        var clientSpecification = Specification.where(firstNameEqual(firstName))
                .and(secondNameEqual(secondName)).and(dateRegEqual(dateReg));
        var clients = clientRepo.findAll(clientSpecification);

        model.addAttribute("clients", clients);
        model.addAttribute("firstName", firstName);
        model.addAttribute("secondName", secondName);
        model.addAttribute("dateRegStr", dateRegStr);
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
                             @RequestParam String dateRegStr, @NotNull Model model) throws ParseException {
        if (isFieldsEmpty(firstName, secondName, dateRegStr, model))
            return "add/clientAdd";

        var dateReg = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateRegStr).getTime());
        var newClient = new Client(firstName, secondName, dateReg);
        clientRepo.save(newClient);

        var clients = clientRepo.findAll();
        model.addAttribute("client", clients);
        return "redirect:/client";
    }

    @NotNull
    @GetMapping("/edit/{editClient}")
    private String editRecord(@PathVariable Client editClient, @NotNull Model model) {
        model.addAttribute("editClient", editClient);
        return "/edit/clientEdit";
    }

    @NotNull
    @PostMapping("/edit/{editClient}")
    private String saveEditedRecord(@PathVariable Client editClient, @RequestParam String firstName,
                                    @RequestParam String secondName, @RequestParam String dateRegStr,
                                    @NotNull Model model) throws ParseException {
        if (isFieldsEmpty(firstName, secondName, dateRegStr, model))
            return "/edit/clientEdit";

        var dateReg = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateRegStr).getTime());
        editClient.setFirstName(firstName);
        editClient.setSecondName(secondName);
        editClient.setDateReg(dateReg);
        clientRepo.save(editClient);
        return "redirect:/client";
    }

    @NotNull
    @GetMapping("/delete/{delClient}")
    private String deleteRecord(@NotNull @PathVariable("delClient") Client delClient) {
        clientRepo.delete(delClient);
        return "redirect:/client";
    }

    private boolean isFieldsEmpty(String firstName, String secondName, String dateReg, Model model) {
        if (firstName == null || secondName == null || dateReg == null ||
                firstName.isEmpty() || secondName.isEmpty() || dateReg.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Поля клієнта не можуть бути пустими!");
            return true;
        }
        return false;
    }
}