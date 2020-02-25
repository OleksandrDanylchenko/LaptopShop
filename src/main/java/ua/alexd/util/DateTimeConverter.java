package ua.alexd.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.ui.Model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeConverter {
    public static boolean isNonValidDate(@NotNull Date dateReg) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        var nonValidDate = dateFormat.parse("0001-01-01");
        return dateReg.compareTo(nonValidDate) == 0;
    }

    public static boolean isDateStartPrevDateEnd(@NotNull Date dateStart, @NotNull Date dateEnd, @NotNull Model model) {
        if (dateStart.compareTo(dateEnd) > 0) {
            model.addAttribute("errorMessage",
                    "Дата закінчення продаж не може передувати даті початку продаж");
            return true;
        }
        return false;
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