package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.SeleniumUtils;

public class LoginPage extends BasePage {

	// ========== DECLARATIONS ==========
	private final SeleniumUtils utils;
	private static final Logger logger = LogManager.getLogger(LoginPage.class);
	
	
	// ========== CONSTRUCTOR ==========
	public LoginPage(WebDriver driver) {
		super(driver);
		utils = new SeleniumUtils(driver);
	}
	
	
	// ========== LOCATORS ==========
	@FindBy(xpath = "//input[@id='username']")
	private WebElement usernameField;

	@FindBy(xpath = "//input[@id='password']")
	private WebElement passwordField;

	@FindBy(xpath = "//td//input[@id='login']")
	public WebElement loginButton;

	

	// ========== PAGE ACTION METHODS ==========

	// Individual action methods
	public void enterUsername(String username) {
		utils.sendKeys(usernameField, username);
	}

	public void enterPassword(String password) {
		utils.sendKeys(passwordField, password);
	}

	public void clickLoginButton() {

		utils.click(loginButton);
	}

	// Combined action method
	public void performLogin(String username, String password) throws InterruptedException {
		enterUsername(username);
		enterPassword(password);
		clickLoginButton();
	}
}