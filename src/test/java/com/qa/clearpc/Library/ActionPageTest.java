package com.qa.clearpc.Library;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.pages.HomePage;
import com.qa.clearpc.pages.LoginPage;
import com.qa.clearpc.pages.SearchPage;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.Constants;
import com.qa.clearpc.utility.GenericUtils;
import com.qa.clearpc.utility.SeleniumUtility;

public class ActionPageTest extends BaseClass {

	BaseClass baseClass;
	LoginPage loginPage;
	HomePage homePage;
	GenericUtils genericUtils;
	SearchPage searchPage;

	@BeforeTest
	public void setup() throws Exception {
		baseClass = new BaseClass();
		BaseClass.loadPortal();
		loginPage = new LoginPage();
		if (driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("url"))) {
			loginPage.login(ConfigUtils.getConfigData("username"), ConfigUtils.getConfigData("password"),
					ConfigUtils.getConfigData("host"), ConfigUtils.getConfigData("Username"),
					ConfigUtils.getConfigData("Password"), ConfigUtils.getConfigData("Sender"),
					ConfigUtils.getConfigData("Subject"), Constants.mail_Body_path);
		} else {
			log.info("Portal is logged in already");
		}
		genericUtils = new GenericUtils();
		searchPage = new SearchPage();

	}

	@Test(priority = 1, enabled = false, description = "Verify copy asset popUp is displayed")
	public void validateCopyAssetPopUpDisplayed() throws Exception {
		extentTest = extent.startTest("validateCopyAsset");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.clickOnSearchBtn();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.ClickOncopyAsset(); // returns title
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		boolean popUpDisplayed = searchPage.copyAssetPopUpDisplayed();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		Assert.assertTrue(popUpDisplayed);

	}

	@Test(priority = 2, enabled = false, description = "Validate copy functionality")
	public void validateCopyFunctionality() throws Exception {
		extentTest = extent.startTest("validateCopyFunctionality");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.clickOnSearchBtn();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		searchPage.ClickOncopyAsset();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.copyAssetPopUpDisplayed();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.copyAssetTo();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
		boolean popUpdisplayed = searchPage.captureFolderTitle_ifCopyPopupIsDisplayed();
		Assert.assertTrue(popUpdisplayed);
		String alertMsg = searchPage.getCopySuccessAlertMessage();
		Assert.assertTrue(alertMsg.contains("Asset copied successfully"));
	}

	@Test(priority = 3, enabled = false, description = "Verify that copied asset is present in the destination folder")
	public void validateCopiedAssetInDestFolder() {

		extentTest = extent.startTest("validateCopiedAssetInDestFolder");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.clickOnSearchBtn();
		boolean searchStatus = searchPage.verifyCopiedAsset();
		Assert.assertTrue(searchStatus);
	}

	@Test(priority = 4, enabled = false, description = "Verify move asset functionality and capture success alert message")
	public void validateMoveFunctionality() throws Exception {
		extentTest = extent.startTest("validateMoveFunctionality");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
		boolean assetSelectedToMove = searchPage.clickOnMoveAsset();
		Assert.assertTrue(assetSelectedToMove);
		String[] moveStatus = searchPage.pasteAssetToTestFolder().split("\n");
		Assert.assertTrue("Alert".equals(moveStatus[0]) && "Assets moved successfully".equals(moveStatus[1]));

	}

	@Test(priority = 5, enabled = true, description = "Verify rename asset functionality and capture the success alert message")
	public void validateRenameFunctionality() {
		extentTest = extent.startTest("validateRenameFunctionality");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
		String[] renameStatus = searchPage.verifyRenameAction().split("\n");
		Assert.assertTrue("Alert".equals(renameStatus[0]) && "Asset renamed successfully".equals(renameStatus[1]));

	}

	@Test(priority = 6, enabled = true, description = "Verify that renamed asset is searchable")
	public void validateRenamedAssetSearchResult() {
		extentTest = extent.startTest("validateRenameFunctionality");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.clickOnSearchBtn();
		boolean searchRes = searchPage.verifyRenamedAssetSearchResult();
		Assert.assertTrue(searchRes);

	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
