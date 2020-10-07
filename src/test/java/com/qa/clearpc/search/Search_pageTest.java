package com.qa.clearpc.search;

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

public class Search_pageTest extends BaseClass {
	
	BaseClass baseClass;
	LoginPage loginPage;
	GenericUtils genericUtils;
	SearchPage searchPage;
	
	@BeforeTest
	public void setUp() throws Exception
	{
		baseClass=new BaseClass();
		BaseClass.loadPortal();
		loginPage=new LoginPage();
		//loginPage.loginToPortal(ConfigUtils.getConfigData("username"), ConfigUtils.getConfigData("password"));
		loginPage.login(ConfigUtils.getConfigData("username"), ConfigUtils.getConfigData("password"), 
	    ConfigUtils.getConfigData("host"), ConfigUtils.getConfigData("Username"), ConfigUtils.getConfigData("Password"), ConfigUtils.getConfigData("Sender"), ConfigUtils.getConfigData("Subject"), Constants.mail_Body_path);
		genericUtils=new GenericUtils();
		searchPage=new SearchPage();
	}
	
	@Test(priority=0,enabled=true)
	public void ValidateSearchTipsMessageIsDispalyed() throws Exception
	{
		extentTest=extent.startTest("ValidateSearchTipsMessageIsDispalyed");
		
		//genericUtils.checkNavigationPanelStatus();
		String OrgName=genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		log.info("Tenant name is " +OrgName);
		//Thread.sleep(3000);
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		String orgName_breadCrumb=genericUtils.getBreadCrumbName();
		log.info("Breadcrumb text-->" +orgName_breadCrumb);
		Assert.assertTrue(orgName_breadCrumb.contains(OrgName), "User is not landed to the right tenant folder");
		searchPage.clickOnSearchBtn();
		String search_tips=searchPage.getSearchTipsText();
		Assert.assertTrue(search_tips.contains(ConfigUtils.getConfigData("searchTipsMessage")), "Search tips message is not displayed");
		
	}
	
	@Test(priority=1,enabled=true)
	public void performSearchWithinOrganisation()
	{
		extentTest=extent.startTest("performSearchWithinOrganisationLevel");
		String OrgName=genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		String orgName_breadCrumb=genericUtils.getBreadCrumbName();
		log.info("Breadcrumb text-->" +orgName_breadCrumb);
		Assert.assertTrue(orgName_breadCrumb.contains(OrgName), "User is not landed to the right tenant folder");
		searchPage.clickOnSearchBtn();
		searchPage.enterSearchText_CaptureFilterSuggestions(ConfigUtils.getConfigData("searchInput"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
		searchPage.getSearchResultsCount();
		boolean searchResultVerification=searchPage.verifySearchResults(ConfigUtils.getConfigData("content_type"), ConfigUtils.getConfigData("searchInput"));
		Assert.assertTrue(searchResultVerification);
	}
	
	@Test(priority=2, enabled=true)
	public void validateSearchForInvalidData()
	{
		extentTest=extent.startTest("validateSearchWithInFolder");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.clickOnSearchBtn();
		String searchResult=searchPage.enterInvalidData(ConfigUtils.getConfigData("invalidData"));
		Assert.assertEquals(searchResult, ConfigUtils.getConfigData("invalidSearchResult"));
		
	}
	
	@Test(priority=3,enabled=true)
	public void validateAutosuggetionsForAdvancedSearch()
	{
		extentTest=extent.startTest("validateAutosuggetionsForAdvancedSearch");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.clickOnSearchBtn();
		int AutoSuggestionsCount=searchPage.captureAutosuggestion(ConfigUtils.getConfigData("searchInput"), ConfigUtils.getConfigData("Autosuggestions_path"));
		Assert.assertTrue(AutoSuggestionsCount>0, "Autosuggestions is not displayed");
		
	}
	
	@Test(priority=4, enabled=false, description="Verify grid asset view")
	public void validateGridAssetView()
	{
		extentTest=extent.startTest("validateGridAssetView");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.performBlankSearch();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		boolean gridViewStatus=searchPage.applyGridView();
		Assert.assertTrue(gridViewStatus);
	
	}
	
	@Test(priority=5, enabled=false, description="Verify list asset view")
	public void validateListAssetView()
	{
		extentTest=extent.startTest("validateListAssetView");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.performBlankSearch();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		boolean listViewStatus=searchPage.applylistAssetView();
		Assert.assertTrue(listViewStatus);
	}
	
	@Test(priority=6, enabled=false, description="Verify Detailed asset view")
	public void validateDetailedAssetView()
	{
		extentTest=extent.startTest("validateDetailedAssetView");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.performBlankSearch();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		boolean detailViewStatus=searchPage.applyDetailedAssetView();
		Assert.assertTrue(detailViewStatus);
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	
	
	
	

}
