package ua.alexd.inputService;

import java.util.regex.Pattern;

public class InputValidator {
    public static boolean stringContainsAlphabet(String str) {
        if (str != null) {
            var pattern = Pattern.compile("^\\d*.*[a-zA-Z]+.*");
            var matcher = pattern.matcher(str);
            return matcher.find();
        }
        return false;
    }
}