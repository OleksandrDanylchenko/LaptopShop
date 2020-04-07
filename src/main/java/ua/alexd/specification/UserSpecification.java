package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.security.User;

public class UserSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<User> usernameEqual(String expression) {
        if (expression == null || expression.isBlank())
            return null;
        return (root, query, builder) -> builder.equal(root.get("username"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<User> isActiveEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("isActive"), expression.equals("Активний"));
    }
}