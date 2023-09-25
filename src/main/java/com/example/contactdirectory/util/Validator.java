package com.example.contactdirectory.util;

import java.util.regex.Pattern;

public class Validator {
    private static final String PHONE_PATTERN = "^\\+?[1-9][0-9]{7,14}$";

    //check is String empty
    public static boolean checkIsEmpty(String... fields) {
        for (String field : fields) {
            if (field == null || field.isBlank() || field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    //simple check for phone number
    public static boolean checkPhone(String phoneNumber) {
        Pattern compile = Pattern.compile(PHONE_PATTERN);
        return compile.matcher(phoneNumber).matches();
    }
}
