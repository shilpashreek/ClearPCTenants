package com.qa.clearpc.shareLink;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.pages.LoginPage;
import com.qa.clearpc.pages.SearchPage;
import com.qa.clearpc.pages.ShareLinkPage;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.Constants;
import com.qa.clearpc.utility.GenericUtils;
import com.qa.clearpc.utility.MailUtils;
import com.qa.clearpc.utility.SeleniumUtility;
import com.qa.clearpc.utility.Xls_Reader;

public class ShareLinkTest extends BaseClass {

	BaseClass baseClass;
	LoginPage loginPage;
	GenericUtils genericUtils;
	SearchPage searchPage;
	ShareLinkPage shareLink;
	Xls_Reader reader;
	String shareAssetSheet = "shareAssetLoc";
	MailUtils mailUtils;

	public ShareLinkTest() {
		super();
	}

	@BeforeTest
	public void SetUp() throws Exception {
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
		shareLink = new ShareLinkPage();
		mailUtils = new MailUtils();
	}

	@Test(priority = 1, enabled = false, description = "Validate share pop-up")
	public void validateSharePopUp() throws Exception {
		extentTest = extent.startTest("validateSharePopUp");
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
		shareLink.selectContentToShare(ConfigUtils.getConfigData("Folder_contentType"));
		Thread.sleep(2000);
		ArrayList<Boolean> popUpFields = shareLink
				.validateFieldsInSharePopUp(ConfigUtils.getConfigData("folderSharePermissions"));
		Assert.assertEquals(popUpFields, searchPage.targetList());

	}

	@DataProvider
	public Object[][] getShareAssetLocators() {
		reader = new Xls_Reader();
		Object[][] shareFunctionalityLoctors = reader.GetData(shareAssetSheet);
		return shareFunctionalityLoctors;
	}

	@Test(priority = 2, enabled = false, description = "Validate folder share link functionality", dataProvider = "getShareAssetLocators")
	public void validateSuccessMessageaUponCopyingShareLink(String id) throws Exception {
		extentTest = extent.startTest("validateSuccessMessageaUponCopyingShareLink");
		shareLink.closeSharePopUpWindow();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
		shareLink.selectContentToShare(ConfigUtils.getConfigData("Folder_contentType"));
		String linkCopied[] = shareLink.shareTheContentAndCaptureSuccessAlertMessage(id).split("\n");
		Assert.assertTrue(linkCopied[0].equals("Alert") && linkCopied[1].equals("Success: Link copied"));

	}

	@Test(priority = 3, enabled = false, description = "Validate share fnctionality is working as excepted")
	public void validateShareLinkFunctionality() throws Exception {
		extentTest = extent.startTest("validateShareFunctionality");
		shareLink.closeSharePopUpWindow();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
		String contentSelectedToShare = shareLink.selectContentToShare(ConfigUtils.getConfigData("Folder_contentType"));
		String link = shareLink.getShareLink();
		shareLink.shareTheContentAndCaptureSuccessAlertMessage("COPY & EXIT1");
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		String childWindow = genericUtils.openNewTab();
		genericUtils.closeOtherTabsExpectChildWindow(childWindow);
		driver.get(link);
		if (driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("url"))) {
			loginPage.login(ConfigUtils.getConfigData("username"), ConfigUtils.getConfigData("password"),
					ConfigUtils.getConfigData("host"), ConfigUtils.getConfigData("Username"),
					ConfigUtils.getConfigData("Password"), ConfigUtils.getConfigData("Sender"),
					ConfigUtils.getConfigData("Subject"), Constants.mail_Body_path);
		} else {
			log.info("Portal is logged in already");
		}

