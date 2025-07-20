package PageObjects;

import java.util.List;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.SeleniumUtils;

public class SelectHotelPage extends BasePage {
	
	// ========== DECLARATIONS ==========
	SeleniumUtils utils;
	private static Logger logger = LogManager.getLogger(SelectHotelPage.class);
	
	
	// ========== CONSTRUCTOR ==========
	public SelectHotelPage(WebDriver driver){
		super(driver);
		utils = new SeleniumUtils(driver);
	}
	
	
	// ========== LOCATORS ==========
	@FindBy(xpath = "//td[@class='login_title']") WebElement selectPageCheck;
	@FindBy(xpath = "//form[@id='select_form']//table//tr[2]//tr[1]/td") List<WebElement> tableHeading;
	@FindBy(xpath = "//form[@id='select_form']//table//tr[2]//tr[2]//td") List<WebElement> tableDatas;
	@FindBy(xpath = "//td[@id='continue'']") WebElement selectPageContinueButton;
	@FindBy(xpath = "//td[@id='cancel'']") WebElement selectPageCancelButton;
	
	
	// ========== PAGE ACTION METHODS ==========
	
}
