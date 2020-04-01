package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Type;

public class TypeSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Type> typeNameLike(String expression) {
        if (expression == null || expression.isBlank())
            return null;
        return (root, query, builder) -> builder.like(root.get("name"), "%" + expression + "%");
    }
}