package ua.alexd.dateTimeUtils;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeChecker {
    private static Date nonValidDate;

    static {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            nonValidDate = dateFormat.parse("0001-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNonValidDate(@NotNull Date dateReg) {
        return dateReg.compareTo(nonValidDate) == 0;
    }

    public static boolean isDateStartPrevDateEnd(@NotNull Date dateStart, @NotNull Date dateEnd) {
        return dateStart.compareTo(dateEnd) > 0;
    }
}