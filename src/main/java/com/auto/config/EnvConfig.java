package com.auto.config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.auto.utils.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvConfig {

    private static final Properties properties;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream(Constants.PROP_FILE_PATH);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file");
        }
    }

    public static void ChromeConfig() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.timeout = Constants.TIMEOUT_IN_MILLISECONDS;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        if (Boolean.parseBoolean(System.getProperty("selenide.headless", "false"))) {
            options.addArguments("--headless=new");
        }
        WebDriverRunner.setWebDriver(new ChromeDriver(options));
    }

    public static String getPropValue(String key) {
        Object value = properties.get(key);
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    public static String getEmail() {
        return getPropValue("EMAIL");
    }

    public static String getPassword() {
        return getPropValue("PASSWORD");
    }
}
