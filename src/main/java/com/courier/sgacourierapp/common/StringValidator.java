package com.courier.sgacourierapp.common;

import java.util.regex.Pattern;

public class StringValidator {

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$";
    private static final String IDNUMBER_REGEX = "^[A-Za-z0-9]{6,10}$";

    public static String identifyStringType(String input) {
        if (Pattern.matches(EMAIL_REGEX, input)) {
            return "Email";
        } else if (Pattern.matches(PHONE_REGEX, input)) {
            return "Phone Number";
        } else if (Pattern.matches(IDNUMBER_REGEX, input)) {
            return "ID Number";
        } else {
            return "Unknown";
        }
    }
}
