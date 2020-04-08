package ua.alexd.controllerService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.alexd.repos.UserRepo;
import ua.alexd.security.Role;
import ua.alexd.security.User;
import ua.alexd.security.service.ActivationMailSender;

import java.util.UUID;

import static ua.alexd.specification.UserSpecification.*;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ActivationMailSender mailSender;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, ActivationMailSender mailSender) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    public Iterable<User> loadUserTable(String username, String isActive, String email, Model model) {
        var userSpecification = createUserSpecification(username, isActive, email);
        var users = userRepo.findAll(userSpecification);
        initializeUserChoices(model);
        return users;
    }

    @SuppressWarnings("ConstantConditions")
    private Specification<User> createUserSpecification(String username, String isActive, String email) {
        return Specification.where(usernameEqual(username)).and(isActiveEqual(isActive)).and(emailLike(email));
    }

    public boolean addUserRecord(@NotNull User newUser, Model model) {
        initializeUserChoices(model);
        newUser.setActive(false);
        var encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.setActivationCode(UUID.randomUUID().toString());
        if (!saveRecord(newUser))
            return false;

        mailSender.sendActivation(newUser);
        return true;
    }

    public boolean editUserRecord(String username, String password, Role role, @NotNull String active,
                                  @NotNull String email, @NotNull User editUser, Model model) {
        editUser.setUsername(username);
        var encodedPassword = passwordEncoder.encode(password);
        editUser.setPassword(encodedPassword);
        editUser.setRole(role);
        editUser.setEmail(email);
        editUser.setActive(active.equals("Активний"));

        initializeUserChoices(model);
        return saveRecord(editUser);
    }

    @Nullable
    public User activateUser(String code) {
        var activationUser = userRepo.findByActivationCode(code);
        if (activationUser == null)
            return null;
        activationUser.setActivationCode(null);
        activationUser.setActive(true);
        userRepo.save(activationUser);
        return activationUser;
    }

    private boolean saveRecord(User saveUser) {
        try {
            userRepo.save(saveUser);
        } catch (DataIntegrityViolationException ignored) {
            return false;
        }
        return true;
    }

    public void deleteRecord(User delUser) {
        userRepo.delete(delUser);
    }

    private void initializeUserChoices(@NotNull Model model) {
        model.addAttribute("roles", Role.values());
    }
}