package ua.alexd.dateTimeService;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeProvider {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    @NotNull
    public static String getCurrentDateTime() {
        return formatter.format(new Date());
    }
}