		Thread.sleep(15000);
		String shareLinkLoadedPage = genericUtils.returnBredaCrumbCurrentFolder().trim();
		Assert.assertEquals(shareLinkLoadedPage, contentSelectedToShare);
	}

	@Test(priority = 4, enabled = false, description = "Verify asset share link functionality")
	public void verifyAssetShareLinkFunctionality() throws Exception {

		extentTest = extent.startTest("verifyAssetShareLinkFunctionality");
		shareLink.closeSharePopUpWindow();
		genericUtils.checkNavigationPanelStatus();
		genericUtils.expandLibraryTab();
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
		String titleOfSharedAsset = shareLink.selectContentToShare(ConfigUtils.getConfigData("CopyAsset_content_type"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
		String link = shareLink.clickOnShareLinkTab();
		ArrayList<Boolean> popUpFields = shareLink
				.validateFieldsInSharePopUp(ConfigUtils.getConfigData("AssetSharePermissions"));
		Assert.assertEquals(popUpFields, searchPage.targetList());
		String linkCopied[] = shareLink.shareTheContentAndCaptureSuccessAlertMessage("COPY & EXIT1").split("\n");
		Assert.assertTrue(linkCopied[0].equals("Alert") && linkCopied[1].equals("Success: Link copied"));
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		String childWindow = genericUtils.openNewTab();
		genericUtils.closeOtherTabsExpectChildWindow(childWindow);
		driver.get(link);
		if (driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("url"))) {
			loginPage.login(ConfigUtils.getConfigData("username"), ConfigUtils.getConfigData("password"),
					ConfigUtils.getConfigData("host"), ConfigUtils.getConfigData("Username"),
					ConfigUtils.getConfigData("Password"), ConfigUtils.getConfigData("Sender"),
					ConfigUtils.getConfigData("Subject"), Constants.mail_Body_path);
		} else {
			log.info("Portal is logged in already");
		}

		Thread.sleep(15000);
		String assetTitleInViewerMode = shareLink.validateAssetTitleInViewer();
		Assert.assertEquals(assetTitleInViewerMode.trim(), titleOfSharedAsset.trim());

	}

	@Test(priority = 5, enabled = false, description = "Validate asset share popup")
	public void validateAssetSharePopUp() throws Exception {
		extentTest = extent.startTest("validateAssetSharePopUp");
		shareLink.closeSharePopUpWindow();
		genericUtils.checkNavigationPanelStatus();
		genericUtils.expandLibraryTab();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.textLoadingSymbol());
		String titleOfSharedAsset = shareLink.selectContentToShare(ConfigUtils.getConfigData("CopyAsset_content_type"));
		// SeleniumUtility.waitTill_invisibility_of_Element(driver, 20,
		// genericUtils.loadingSymbol());
		List<Boolean> assetShare_popUp = shareLink.verifyShareAssetPopup();
		Assert.assertEquals(assetShare_popUp, shareLink.sharePopUpTargetList());

		// shareLink.selectEndDate("oct", "2021", "22");

	}

	@DataProvider
	public Object[][] getShareAssetData() {
		reader = new Xls_Reader();
		Object[][] assetShareDetails = reader.GetData("getShareAssetData");
		return assetShareDetails;
	}

