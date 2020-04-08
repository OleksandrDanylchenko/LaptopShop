package ua.alexd.dateTimeService;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Lazy
public class DateTimeChecker {
    private static Date nonValidDate;

    static {
        try {
            var dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            nonValidDate = dateFormat.parse("0001-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isNonValidDate(@NotNull Date dateReg) {
        return dateReg.compareTo(nonValidDate) == 0;
    }

    public boolean isDateStartPrevDateEnd(@NotNull Date dateStart, @NotNull Date dateEnd) {
        return dateStart.compareTo(dateEnd) > 0;
    }
}