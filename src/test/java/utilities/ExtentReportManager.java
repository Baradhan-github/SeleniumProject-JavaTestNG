package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * This class manages Extent Report configuration and integrates with TestNG via ITestListener.
 * It handles reporting of test start, success, failure, and skip events.
 */
public class ExtentReportManager implements ITestListener {

    // Using ThreadLocal for thread-safe parallel test logging
    private static final ThreadLocal<ExtentTest> TEST_HOLDER = new ThreadLocal<>();
    private static ExtentReports extent;
    private static String docName; // Holds the generated report file name

    /*
     * onStart runs at the start and collects and imprints all the necessary data
     * This initializes the ExtentReports object and configures the spark reporter
     */
    private static synchronized ExtentReports getExtent() {
        if (extent == null) {
            // Using SimpleDateFormat to create unique file name
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            docName = "Test-Report-" + timeStamp + ".html";
            ExtentSparkReporter spark = new ExtentSparkReporter("./reports/" + docName);

            // using config() method from sparkReporter to set basic information
            spark.config().setDocumentTitle("Adactin Hotel – Automation Report"); // Title of report
            spark.config().setReportName("Adactin Functional Testing"); // Name of report
            spark.config().setTheme(Theme.DARK); // Dark theme for visual clarity

            // attaching system information to the report
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Application", "Adactin_Hotel");
            extent.setSystemInfo("User", System.getProperty("user.name"));
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    // Get current thread's ExtentTest object
    public static ExtentTest getTest() {
        return TEST_HOLDER.get();
    }

    /*
     * Triggered when a test starts.
     * Creates a test entry in the report.
     */
    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = getExtent().createTest(result.getMethod().getMethodName());
        TEST_HOLDER.set(test);
    }

    /*
     * Successive test cases
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        getTest().pass("Test passed"); // Adds green mark to report
        getTest().assignCategory(result.getMethod().getGroups()); // Display groups in report
        getTest().log(Status.PASS, result.getName() + " got successfully executed");
    }

    /*
     * For failed test cases
     */
    @Override
    public void onTestFailure(ITestResult result) {
        getTest().fail(result.getThrowable()); // Logs the exception

        // Capture screenshot using driver from BaseClass
        WebDriver driver = ((baseClass.BaseClass) result.getInstance()).driver;
        String path = captureScreenshot(driver, result.getMethod().getMethodName());
        getTest().addScreenCaptureFromPath(path); // Attach screenshot to report

        getTest().assignCategory(result.getMethod().getGroups());
        getTest().log(Status.FAIL, result.getName() + " got failed");
        getTest().log(Status.INFO, result.getThrowable().getMessage());

        /*
         * Old logic for screenshot (replaced with new captureScreenshot method)
         * try {
         *     String imgPath = new BaseClass().captureScreen(result.getName());
         *     test.addScreenCaptureFromPath(imgPath);
         * } catch (IOException e1) {
         *     e1.printStackTrace();
         * }
         */
    }

    /*
     * For skipped test cases
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        getTest().skip(result.getThrowable()); // logs skip reason
        getTest().assignCategory(result.getMethod().getGroups());
        getTest().log(Status.SKIP, result.getName() + " got skipped");
        getTest().log(Status.INFO, result.getThrowable().getMessage());
    }

    /*
     * onFinish helps automatically open the extent report
     */
    @Override
    public void onFinish(ITestContext context) {
        getExtent().flush(); // finalize and write the report

        // Automatically open the report on desktop
        String reportPath = System.getProperty("user.dir") + "/reports/" + docName;
        File reportFile = new File(reportPath);

        try {
            Desktop.getDesktop().browse(reportFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Capture screenshot method for failed test cases
     */
    private String captureScreenshot(WebDriver driver, String methodName) {
        // Screenshot will be stored in 'screenshots' folder with method name
        String dest = System.getProperty("user.dir") + "/screenshots/" + methodName + ".png";
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/screenshots"));
            Files.copy(src.toPath(), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dest; // return full path for ExtentReport to pick
    }
}
