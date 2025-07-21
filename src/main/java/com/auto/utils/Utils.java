package com.auto.utils;

import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

import static com.codeborne.selenide.Selenide.executeJavaScript;

public class Utils {

    public static String emailGenerator() {
        return String.format(
                Constants.EMAIL_TEMPLATE,
                Constants.DATE_TIME_FORMATTER.format(LocalDateTime.now()),
                new Random().nextInt(10000)
        );
    }

    public static String getCurrentUtcTime() {
        return Instant.now().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
    }

    public static String formatProductName(String name) {
        String[] targets = {"I", "By", "With"};
        for (String word : targets) {
            name = name.replaceAll("\\b" + word, word.toLowerCase());
        }
        return name.trim();
    }

    public static double parseCurrencyToDouble(String text) {
        return Double.parseDouble(text.replaceAll("[$,]", "").trim());
    }

    public static void scrollToTop() {
        executeJavaScript("window.scrollTo(0, 0);");
    }

    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(
                dateString.substring(0, 1).toUpperCase() + dateString.substring(1).toLowerCase(),
                DateTimeFormatter.ofPattern(Constants.DATE_FORMAT, Locale.ENGLISH));
    }

    public static String getRandomSentence() {
        return new Faker().lorem().sentence();
    }
}
