package org.AutomationExercise.e_com.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class takeScreenshotUtil extends org.AutomationExercise.e_com.Base.BaseClassLocal  {
	 public static String takeScreenshot(String testName) {
	        WebDriver localDriver = getDriver();
	        try {
	            File src = ((TakesScreenshot) localDriver).getScreenshotAs(OutputType.FILE);
	            String path = System.getProperty("user.dir") + "/target/surefire-reports/screenshots/" + testName + ".png";
	            File destination = new File(path);
	            Files.createDirectories(destination.getParentFile().toPath());
	            Files.copy(src.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            return path;
	        } catch (IOException | WebDriverException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
}
