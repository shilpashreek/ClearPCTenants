package com.qa.clearpc.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.pages.HomePage;
import com.qa.clearpc.pages.LoginPage;

public class HomePageTest extends BaseClass 
{
	BaseClass baseClass;
	LoginPage loginPage;
	HomePage homePage;
	
  public HomePageTest()
  {
	  super();
  }
  
  @BeforeMethod
  public void setUp()
  {
	  baseClass=new BaseClass();
	  BaseClass.loadPortal();
	  homePage=new HomePage();
  }
  
  @Test(priority=3)
  public void validateAllFoldersAreLoadingInLibrary()
  {
	  homePage.selectOrganization("z Admin v2 UAT");
  }
  
  
  @AfterMethod
  public void tearDown()
  {
	  	driver.quit();
  }
  
  
  
  
  
  
}
