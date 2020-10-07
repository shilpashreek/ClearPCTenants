package com.qa.clearpc.search;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.pages.LoginPage;
import com.qa.clearpc.pages.SearchPage;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.Constants;
import com.qa.clearpc.utility.GenericUtils;
import com.qa.clearpc.utility.SeleniumUtility;

public class FilterPageTest extends BaseClass {

	BaseClass baseClass;
	LoginPage loginPage;
	GenericUtils genericUtils;
	SearchPage searchPage;
	
	public FilterPageTest()
	{
		super();
	}
	
	@BeforeTest
	public void setUp() throws Exception
	{
		baseClass=new BaseClass();
		BaseClass.loadPortal();
		loginPage=new LoginPage();
		if(driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("url"))) {
		loginPage.login(ConfigUtils.getConfigData("username"), ConfigUtils.getConfigData("password"), ConfigUtils.getConfigData("host"), 
				ConfigUtils.getConfigData("Username"), ConfigUtils.getConfigData("Password"), ConfigUtils.getConfigData("Sender"), ConfigUtils.getConfigData("Subject"), Constants.mail_Body_path);
		}else {
			log.info("Portal is logged in already");
		}
		genericUtils=new GenericUtils();
		searchPage=new SearchPage();
	}
	
	@Test(priority=1, enabled=false,description="Validate and print filter options")
	public void validateAllFilterOptionsAreDisplayed()
	{
		extentTest=extent.startTest("validateAllFilterOptionsAreDisplayed");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.performBlankSearch();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		genericUtils.CheckFilterPanelDrawerStatus();
		List<String> Filters = genericUtils.filterOptions();
		boolean uploadDate_filter=genericUtils.uploadDateFilter_displayed();
		List<String> target_Filters = genericUtils.targetFilterOptions();
		//Assert.assertTrue(Filters.contains(target_Filters));
		Assert.assertTrue(uploadDate_filter);
		
	}
	
	@Test(priority=2,enabled=true,description="Apply Categories filter type and validate the result")
	public void validateCategoriesFilterTypeResults()
	{
		boolean SearchResultMatch=false;
		try {
			extentTest=extent.startTest("validateCategoriesFilterTypeResults");
			genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
			searchPage.performBlankSearch();
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
			genericUtils.CheckFilterPanelDrawerStatus();
			genericUtils.FILTERcategoriesStatus();
			SearchResultMatch = genericUtils.unCheckCurrentAndApplyNewFilter();
			Assert.assertTrue(SearchResultMatch);
			//genericUtils.splitString(FilterConut);
		} catch (Exception e) {
			log.error("Failed to apply filters" +e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	
	
	
	
	
}
