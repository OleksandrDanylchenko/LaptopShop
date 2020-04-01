package ua.alexd.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final AdminRepo adminRepo;

    public RegistrationController(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    @NotNull
    @GetMapping
    private String registration() {
        return "/security/registration";
    }

    @NotNull
    @PostMapping
    public String registration(@NotNull @ModelAttribute("newAdmin") Admin newAdmin, @NotNull Model model) {
        var foundedAdmin = adminRepo.findByUsername(newAdmin.getUsername());
        if (foundedAdmin != null) {
            model.addAttribute("errorMessage",
                    "Представлений логін уже присутній в базі даних!");
            return "/security/registration";
        }
        newAdmin.setActive(true);
        newAdmin.setRoles(Collections.singleton(Role.MANAGER));
        adminRepo.save(newAdmin);
        return "redirect:/login";
    }
}