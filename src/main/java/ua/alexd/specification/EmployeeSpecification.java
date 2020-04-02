package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Employee;

public final class EmployeeSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> firstNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("firstName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> secondNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("secondName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> shopAddressLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("shop")
                .get("address"), "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Employee> isActiveEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        // TODO Rework name of isActive
        return (root, query, builder) -> builder.equal(root.get("isActive"), expression.equals("Працюючий"));
    }
}
