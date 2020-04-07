package ua.alexd.dateTimeService;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@Lazy
public final class DateFormatter {
    private static final SimpleDateFormat fullDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat abridgedDateFormat = new SimpleDateFormat("d-M-yy");

    @NotNull
    @Contract(pure = true)
    public static Date parseDate(@NotNull String dateStr) throws ParseException {
        var dashesAmount = StringUtils.countMatches(dateStr, '-');
        if(dashesAmount == 2)
           return new java.sql.Date(fullDateFormat.parse(dateStr).getTime());
        else {
            var normalizedDate = normalizeDate(dateStr);
            return new java.sql.Date(abridgedDateFormat.parse(normalizedDate).getTime());
        }
    }

    @NotNull
    @Contract(pure = true)
    private static String normalizeDate(@NotNull String initDate) {
        var dateElements = initDate.split("/");

        var temp = dateElements[0];
        dateElements[0] = dateElements[1];
        dateElements[1] = temp;

        return String.join("-", dateElements);
    }
}