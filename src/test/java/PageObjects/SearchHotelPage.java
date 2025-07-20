package PageObjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utilities.SeleniumUtils;
import utilities.TimeUtil;

public class SearchHotelPage extends BasePage {

    // ========== DECLARATIONS ==========
    private SeleniumUtils selUtil;
    public TimeUtil timeUtil;
    private static final Logger logger = LogManager.getLogger(SearchHotelPage.class);

    
    // ========== CONSTRUCTOR ==========
    public SearchHotelPage(WebDriver driver) {
        super(driver);
        selUtil = new SeleniumUtils(driver);
    }
    
    
    // ========== LOCATORS ==========
    @FindBy(xpath = "//select[@id='location']")
    WebElement location;
    
    @FindBy(xpath = "//select[@id='hotels']")
    WebElement hotel;
    
    @FindBy(xpath = "//select[@id='room_type']")
    WebElement room;
    
    @FindBy(xpath = "//select[@id='room_nos']")
    WebElement roomTp;
    
    @FindBy(xpath = "//input[@id='datepick_in']")
    WebElement datePick;
    
    @FindBy(xpath = "//input[@id='datepick_out']")
    WebElement dateOut;
    
    @FindBy(xpath = "//select[@id='adult_room']")
    WebElement adultR;
    
    @FindBy(xpath = "//select[@id='child_room']")
    WebElement childR;
    
    @FindBy(xpath = "//input[@id='Submit']")
    WebElement submit;

    
    // ========== PAGE ACTION METHODS ==========
    public void locationName(int n) {
        selUtil.selectByIndex(n+1, location);
        logger.info("Selected location: "+ location);
        
    }

    public void hotelName(String s) {
        selUtil.selectByValue(s, hotel);
        logger.info("Selected Hotel: "+ hotel);
    }

    public void roomType(String s) {
        selUtil.selectByVisibleText(s, room);
        logger.info("Selected room type: "+ room);
    }

    public void roomNumber(int n) {
        selUtil.selectByIndex(n+1, roomTp);
        logger.info("Selected Room number: "+ roomTp);
    }

    public void checkInDate() {
        TimeUtil timeUtil = new TimeUtil();
        selUtil.javascriptSetAttribute(timeUtil.getCurrentDate(), datePick);
        logger.info("Selected checkin date: "+datePick);
    }

    public void checkOutDate(String time) {
        selUtil.sendKeys(dateOut, time);
        logger.info("Selected checkOut date: "+ dateOut);
    }

    public void AdultsPerRoom(int n) {
        selUtil.selectByIndex(n+1, adultR);
        logger.info("Number of adults in room: "+adultR);
    }

    public void childrenPerRoom(int n) {
        selUtil.selectByIndex(n, childR);
        logger.info("Number of children in room: "+childR);
    }
    
    public void submitButton() {
        submit.click();
        logger.info("New user sumbit button clicked");
    }
}