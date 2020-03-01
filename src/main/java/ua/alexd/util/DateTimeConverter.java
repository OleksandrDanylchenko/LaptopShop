package ua.alexd.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeConverter {
    @NotNull
    public static String getDateTimeStr(@NotNull LocalDateTime dateTime) {
        var dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");
        return dateTime.format(dateTimeFormat);
    }

    @Nullable
    public static LocalDateTime getDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.isEmpty())
            return null;
        var dateTimeFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm");
        return LocalDateTime.parse(dateTimeStr, dateTimeFormat);
    }
}