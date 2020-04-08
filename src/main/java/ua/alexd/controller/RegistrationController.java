package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.controllerService.UserService;
import ua.alexd.security.User;

import static ua.alexd.security.Role.USER;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @NotNull
    @GetMapping
    private String registration() {
        return "/security/registration";
    }

    @NotNull
    @PostMapping
    private String registration(@NotNull @ModelAttribute("newUser") User newUser, @NotNull Model model) {
        newUser.setRole(USER);
        var isNewUserSaved = userService.addUserRecord(newUser, model);
        if (!isNewUserSaved) {
            model.addAttribute("errorMessage",
                    "Представлений новий логін чи e-mail уже присутній в базі даних!");
            return "/security/registration";
        }
        model.addAttribute("activationMessage",
                "Очікуйте на лист з посиланням на активацію вашого облікового запису.");
        return "redirect:/login";
    }

    @NotNull
    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        var activationUser = userService.activateUser(code);
        if (activationUser == null) {
            model.addAttribute("errorMessage", "Код активації не було знайдено!");
            return "/security/registration";
        }
        model.addAttribute("activationMessage",
                "Користувача " + activationUser.getUsername() + " активовано успішно!");
        return "/security/login";
    }
}