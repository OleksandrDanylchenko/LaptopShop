package ua.alexd.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static ua.alexd.security.UserSpecification.isActiveEqual;
import static ua.alexd.security.UserSpecification.usernameEqual;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepo userRepo;
    private static Iterable<User> lastOutputtedUsers;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @NotNull
    @GetMapping
    private String getRecords(@RequestParam(required = false) String username,
                              @RequestParam(required = false) String isActive,
                              @NotNull Model model) {
        var userSpecification = Specification.where(usernameEqual(username)).and(isActiveEqual(isActive));
        var users = userRepo.findAll(userSpecification);
        lastOutputtedUsers = users;
        model.addAttribute("users", users);
        model.addAttribute("roles", Role.values());
        return "view/user/table";
    }

    @NotNull
    @PostMapping("/add")
    private String addRecord(@RequestParam String newUsername, @RequestParam String newPassword,
                             @RequestParam String role, @NotNull Model model) {
        var newUser = new User(newUsername, newPassword, true, Role.valueOf(role));
        if (!saveRecord(newUser)) {
            model.addAttribute("errorMessage",
                    "Представлений новий логін уже присутній у базі!");
            model.addAttribute("users", lastOutputtedUsers);
            model.addAttribute("roles", Role.values());
            return "view/user/table";
        }
        return "redirect:/user";
    }

    @NotNull
    @PostMapping("/edit/{editUser}")
    private String editRecord(@RequestParam String editUsername, @RequestParam String editPassword,
                              @RequestParam Role editRole, @NotNull @RequestParam String editActive,
                              @NotNull @PathVariable User editUser, @NotNull Model model) {
        editUser.setUsername(editUsername);
        editUser.setPassword(editPassword);
        editUser.setRole(editRole);
        editUser.setActive(editActive.equals("Активний"));
        if (!saveRecord(editUser)) {
            model.addAttribute("errorMessage",
                    "Представлений змінюваний логін уже присутній у базі!");
            model.addAttribute("users", lastOutputtedUsers);
            model.addAttribute("roles", Role.values());
            return "view/user/table";
        }
        return "redirect:/user";
    }

    @NotNull
    @GetMapping("/delete/{delUser}")
    private String deleteRecord(@NotNull @PathVariable User delUser) {
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