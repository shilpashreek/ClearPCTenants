package com.qa.clearpc.Login;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.pages.HomePage;
import com.qa.clearpc.pages.LoginPage;
import com.qa.clearpc.utility.ConfigUtils;

public class LogoutTest extends BaseClass {
	
	BaseClass baseClass;
	LoginPage loginPage;
	HomePage homePage;
	
	
	
	@BeforeMethod
	public void setup()
	{
		baseClass=new BaseClass();
		BaseClass.loadPortal();
		
		  loginPage=new LoginPage();
		  loginPage.loginToPortal(ConfigUtils.getConfigData("username"),
		  ConfigUtils.getConfigData("password")); 
		  homePage=new HomePage();
		 
	}
	
	@Test(priority=1,enabled=true,description="Verify that logout functionality is working fine")
	public void validateLogoutFunctionality()
	{
		extentTest=extent.startTest("validateLogoutFunctionality");
		String url=homePage.logoutFromPortal();
		Assert.assertEquals(url, ConfigUtils.getConfigData("url"));
	}
	
	@Test(priority=2, enabled=true)
	public void check()
	{
		log.info("executing or not");
	}
	@AfterMethod
	public void tearDown()
	{
		driver.quit();
	}
	
	
	
	
	

}
