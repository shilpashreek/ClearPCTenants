package com.qa.clearpc.AddFolder;

import java.util.ArrayList;

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

public class AddFolderTest extends BaseClass
{

	BaseClass baseClass;
	LoginPage loginPage;
	GenericUtils genericUtils;
	SearchPage searchPage;
	public AddFolderTest() {
		super();
		
	}
	
	@BeforeTest
	public void SetUp() throws Exception
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
	
	@Test(priority=1, enabled=true, description="Validate add folder pop up")
	public void validateAddFolderPopupDisplaying() throws Exception
	{
		extentTest=extent.startTest("validateAddFolderPopupDisplaying");
		searchPage.closePopUp();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.verifyAddFolderIconIsPresent();
		Thread.sleep(3000);
		boolean addFolderPopup=searchPage.addFolderPopUpDisplayed();
		Assert.assertTrue(addFolderPopup);
		
		
	}
	
	@Test(priority=2, enabled=true, description="Validate the fields in Add Folder popUp")
	public void validateFieldsInAddFolderPopup() throws Exception
	{
		extentTest=extent.startTest("validateFieldsInAddFolderPopup");
		searchPage.closePopUp();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.verifyAddFolderIconIsPresent();
		Thread.sleep(3000);
		searchPage.addFolderPopUpDisplayed();
		ArrayList<Boolean> fieldsStatus=searchPage.verifyAddFolderPopUpFields();
		Assert.assertEquals(searchPage.targetList(), fieldsStatus);
		//searchPage.closePopUp();
	
	}
	
	@Test(priority=3, enabled=true, description="validate Add folder fucntionality")
	public void validateAddFolderFunctionality_SaveAndCloseFunctionality() throws Exception
	{
		extentTest=extent.startTest("validateAddFolderFunctionality_SaveAndCloseFunctionality");
	    searchPage.closePopUp();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.verifyAddFolderIconIsPresent();
		Thread.sleep(3000);
		searchPage.addFolderPopUpDisplayed();
		String alertMessage=searchPage.addNewFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		Assert.assertTrue(alertMessage.contains("Folder created successfully"));
		
	}
	
	@Test(priority=4, enabled=true, description="Search newly created folder", dependsOnMethods= {"validateAddFolderFunctionality_SaveAndCloseFunctionality"})
	public void searchNewlyCreatedFolderWithInOrganisation() {
		
		extentTest=extent.startTest("searchNewlyCreatedFolderWithInOrganisation");
		searchPage.closePopUp();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.searchNewFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		int resCount = searchPage.getSearchResultsCount();
		Assert.assertEquals(Integer.toString(resCount), "1");
	    boolean searchStatus = searchPage.verifySearchResults(ConfigUtils.getConfigData("content_type"), searchPage.testFolderName);
	    Assert.assertTrue(searchStatus);
	    
	}
	
	@Test(priority=5, enabled=true, description="validate Save And AnotherFolder Functionality")
	public void validateSaveAndAnotherFolderFunctionality() throws Exception {
		
		extentTest=extent.startTest("validateSaveAndAnotherFolderFunctionality");
		searchPage.closePopUp();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.verifyAddFolderIconIsPresent();
		Thread.sleep(3000);
		searchPage.addFolderPopUpDisplayed();
		String alertMessage=searchPage.saveAndAddAnother();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		Assert.assertTrue(alertMessage.contains("Folder created successfully"));
		boolean addFolderPopup = searchPage.addFolderPopUpDisplayed();
		Assert.assertTrue(addFolderPopup);
		//searchPage.closePopUp();
		
	}
	
	@Test(priority=6, enabled=true, description="Verify edit folder fucntionality", dependsOnMethods= {"validateAddFolderFunctionality_SaveAndCloseFunctionality"})
	public void validateEditFolderFucntionality() {
		
		extentTest=extent.startTest("validateEditFolderFucntionality");
		searchPage.closePopUp();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.searchNewFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		boolean editFolderPopUp=searchPage.editFolderPopUpDisplayed();
		Assert.assertTrue(editFolderPopUp);
		String updateSuccessAlert=searchPage.editFolderName(searchPage.testFolderName);
		Assert.assertTrue(updateSuccessAlert.contains("Folder updated successfully"));       
		
	}
	
	@AfterTest
	public void tearDown()
	{
		driver.quit();
	}
	
	
	
	
	
	
	
}
