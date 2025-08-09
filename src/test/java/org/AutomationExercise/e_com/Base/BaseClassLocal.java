package org.AutomationExercise.e_com.Base;

import com.aventstack.extentreports.*;

import org.AutomationExercise.e_com.Utils.ExtentManager;
import org.AutomationExercise.e_com.Utils.takeScreenshotUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

public class BaseClassLocal {

    private static ExtentReports extent;

    // Thread-safe WebDriver and ExtentTest
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public ExtentTest getTest() {
        return test.get();
    }

    @BeforeSuite(alwaysRun = true)
    public synchronized void setupReport() {
        if (extent == null) {
            extent = ExtentManager.getInstance();
        }
    }

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser", "browserVersion", "platform"})
    public void setupClass(@Optional("chrome") String browser,
                           @Optional("latest") String browserVersion,
                           @Optional("local") String platform) {

        System.out.println("=== Starting setup for: " + browser + " ===");

        try {
            WebDriver localDriver;

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    localDriver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    localDriver = new FirefoxDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    localDriver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Invalid browser: " + browser);
            }

            localDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            localDriver.manage().window().maximize();

            driver.set(localDriver);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Browser setup failed for: " + browser, e);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTest(Method method) {
        ExtentTest extentTest = extent.createTest("Test: " + method.getName());
        test.set(extentTest);
        extentTest.log(Status.INFO, "Starting Test: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void logResult(ITestResult result) throws IOException {
        ExtentTest extentTest = test.get();
        WebDriver localDriver = driver.get();

        if (extentTest != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = takeScreenshotUtil.takeScreenshot(result.getName());
                String relativePath = "screenshots/" + result.getName() + ".png";

                if (screenshotPath != null) {
                    extentTest.fail("Test Failed: " + result.getThrowable(),
					        MediaEntityBuilder.createScreenCaptureFromPath(relativePath).build());
                } else {
                    extentTest.fail("Test Failed: " + result.getThrowable());
                }

            } else if (result.getStatus() == ITestResult.SUCCESS) {
                extentTest.pass("Test Passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                extentTest.skip("Test Skipped");
            }

            extentTest.log(Status.INFO, "Test execution complete.");
        }

        test.remove();
    }

    @AfterClass(alwaysRun = true)
    public void teardownClass() {
        WebDriver localDriver = driver.get();
        if (localDriver != null) {
            localDriver.quit();
            System.out.println("Closed browser: " + localDriver);
            driver.remove();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void generateReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
