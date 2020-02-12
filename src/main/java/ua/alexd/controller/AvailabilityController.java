package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.alexd.repos.AvailabilityRepo;

@Controller
@RequestMapping("/availability")
public class AvailabilityController {
    @Autowired
    private AvailabilityRepo availabilityRepo;

    @GetMapping
    public String seeAllRecords(@NotNull Model model) {
        var allRecords = availabilityRepo.findAll();
        model.addAttribute("records", allRecords);
        return "availability";
    }
}