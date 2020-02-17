package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Client;

import java.sql.Date;

public final class ClientSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Client> firstNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("firstName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Client> secondNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("secondName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Client> dateRegEqual(Date expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("dateReg"), expression);
    }
}
