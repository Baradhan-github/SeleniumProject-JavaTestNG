package baseClass;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;

import PageObjects.BasePage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager; //Log4j
import org.apache.logging.log4j.Logger;

public class BaseClass {

	public WebDriver driver;
	public Properties p;
	protected static final Logger logger = LogManager.getLogger(BaseClass.class);
	public ExtentReports extent;

	@BeforeClass
	public void setUp() throws IOException {

		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		/*
		 * Selenium 4.6+
		 * System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
		 */

		logger.info("***Initializing driver***");
		driver = new ChromeDriver();
		BasePage bp = new BasePage(driver);
		bp.get(p.getProperty("url2"));
		bp.maximize();
		logger.info("**Starting test cases**");
	}

	@AfterClass
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}

	}

}
