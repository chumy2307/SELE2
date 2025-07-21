package com.auto.report;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.FileSystemResultsWriter;
import lombok.extern.slf4j.Slf4j;
import org.testng.IExecutionListener;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TestListener implements ITestListener, ISuiteListener, IExecutionListener {

    @Override
    public void onExecutionStart() {
        AllureLifecycle lifecycle = Allure.getLifecycle();
        String currentTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        FileSystemResultsWriter writer =
                new FileSystemResultsWriter(Paths.get("allure-results/" + "report-" + currentTime));
        Class<?> clazz = lifecycle.getClass();
        try {
            Field field = clazz.getDeclaredField("writer");
            field.setAccessible(true);
            field.set(lifecycle, writer);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        Allure.setLifecycle(lifecycle);
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getDescription();
        if (testName == null || testName.isEmpty()) {
            testName = result.getMethod().getMethodName();
        }
        log.info("Running test: {}", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("Test Passed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("Test Failed: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info("Test Skipped: {}", result.getMethod().getMethodName());
    }
}
