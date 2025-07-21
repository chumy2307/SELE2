package com.auto.utils;


import java.time.format.DateTimeFormatter;

public class Constants {

    // URLs
    public static final String BASE_URL = "https://demo.testarchitect.com/";

    // Paths
    public static final String PROP_FILE_PATH = "src/test/java/resources/account.properties";

    // Config
    public static final int TIMEOUT_IN_MILLISECONDS = 10000;

    // Message
    public static final String ORDER_SUCCESS_MSG = "THANK YOU. YOUR ORDER HAS BEEN RECEIVED.";

    // Others
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS");
    public static final String EMAIL_TEMPLATE = "email_test_%s+%s@email.com";
    public static final String DEFAULT_BANK_NAME = "Citi Bank";
    public static final String DEFAULT_BANK_ACCOUNT_NUMBER = "1234567890";
    public static final String DATE_FORMAT = "MMMM d, yyyy";
}
