package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
	WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/*
	 * Basic WebDriver methods
	 */
	
	public void get(String str) {
		driver.get(str);
	}

	public void maximize() {
		driver.manage().window().maximize();
	}

	public void minimize() {
		driver.manage().window().minimize();
	}

	public void close() {
		driver.close();
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public String getTitle() {
		return driver.getTitle();
	}
}
