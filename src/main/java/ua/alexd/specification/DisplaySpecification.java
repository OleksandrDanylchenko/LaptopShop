package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Display;

public class DisplaySpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Display> typeEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("type"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Display> diagonalEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("diagonal"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Display> resolutionEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("resolution"), expression);
    }
}
