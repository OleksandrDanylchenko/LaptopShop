package ua.alexd.util;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeProvider {
    @NotNull
    public static String getCurrentDateTime() {
        final var formatter = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
        return formatter.format(new Date());
    }
}
