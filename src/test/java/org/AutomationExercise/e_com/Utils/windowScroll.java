package org.AutomationExercise.e_com.Utils;

import org.openqa.selenium.JavascriptExecutor;

public class windowScroll extends org.AutomationExercise.e_com.Base.BaseClassLocal {

    public void ScrollWindow(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
    }
}
