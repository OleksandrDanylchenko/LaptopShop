package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Laptop;

public final class LaptopSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Laptop> hardwareAssemblyNameLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("hardware").get("assemblyName"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Laptop> typeNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("type").get("name"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Laptop> labelBrandEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("label").get("brand"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Laptop> labelModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("label").get("model"),
                "%" + expression + "%");
    }
}