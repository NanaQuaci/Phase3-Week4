package com.projects.testdata;

public class CheckoutTestData {
    // Valid data set
    public static final String VALID_FIRST_NAME = "John";
    public static final String VALID_LAST_NAME = "Doe";
    public static final String VALID_POSTAL_CODE = "12345";

    // Invalid / edge case data sets
    public static final String EMPTY_FIRST_NAME = "";
    public static final String EMPTY_LAST_NAME = "";
    public static final String EMPTY_POSTAL_CODE = "";

    public static final String NUMERIC_FIRST_NAME = "1234";
    public static final String SPECIAL_CHAR_LAST_NAME = "@Doe!";
    public static final String INVALID_POSTAL_CODE = "ABCDE";

    // Expected error messages (Swag Labs standard)
    public static final String ERROR_FIRST_NAME_REQUIRED = "Error: First Name is required";
    public static final String ERROR_LAST_NAME_REQUIRED = "Error: Last Name is required";
    public static final String ERROR_POSTAL_CODE_REQUIRED = "Error: Postal Code is required";
}
