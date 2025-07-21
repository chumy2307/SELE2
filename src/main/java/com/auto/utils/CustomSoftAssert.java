package com.auto.utils;

import io.qameta.allure.Step;
import org.testng.asserts.IAssert;
import org.testng.asserts.SoftAssert;

public class CustomSoftAssert extends SoftAssert {

    @Override
    public void onAssertFailure(IAssert<?> assertCommand, AssertionError ex) {
        super.onAssertFailure(assertCommand, ex);
        logAssertStep(assertCommand.getActual(), assertCommand.getExpected(), assertCommand.getMessage());
    }

    @Override
    public void onAssertSuccess(IAssert<?> assertCommand) {
        super.onAssertSuccess(assertCommand);
        logAssertStep(assertCommand.getActual(), assertCommand.getExpected(), assertCommand.getMessage());
    }

    @Step("Verify point: {description}")
    private void logAssertStep(Object actual, Object expected, String description) {
    }
}
