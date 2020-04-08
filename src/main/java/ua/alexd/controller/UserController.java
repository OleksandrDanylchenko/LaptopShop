package ua.alexd.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.alexd.controllerService.UserService;
import ua.alexd.security.Role;
import ua.alexd.security.User;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('CEO')")
public class UserController {
    private final UserService userService;
    private Iterable<User> lastOutputtedUsers;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @NotNull
    @GetMapping
    public String getRecords(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String isActive,
                             @RequestParam(required = false) String email,
                             @NotNull Model model) {
        var users = userService.loadUserTable(username, isActive, email, model);
        lastOutputtedUsers = users;
        model.addAttribute("users", users);
        return "view/user/table";
    }

    @NotNull
    @PostMapping("/add")
    public String addRecord(@NotNull @ModelAttribute("newUser") User newUser, @NotNull Model model) {
        var isNewUserSaved = userService.addUserRecord(newUser, model);
        if (!isNewUserSaved) {
            model.addAttribute("errorMessage",
                    "Представлений новий логін чи e-mail уже присутній в базі даних!");
            model.addAttribute("users", lastOutputtedUsers);
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
        var isEditUserSaved = userService.editUserRecord(editUsername, editPassword, editRole,
                editActive, editEmail, editUser, model);
        if (!isEditUserSaved) {
            model.addAttribute("errorMessage",
                    "Представлений змінюваний логін чи e-mail уже присутній в базі даних!");
            model.addAttribute("users", lastOutputtedUsers);
            return "view/user/table";
        }
        return "redirect:/user";
    }

    @NotNull
    @GetMapping("/delete/{delUser}")
    public String deleteRecord(@NotNull @PathVariable User delUser) {
        userService.deleteRecord(delUser);
        return "redirect:/user";
    }
}