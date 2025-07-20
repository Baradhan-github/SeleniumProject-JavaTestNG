package PageObjects;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import utilities.SeleniumUtils;

public class NewUserPage {

	// ========== DECLARATIONS ==========
	private SeleniumUtils selUtils;
	protected Logger logger = LogManager.getLogger(NewUserPage.class);

	// ========== CONSTRUCTOR ==========
	public NewUserPage(WebDriver driver) {
		super();
		selUtils = new SeleniumUtils(driver);
	}

	// ========== LOCATORS ==========
	@FindBy(xpath = "//input[@id='username']")
	WebElement newUserName;
	@FindBy(xpath = "//input[@id='password']")
	WebElement newUserPass;
	@FindBy(xpath = "//input[@id='re_password']")
	WebElement newUserRePass;
	@FindBy(xpath = "//input[@id='full_name']")
	WebElement newUserFullName;
	@FindBy(xpath = "//input[@id='email_add']")
	WebElement newUserEmail;
	@FindBy(xpath = "//img[@id='captcha']")
	WebElement captchaImage;
	@FindBy(xpath = "//input[@id='captcha-form']")
	WebElement newUserCaptchaTextBox;
	@FindBy(xpath = "//input[@id='tnc_box']")
	WebElement newUserTermsCheckBox;
	@FindBy(xpath = "//a[normalize-space()='Terms & Conditions']")
	WebElement newTermsAndConditionsLink;
	@FindBy(xpath = "//input[@id='Submit']")
	WebElement newUserSubmit;
	@FindBy(xpath = "//input[@id='Reset']")
	WebElement newUserReset;
	@FindBy(xpath = "//img[@alt='Refresh Captcha']")
	WebElement newUserRefresh;

	// ========== PAGE ACTION METHODS ==========
	public void newUserNameField(String name) {
		selUtils.sendKeys(newUserName, name);
	}

	public void newUserPassField(String pass) {
		selUtils.sendKeys(newUserPass, pass);
	}

	public void newUserRePassField(String pass) {
		selUtils.sendKeys(newUserRePass, pass);
	}

	public void newUserFullnameField(String fname) {
		selUtils.sendKeys(newUserFullName, fname);
	}

	public void newUserEmailField(String mail) {
		selUtils.sendKeys(newUserEmail, mail);
	}

	public String extractCaptchaText() throws IOException, TesseractException {
		File sourceSs = captchaImage.getScreenshotAs(OutputType.FILE);
		File capLocation = new File("src/test/resources/TempImage/iamge" + System.currentTimeMillis() + ".png");
		FileUtils.copyFile(sourceSs, capLocation);

		// need to install tessract manually which helps solve capcha
		Tesseract tess = new Tesseract();
		tess.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
		tess.setLanguage("eng");

		// using tessract we are trimming the extra spaces while extracting text
		String captchaText = "";
		captchaText = tess.doOCR(capLocation).replaceAll("[^a-zA-Z0-9]", "").trim();
		logger.info("Solved Captcha: "+captchaText);
		return captchaText;
	}
	
	public void newUserCaptchaTextBox() throws IOException, TesseractException {
		selUtils.sendKeys(newUserCaptchaTextBox, extractCaptchaText());
	}
	
	public void newUserTermsAndConditionsCheckBox() {
		selUtils.click(newUserTermsCheckBox);
	}
	
	public void newUserTermAndConditionLink() {
		selUtils.click(newTermsAndConditionsLink);
	}
	
	public void newUserSubmitButton() {
		selUtils.click(newUserSubmit);
	}
	
	public void newUserResetButton() {
		selUtils.click(newUserReset);
	}

	public void newUserRefreshButton() {
		selUtils.click(newUserRefresh);
	}
	
	
	public void newUserAccountCreationFlow(String name, String pass, String fname, String email) throws IOException, TesseractException {
		newUserNameField(name);
		newUserPassField(pass);
		newUserRePassField(pass);
		newUserFullnameField(fname);
		newUserEmailField(email);
		extractCaptchaText();
		newUserCaptchaTextBox();
		newUserTermsAndConditionsCheckBox();
		newUserTermAndConditionLink();
		newUserSubmitButton();
	}
}
