package ua.alexd.dateTimeUtils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class DateNormalizer {
    @NotNull
    @Contract(pure = true)
    public static String normalizeDate(@NotNull String initDate, @NotNull String delimiter) {
        var dateElements = initDate.split(delimiter);

        var temp = dateElements[0];
        dateElements[0] = dateElements[1];
        dateElements[1] = temp;

        return String.join("-", dateElements);
    }
}