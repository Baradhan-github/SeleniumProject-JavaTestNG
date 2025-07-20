package testCases;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import PageObjects.LoginPage;
import PageObjects.SearchHotelPage;
import baseClass.BaseClass;

public class TC002_NewUserTest extends BaseClass {
	private static final Logger logger = LogManager.getLogger(TC002_NewUserTest.class);
	@Test(groups="Master")
	public void enterDetails() throws InterruptedException {
		
		 
		LoginPage lp = new LoginPage(driver);
		
		lp.performLogin(p.getProperty("user"), p.getProperty("pass"));
		
	}
	
	@Test(dependsOnMethods = {"enterDetails"}, groups="Master")
	public void newUserVerification() {
		logger.info("start test");
		SearchHotelPage nup = new SearchHotelPage(driver);
		nup.locationName(2);
		nup.hotelName("Hotel Hervey");
		nup.roomType("Standard");
		nup.roomNumber(3);
		nup.checkInDate();
		nup.checkOutDate("26/06/2025");
		nup.AdultsPerRoom(1);
		nup.childrenPerRoom(0);
		nup.submitButton();
	}

}
