package ua.alexd.inputService;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Lazy
public final class InputValidator {
    public static boolean stringContainsAlphabet(String str) {
        if (str != null) {
            var pattern = Pattern.compile("^\\d*.*[a-zA-Zа-яА-Я]+.*");
            var matcher = pattern.matcher(str);
            return matcher.find();
        }
        return false;
    }
}