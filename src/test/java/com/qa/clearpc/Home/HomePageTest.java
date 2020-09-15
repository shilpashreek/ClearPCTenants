package com.qa.clearpc.Home;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.pages.HomePage;
import com.qa.clearpc.pages.LoginPage;
import com.qa.clearpc.utility.ConfigUtils;

public class HomePageTest extends BaseClass {
	
	BaseClass baseClass;
	LoginPage loginPage;
	HomePage homePage;
	
	
	@BeforeTest
	public void setup()
	{
		baseClass=new BaseClass();
		BaseClass.loadPortal();
		loginPage=new LoginPage();
		/*
		 * loginPage.loginToPortal(ConfigUtils.getConfigData("username"),
		 * ConfigUtils.getConfigData("password")); homePage=new HomePage();
		 */
	}
	
	@Test(priority=1,enabled=true)
	public void getAllLeftTabsName()
	{
		boolean panelElements = homePage.checkNavigationPanelStatus();
		Assert.assertTrue(panelElements, "LeftPanel elements are not displaying in homepage");
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	
	
	

}
