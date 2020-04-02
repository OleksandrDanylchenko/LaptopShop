package ua.alexd.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserRepo userRepo;

    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @NotNull
    @GetMapping
    private String registration() {
        return "/security/registration";
    }

    @NotNull
    @PostMapping
    private String registration(@NotNull @ModelAttribute("newUser") User newUser, @NotNull Model model) {
        var foundedUser = userRepo.findByUsername(newUser.getUsername());
        if (foundedUser != null) {
            model.addAttribute("errorMessage",
                    "Представлений логін уже присутній в базі даних!");
            return "/security/registration";
        }
        newUser.setActive(true);
        newUser.setRole(Role.USER);
        userRepo.save(newUser);
        return "redirect:/login";
    }
}