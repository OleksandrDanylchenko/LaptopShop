package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.repos.UserRepo;
import ua.alexd.security.Role;
import ua.alexd.security.User;

import static ua.alexd.specification.UserSpecification.*;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('CEO')")
public class UserController {
    private final UserRepo userRepo;
    private static Iterable<User> lastOutputtedUsers;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String isActive,
                             @RequestParam(required = false) String email,
                             @NotNull Model model) {
        var userSpecification = Specification.where(usernameEqual(username)).and(isActiveEqual(isActive))
                .and(emailLike(email));
        var users = userRepo.findAll(userSpecification);
        lastOutputtedUsers = users;
        model.addAttribute("users", users);
        model.addAttribute("roles", Role.values());
        return "view/user/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@NotNull @ModelAttribute("newUser") User newUser, @NotNull Model model) {
        newUser.setActive(true);
        if (!saveRecord(newUser)) {
            model.addAttribute("errorMessage",
                    "Представлений новий логін чи e-mail уже присутній в базі даних!");
            model.addAttribute("users", lastOutputtedUsers);
            model.addAttribute("roles", Role.values());
            return "view/user/table";
        }
        return "redirect:/user";
    }

    @NotNull
    @PostMapping("/edit/{editUser}")
    public String editRecord(@RequestParam String editUsername, @RequestParam String editPassword,
                             @RequestParam Role editRole, @NotNull @RequestParam String editActive,
                             @NotNull @RequestParam String editEmail, @NotNull @PathVariable User editUser,
                             @NotNull Model model) {
        editUser.setUsername(editUsername);
        editUser.setPassword(editPassword);
        editUser.setRole(editRole);
        editUser.setEmail(editEmail);
        editUser.setActive(editActive.equals("Активний"));
        if (!saveRecord(editUser)) {
            model.addAttribute("errorMessage",
                    "Представлений змінюваний логін чи e-mail уже присутній в базі даних!");
            model.addAttribute("users", lastOutputtedUsers);
            model.addAttribute("roles", Role.values());
            return "view/user/table";
        }
        return "redirect:/user";
    }

    @NotNull
    @GetMapping("/delete/{delUser}")
    public String deleteRecord(@NotNull @PathVariable User delUser) {
        userRepo.delete(delUser);
        return "redirect:/user";
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean saveRecord(User saveUser) {
        try {
            userRepo.save(saveUser);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }
}