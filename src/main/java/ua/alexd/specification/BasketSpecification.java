package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Basket;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BasketSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> employeeIdEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("employee").get("id"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> employeeFirstNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("employee").get("firstName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> employeeSecondNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("employee").get("secondName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> employeeShopAddressEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("employee").join("shop")
                .get("address"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> clientIdEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("client").get("id"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> clientFirstNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("client").get("firstName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> clientSecondNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("client").get("secondName"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Basket> dateTimeEqual(LocalDateTime expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("dateTime"), expression);
    }
}