package ua.alexd.specification;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import ua.alexd.domain.Hardware;

public final class HardwareSpecification {
    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> displayModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("display").get("model"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> displayDiagonalEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("display").get("diagonal"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> displayResolutionEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("display").get("resolution"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> displayTypeEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("display").get("type"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> cpuModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("cpu").get("model"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> cpuFrequencyEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.join("cpu").get("frequency"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> ramModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("ram").get("model"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> ramMemoryEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("ram").get("memory"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> ssdModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("ssd").get("model"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> ssdMemoryEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("ssd").get("memory"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> hddModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("hdd").get("model"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> hddMemoryEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("hdd").get("memory"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> gpuModelLike(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.like(root.join("gpu").get("model"),
                "%" + expression + "%");
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> gpuMemoryEqual(Integer expression) {
        if (expression == null)
            return null;
        return (root, query, builder) -> builder.equal(root.join("gpu").get("memory"), expression);
    }

    @Nullable
    @Contract(pure = true)
    public static Specification<Hardware> assemblyNameEqual(String expression) {
        if (expression == null || expression.isEmpty())
            return null;
        return (root, query, builder) -> builder.equal(root.get("assemblyName"), expression);
    }
}