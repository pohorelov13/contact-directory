package com.example.contactdirectory.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.example.contactdirectory.util.Validator.checkIsEmpty;
import static com.example.contactdirectory.util.Validator.checkPhone;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    private static String notEmptyField;
    private static String emptyField;
    private static String blankField;
    private static String validPhoneNumber;
    private static String wrongFormatPhoneNumber;
    private static String shortPhoneNumber;

    @BeforeAll
    static void beforeAll() {
        notEmptyField = "Ivan";
        emptyField = "";
        blankField = "     ";
        validPhoneNumber = "+380999283123";
        wrongFormatPhoneNumber = "phone number";
        shortPhoneNumber = "+38095";
    }

    @Test
    void checkIsEmptyTest() {
        assertFalse(checkIsEmpty(notEmptyField));
        assertTrue(checkIsEmpty(emptyField));
        assertTrue(checkIsEmpty(emptyField, notEmptyField));
        assertTrue(checkIsEmpty(blankField));
    }

    @Test
    void checkPhoneTest() {
        assertTrue(checkPhone(validPhoneNumber));
        assertFalse(checkPhone(wrongFormatPhoneNumber));
        assertFalse(checkPhone(shortPhoneNumber));
    }

}