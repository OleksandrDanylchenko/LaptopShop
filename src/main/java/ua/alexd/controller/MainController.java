package ua.alexd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String landing(Model model) {
        var controllers = new ArrayList<String>();
        Collections.addAll(controllers, "availability", "basket", "buying", "client", "cpu", "display",
                "employee", "gpu", "hardware", "hdd", "laptop", "post", "producer", "ram", "shop", "ssd", "status",
                "type", "weight");
        model.addAttribute("controllers", controllers);
        return "landing";
    }
}