//AssetShareDetails
	@Test(priority = 6, enabled = true, description = "Share the asset and capture success alert message", dataProvider = "getShareAssetData")
	public void ValidateAssetShareFunctionality(String mailId, String EndDate) throws Exception {
		extentTest = extent.startTest("ValidateAssetShareFunctionality");
		shareLink.closeSharePopUpWindow();
		genericUtils.checkNavigationPanelStatus();
		genericUtils.expandLibraryTab();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.textLoadingSymbol());
		String titleOfSharedAsset = shareLink.selectContentToShare(ConfigUtils.getConfigData("CopyAsset_content_type"));
		shareLink.verifyShareAssetPopup();
		// String[] endShareDate =
		// genericUtils.parseStringToDateFormat(EndDate).split("-");
		String[] endShareDate = EndDate.split("-");
		String dateVal = endShareDate[0];
		String month = Month.of(Integer.parseInt(endShareDate[1])).toString().substring(0, 3);
		String year = endShareDate[2];

		/*
		 * String[] assetSharedStatus = shareLink .enterDataInTheAssetSharePopup(mailId,
		 * month, year, dateVal, titleOfSharedAsset).split("\n");
		 */

		String date = dateVal.concat(" ")
				.concat(month.substring(0, 1).toUpperCase() + month.substring(1, month.length()).toLowerCase())
				.concat(" ").concat(year).concat(" ");
		String[] assetSharedStatus = shareLink.enterDataInTheAssetSharePopupUsingJs(mailId, date, titleOfSharedAsset)
				.split("\n");
		Assert.assertTrue(
				assetSharedStatus[0].equals("Alert") && "Asset shared successfully".equals(assetSharedStatus[1]));
	}

	@Test(priority = 7, enabled = true, description = "Validate Asset Share Notification for internal user", dataProvider = "getShareAssetData")
	public void validateAssetShareNotificationForInternalUser(String mailId, String EndDate) throws Exception {

		extentTest = extent.startTest("validateAssetShareNotificationForInternalUser");
		shareLink.closeSharePopUpWindow();
		genericUtils.checkNavigationPanelStatus();
		genericUtils.expandLibraryTab();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.textLoadingSymbol());
		String titleOfSharedAsset = shareLink.selectContentToShare(ConfigUtils.getConfigData("CopyAsset_content_type"));
		shareLink.verifyShareAssetPopup();
		String[] endShareDate = EndDate.split("-");
		String dateVal = endShareDate[0];
		String month = Month.of(Integer.parseInt(endShareDate[1])).toString().substring(0, 3);
		String year = endShareDate[2];
		String date = dateVal.concat(" ")
				.concat(month.substring(0, 1).toUpperCase() + month.substring(1, month.length()).toLowerCase())
				.concat(" ").concat(year).concat(" ");

		// shareLink.enterDataInTheAssetSharePopup(mailId, month, year, dateVal,
		// titleOfSharedAsset).split("\n");

		shareLink.enterDataInTheAssetSharePopupUsingJs(mailId, date, titleOfSharedAsset);

		String subject = shareLink.assetTitleWithExtension.concat(" has been shared with you in CLEAR!").trim();
		Thread.sleep(25000);
		String mailBody = mailUtils.readTheLatestMail(ConfigUtils.getConfigData("host"),
				ConfigUtils.getConfigData("Username"), ConfigUtils.getConfigData("Password"),
				ConfigUtils.getConfigData("Sender"), subject, Constants.mail_Body_path);
		log.info(mailBody);
		Assert.assertTrue(mailBody != null);

	}

	@DataProvider
	public Object[][] getExternalUserAssetShareData() {
		reader = new Xls_Reader();
		Object[][] assetShareDetails = reader.GetData("externalUserAssetShare");
		return assetShareDetails;
	}

	@Test(priority = 8, enabled = true, description = "Validate asset share notification is received by external user", dataProvider = "getExternalUserAssetShareData")
	public void validateAssetShareNotificationForExternalUser(String host, String mailId, String Password,
			String sender, String EndShare) throws Exception, IOException {
		extentTest = extent.startTest("validateAssetShareNotificationForExternalUser");
		shareLink.closeSharePopUpWindow();
		genericUtils.checkNavigationPanelStatus();
		genericUtils.expandLibraryTab();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		genericUtils.selectOrganization(ConfigUtils.getConfigData("tenant"));
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		searchPage.selectTestFolder();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.textLoadingSymbol());
		String titleOfSharedAsset = shareLink.selectContentToShare(ConfigUtils.getConfigData("CopyAsset_content_type"));
		shareLink.verifyShareAssetPopup();
		String[] endShareDate = EndShare.split("-");
		String dateVal = endShareDate[0];
		String month = Month.of(Integer.parseInt(endShareDate[1])).toString().substring(0, 3);
		String year = endShareDate[2];

		/*
		 * shareLink.enterDataInTheAssetSharePopup("shilpashreekgowda@gmail.com", month,
		 * year, dateVal, titleOfSharedAsset) .split("\n");
		 */
		String date = dateVal.concat(" ")
				.concat(month.substring(0, 1).toUpperCase() + month.substring(1, month.length()).toLowerCase())
				.concat(" ").concat(year).concat(" ");
		shareLink.enterDataInTheAssetSharePopupUsingJs(mailId, date, titleOfSharedAsset);

		String subject = shareLink.assetTitleWithExtension.concat(" has been shared with you in CLEAR!").trim();
		Thread.sleep(25000);
		String mailBody = mailUtils.readTheLatestMail(host, mailId, Password, sender, subject,
				Constants.mail_Body_path);
		log.info(mailBody);
		Assert.assertTrue(mailBody != null);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
