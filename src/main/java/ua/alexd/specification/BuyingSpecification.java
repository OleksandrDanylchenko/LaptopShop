package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Buying;

import java.time.LocalDateTime;

public class BuyingSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Buying> basketIdEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("basket").get("id"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Buying> laptopModelEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("laptop").join("label")
                .get("model"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Buying> totalPriceEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("totalPrice"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Buying> dateTimeEqual(LocalDateTime expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("basket").get("dateTime"), expression);
    }
}