package org.AutomationExercise.e_com.Base;

import com.aventstack.extentreports.*;
import org.AutomationExercise.e_com.Utils.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

public class BaseClassLocal {

    private static ExtentReports extent;

    // Thread-safe WebDriver, ExtentTest, and Browser Name
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static ThreadLocal<String> browserName = new ThreadLocal<>();

    public WebDriver getDriver() {
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

        browserName.set(browser); // store browser name for report
        System.out.println("=== Starting setup for: " + browser + " ===");

        try {
            WebDriver localDriver;

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--disable-gpu");
                    localDriver = new ChromeDriver(chromeOptions);
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--disable-gpu");
                    localDriver = new FirefoxDriver(firefoxOptions);
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--headless");
                    edgeOptions.addArguments("--disable-gpu");
                    localDriver = new EdgeDriver(edgeOptions);
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
    public void startTest(Method method) {
        String title = "Test: " + method.getName() + " [" + browserName.get() + "]";
        ExtentTest extentTest = extent.createTest(title);
        extentTest.assignCategory(browserName.get()); // category for filtering
        test.set(extentTest);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        ExtentTest extentTest = test.get();

        if (extentTest != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                String screenshotPath = takeScreenshot(result.getName());
                if (screenshotPath != null) {
                    extentTest.fail("Test Failed: " + result.getThrowable(),
                            MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                } else {
                    extentTest.fail("Test Failed: " + result.getThrowable());
                }
            } else if (result.getStatus() == ITestResult.SUCCESS) {
                extentTest.pass("Test Passed");
            } else if (result.getStatus() == ITestResult.SKIP) {
                extentTest.skip("Test Skipped");
            }
        }

        test.remove();
    }

    @AfterClass(alwaysRun = true)
    public void teardownClass() {
        WebDriver localDriver = driver.get();
        if (localDriver != null) {
            localDriver.quit();
            System.out.println("Closed browser: " + browserName.get());
            driver.remove();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void generateReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    public String takeScreenshot(String testName) {
        WebDriver localDriver = driver.get();
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
