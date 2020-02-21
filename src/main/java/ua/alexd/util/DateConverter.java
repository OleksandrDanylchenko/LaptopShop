package ua.alexd.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateConverter {
    public static boolean isNonValidDate(@NotNull Date dateReg) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        var nonValidDate = dateFormat.parse("0001-01-01");
        return dateReg.compareTo(nonValidDate) == 0;
    }

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