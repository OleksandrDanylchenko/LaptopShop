package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Availability;

import java.sql.Date;

public final class AvailabilitySpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Availability> laptopModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("laptop").join("label")
                .get("model"), "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Availability> shopAddressLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("shop").get("address"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Availability> quantityEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("quantity"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Availability> fullPriceEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("price"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Availability> dateStartEqual(Date expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("dateStart"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Availability> dateEndEqual(Date expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("dateEnd"), expression);
    }
}