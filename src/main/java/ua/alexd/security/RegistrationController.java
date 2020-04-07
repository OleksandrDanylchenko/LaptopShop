package ua.alexd.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.repos.UserRepo;
import ua.alexd.security.service.ActivationMailSender;

import java.util.UUID;

import static ua.alexd.security.Role.USER;

@Controller
@RequestMapping("/registration")
public final class RegistrationController {
    private final UserRepo userRepo;
    private final ActivationMailSender mailSender;

    public RegistrationController(UserRepo userRepo, ActivationMailSender mailSender) {
        this.userRepo = userRepo;
        this.mailSender = mailSender;
    }

    @NotNull
    @GetMapping
    private String registration() {
        return "/security/registration";
    }

    @NotNull
    @PostMapping
    private String registration(@NotNull @ModelAttribute("newUser") User newUser, @NotNull Model model) {
        if (!saveRecord(newUser)) {
            model.addAttribute("errorMessage",
                    "Представлений новий логін чи e-mail уже присутній в базі даних!");
            return "/security/registration";
        }
        newUser.setActive(false);
        newUser.setRole(USER);
        newUser.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(newUser);

        mailSender.sendActivation(newUser);
        return "redirect:/login";
    }

    @NotNull
    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        var activationUser = userRepo.findByActivationCode(code);
        if (activationUser == null) {
            model.addAttribute("errorMessage", "Код активації не було знайдено!");
            return "/security/registration";
        }
        activationUser.setActivationCode(null);
        activationUser.setActive(true);
        model.addAttribute("successfulActivationMessage",
                "Користувача " + activationUser.getUsername() + " активовано успішно!");
        userRepo.save(activationUser);
        return "/security/login";
    }

    private boolean saveRecord(User saveUser) {
        try {
            userRepo.save(saveUser);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}