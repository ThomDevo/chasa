package com.example.chasa.utilities;

import java.util.Locale;
import java.util.ResourceBundle;

public class JsfUtils {
    public static String returnMessage(Locale locale, String message) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(
                "i18n.messages", locale);
        return resourceBundle.getString(message);
    }
}
