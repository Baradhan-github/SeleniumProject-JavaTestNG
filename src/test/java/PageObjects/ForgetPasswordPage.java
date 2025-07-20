package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.SeleniumUtils;

public class ForgetPasswordPage extends BasePage {

	// ========== DECLARATIONS ==========
	SeleniumUtils utils;
	protected static Logger logger = LogManager.getLogger(ForgetPasswordPage.class);

	
	// ========== CONSTRUCTOR ==========
	public ForgetPasswordPage(WebDriver driver) {
		super(driver);
		utils = new SeleniumUtils(driver);

	}

	
	// ========== LOCATORS ==========
	@FindBy(xpath = "//input[@id='emailadd_recovery']")
	WebElement mailRecovery;
	
	@FindBy(xpath = "//input[@id='Submit']")
	WebElement submitMail;
	
	@FindBy(xpath = "//input[@id='Reset']")
	WebElement resetBox;
	
	@FindBy(xpath = "//td[contains(text(),'Forgot')]")
	WebElement forgotPass;
	
	@FindBy(xpath = "//a[contains(text(),'Go back')]")
	WebElement backToLogin;
	
	
	// ========== PAGE ACTION METHODS ==========
	public void recoveryMailInput(String value) {
		utils.sendKeys(mailRecovery, value);
		logger.info("Mail ID used: "+value);
	}
	
	public void clickRecoveryMailSubmit() {
		utils.click(submitMail);
		logger.info("Password recovery Submit button clicked");
	}
	
	public void clickRecoveryMailReset() {
		utils.click(resetBox);
		logger.info("Password recovery reset button clicked");
	}
	
	public void goBackToLoginPage() {
		utils.click(backToLogin);
		logger.info("Back to Login Page button clicked");
	}
}
