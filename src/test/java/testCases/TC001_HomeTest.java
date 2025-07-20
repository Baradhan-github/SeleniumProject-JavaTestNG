package testCases;



import org.testng.annotations.Test;

import PageObjects.CommonComponents;
import PageObjects.LoginPage;
import baseClass.BaseClass;

public class TC001_HomeTest extends BaseClass {

	@Test(groups= {"Master"})
	public void verifyLoginPage() {
		try {
			logger.info("=======Login test case starts=======");
			LoginPage hp = new LoginPage(driver);
			hp.performLogin(p.getProperty("user"), p.getProperty("pass"));
			CommonComponents components = new CommonComponents(driver);
			Thread.sleep(3000);
			components.LogoutLink();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("======Start=====\n"+e+"======end======");
		}
	}
	
	


}
