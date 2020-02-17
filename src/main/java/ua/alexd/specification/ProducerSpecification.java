package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Label;

public class ProducerSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Label> brandEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("brand"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Label> modelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.get("model"), "%" + expression + "%");
    }
}
