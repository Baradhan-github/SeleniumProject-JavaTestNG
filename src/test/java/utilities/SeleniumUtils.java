package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager; //Log4j
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	/*
	 * Declarations
	 * All necessary field declarations for the utility class
	 */
	final WebDriver driver;
	private TakesScreenshot tk;
	final Actions actions;
	final JavascriptExecutor js;
	private static Robot robot;
	private static final Logger logger = LogManager.getLogger(SeleniumUtils.class);


	/*
	 * if we declare the declaration as static, it would take class name, since
	 * static is bounded to the class. we have to use className.instance variable if
	 * we don't use static, we can use constructor's this to initiate
	 */

	/*
	 * constructors
	 * Initializes all required driver instances and utilities
	 */
	public SeleniumUtils(WebDriver driver) {
		this.driver = Objects.requireNonNull(driver, "this driver shouldn't be null");
		this.tk = (TakesScreenshot) driver;
		this.actions = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
	}

	
	/*
	 * creating robot class as static since robot is not a part of webdriver and its
	 * ideal to create seperate from webdriver
	 */
	static {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	/*
	 * UI driver methods
	 * Basic browser navigation and element interaction methods
	 */
	public void navigateTo(String str) {
		driver.navigate().to(str);
		logger.info("Navigated to: " + str);
	}

	public void back() {
		driver.navigate().back();
		logger.info("Navigated backward");
	}

	public void forward() {
		driver.navigate().forward();
		logger.info("Navigated forward");
	}

	public void refresh() {
		driver.navigate().refresh();
		logger.info("Screen Refreshed");
	}

	public void sendKeys(WebElement element, String value) {
		if (element != null && value != null) {
			element.clear();
			element.sendKeys(value);
			logger.info("entered value: " + value);
		} else {
			logger.info("Element value is null");
		}

	}

	public void click(WebElement el) {
		logger.info("Element clicked: " + el.getText());
		el.click();
	}
	

	/*
	 * window handling
	 * Methods for managing multiple browser windows/tabs
	 */
	public String switchToNewWindow() {
		String current = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(current)) {
				driver.switchTo().window(handle);
				return handle;
			}
		}
		return current;
	}

	public Set<String> getAllWindows() {
		return driver.getWindowHandles();
	}

	public WebDriver switchToWindowInt(String str) {
		return driver.switchTo().window(str);
	}

	public void switchToDefaultWindow() {
		driver.switchTo().defaultContent();
		logger.info("Switched to default window");
	}
	

	/*
	 * iFrames
	 * Methods for working with iframes in web pages
	 */
	public void switchToFrameInt(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrameNameOrId(String name) {
		driver.switchTo().frame(name);
	}

	public void switchToFrameElement(WebElement element) {
		driver.switchTo().frame(element);
	}

	public void switchToParentWindow() {
		driver.switchTo().parentFrame();
	}
	

	/*
	 * Selenium methods
	 * Core Selenium utility methods for various operations
	 */
	

	// screenshot method
	public void takesScreenshotAs(String srcLocation) throws IOException {
		File src = tk.getScreenshotAs(OutputType.FILE);
		File dest = new File(srcLocation + ".png");
		FileUtils.copyFile(src, dest);
	}
	

	// Select methods

	/*
	 * here we are using helper methods to avoid repeating the code
	 * we return the Select class as return type and no declaration is required
	 */
	private Select select(WebElement element) {
		return new Select(element);
	}

	public boolean isMultiple(WebElement element) {
		return select(element).isMultiple();
	}

	public void selectByIndex(int num, WebElement element) {
		select(element).selectByIndex(num);

		logger.info("Selected the index value");
	}

	public void selectByValue(String str, WebElement element) {
		select(element).selectByValue(str);
	}

	public void selectByVisibleText(String str, WebElement element) {
		select(element).selectByVisibleText(str);
	}

	public void deselectByIndex(int num, WebElement element) {
		select(element).deselectByIndex(num);
	}

	public void deselectByValue(String str, WebElement element) {
		select(element).deselectByValue(str);
	}

	public void deselectVisibleText(String str, WebElement element) {
		select(element).deselectByVisibleText(str);
	}

	public void deselectAll(WebElement element) {
		select(element).deselectAll();

	}

	/*
	 * to get all options we are creating an empty ArrayList and using for each loop
	 * we are adding the elements one by one while adding its crucial to include
	 * getText() for temporary object reference to convert it to string
	 */
	public List<String> getAllOptions(WebElement element) {
		List<WebElement> options = select(element).getOptions();
		List<String> allOptions = new ArrayList<>();
		for (WebElement option : options) {
			allOptions.add(option.getText());
		}
		return allOptions;
	}

	public List<WebElement> getAllSelectedOptions(WebElement element) {
		return select(element).getAllSelectedOptions();
	}

	public WebElement getFirstSelectedOption(WebElement element) {
		return select(element).getFirstSelectedOption();
	}

	// Action methods
	public void doubleClickAction(WebElement element) {
		actions.doubleClick(element).perform();
	}

	public void contextClickAction(WebElement element) {
		actions.contextClick(element).perform();
	}

	public void dragAndDrop(WebElement srcElement, WebElement desElement) {
		actions.dragAndDrop(srcElement, desElement).perform();
	}

	public void clickAndDrop(WebElement srcElement, WebElement desElement) {
		actions.clickAndHold(srcElement).moveToElement(desElement).release().perform();
	}
	

	// javascript methods
	public void javascriptSetAttribute(String s, WebElement em) {
		js.executeScript("arguments[0].setAttribute('value','" + s + "');", em);
	}

	public void javascriptClick(WebElement em) {
		js.executeScript("arguments[0].click();", em);
	}

	public void javascriptHighlighter(WebElement e) {
		js.executeScript("arguments[0].setAttribute('style','background: green');", e);
	}

	public void javascriptScroll(WebElement e, boolean b) {
		js.executeScript("arguments[0].scrollIntoView(b);", e);
	}

	public String javaScriptGetAttribute(WebElement e) {
		return (String) js.executeScript("arguments[0].getAttribute('value');", e);
	}
	

	/*
	 * Alerts
	 * Methods for handling JavaScript alert popups
	 */

	/*
	 * creating an alert reusable code with Alert as return type, which can be
	 * chained, helps in reduce multiple occurrence of same code
	 */
	private Alert alert() {
		return driver.switchTo().alert();
	}

	public String acceptAlert() {
		String text = alert().getText();
		alert().accept();
		return text;
	}

	public String dismissAlert() {
		String text = alert().getText();
		alert().dismiss();
		return text;
	}

	public String sendKeysAlert(String input) {
		String text = alert().getText();
		alert().sendKeys(input);
		return text;
	}

	public String getAlertText() {
		return alert().getText();
	}


	/*
	 * Waits
	 * based on conditions, helps to wait for certain period of time for web element, web driver
	 * used to avoid exceptions like noSuchElemtExceptions, ElementNotVisibleExceptions, AJAX elements, etc.
	 */

	//Implicitly wait
	public void implicitlyWait(WebElement element, int seconds) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
	}


	// Explicit wait
	private WebDriverWait wait(int seconds) {
		return new WebDriverWait(driver,Duration.ofSeconds(seconds));
	}

	public Alert waitUntilAlertIsPresent( int seconds) {
		return wait(seconds).until(ExpectedConditions.alertIsPresent());
	}

	public WebElement waitUntilElementIsVisible(WebElement element, int seconds) {
		return wait(seconds).until(ExpectedConditions.visibilityOf(element));
	}

	public WebElement waitUntilElementToBeSelected(WebElement element, int seconds) {
		wait(seconds).until(ExpectedConditions.elementToBeSelected(element));
		return element;
	}

	public WebElement waitUntilElementToBeClickable(WebElement element, int seconds) {
		return wait(seconds).until(ExpectedConditions.elementToBeClickable(element));
	}

	public List<WebElement> waitUntilVisiblityOfAllElements(WebElement element, int seconds) {
		return wait(seconds).until(ExpectedConditions.visibilityOfAllElements(element));
	}

	// Fluent wait
	public void fluentWaits(int timeOut, long milli) {
		new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeOut)).pollingEvery(Duration.ofMillis(milli)).ignoring(Exception.class);
	}


	/*
	 * Robots keys
	 * Keyboard simulation methods using Robot class
	 */

	/*
	 * To minimize the massive code for robots we use robot helper which is reusable
	 * and efficient here we use (int... arg) in argument which is use to send
	 * multiple arguments without limit here we are using minimized code for for
	 * loop for each argument both loop runs, hence if there are 3 keys, the both
	 * loop runs thrice spontaneously
	 */
	private static void robo(int... keys) {
		for (int k : keys)
			robot.keyPress(k);
		for (int k : keys)
			robot.keyRelease(k);
	}

	/*
	 * loop run twice iteration 1 - keyPress for control and C iteration 2 -
	 * keyRelease for control and C
	 */
	// Press Copy key
	public static void robotCopy() {
		robo(KeyEvent.VK_CONTROL, KeyEvent.VK_C);
	}

	// Press Cut key
	public static void robotCut() {
		robo(KeyEvent.VK_CONTROL, KeyEvent.VK_X);
	}

	// Press Paste key
	public static void robotPaste() {
		robo(KeyEvent.VK_CONTROL, KeyEvent.VK_V);
	}

	// Press Reverse Tab key
	public static void reverseTab() {
		robo(KeyEvent.VK_SHIFT, KeyEvent.VK_TAB);
	}

	/*
	 * loop run once iteration 1 - keyPress for tab iteration 2 - keyRelease for tab
	 */
	// Press Tab key
	public static void pressTab() {
		robo(KeyEvent.VK_TAB);
	}

	// Press Escape
	public static void pressEsc() {
		robo(KeyEvent.VK_ESCAPE);
	}


	/*
	 * Broken link
	 */

	public Boolean checkIsTheBrokenLink(String link) throws IOException {
		Boolean isTheLinkBroken;
		URL url = new URL(link);
		HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		if (!(con.getResponseCode() == 200)) {
			isTheLinkBroken = Boolean.TRUE;
		} else {
			isTheLinkBroken = Boolean.FALSE;
		}
		return isTheLinkBroken;
	}
}