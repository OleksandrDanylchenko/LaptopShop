package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.HDD;

public class HDDSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<HDD> modelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.get("model"), "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<HDD> memoryEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.get("memory"), expression);
    }
}