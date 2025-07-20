package PageObjects;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CommonComponents extends BasePage {

	protected final Logger logger = LogManager.getLogger(CommonComponents.class);

	// ========== CONSTRUCTOR ==========
	public CommonComponents(WebDriver driver) {
		super(driver);
	}

	// ========== LOCATORS ==========

	@FindBy(xpath = "//td[contains(text(),'Welcome')]")
	WebElement welcomeText;

	@FindBy(xpath = "//input[@id='username_show']")
	WebElement showUserName;

	@FindBy(xpath = "//a[starts-with(text(),'Search')]")
	WebElement searchHotelLink;

	@FindBy(xpath = "//a[starts-with(text(),'Booked')]")
	WebElement bookedIterary;

	@FindBy(xpath = "//a[starts-with(text(),'Change')]")
	WebElement changePass;

	@FindBy(xpath = "//a[starts-with(text(),'Logout')]")
	WebElement logout;

	@FindBy(xpath = "//input[@id='username_show']/parent::td")
	WebElement commonTableData;

	// ========== PAGE ACTION METHODS ==========

	public String welcomeText() {
		String text = welcomeText.getText();
		logger.info("Top menu - text name: " + text);
		return text;
	}

	public void searchHotelLink() {
		searchHotelLink.click();
		logger.info("Top menu - search hotel link clicked");
	}

	public void bookedIteraryLink() {
		bookedIterary.click();
		logger.info("Top menu - booked iterary link clicked");
	}

	public void changePasswordLink() {
		changePass.click();
		logger.info("Top menu - change password link clicked ");
	}

	public void LogoutLink() {
		logout.click();
		logger.info("Top menu - logout link link clicked ");
	}

	public Map<String, String> allLinks() {
		List<WebElement> links = commonTableData.findElements(By.tagName("a"));

		Map<String, String> map = new LinkedHashMap<>();
		for (WebElement link : links) {
			map.put(link.getText().trim(), link.getAttribute("href"));
		}
		logger.info("Elements: {}", map);
		return map;
	}
}
