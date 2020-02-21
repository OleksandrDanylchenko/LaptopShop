package ua.alexd.util;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class DateConverter {
    public static boolean isNonValidDate(@NotNull Date dateReg) throws ParseException {
        var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        var nonValidDate = dateFormat.parse("0001-01-01");
        return dateReg.compareTo(nonValidDate) == 0;
    }
}