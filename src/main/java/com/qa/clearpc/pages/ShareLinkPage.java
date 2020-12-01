package com.qa.clearpc.pages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.google.common.primitives.Booleans;
import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.GenericUtils;
import com.qa.clearpc.utility.JavascriptUtils;
import com.qa.clearpc.utility.SeleniumUtility;

public class ShareLinkPage extends BaseClass {
	GenericUtils genericUtils;
	String Months = "";
	public String assetTitleWithExtension = "";

	@FindBy(id = "AssetContextMenu")
	WebElement assetContextMenuIcon;

	@FindBy(id = "shareAssetLink")
	WebElement shareLinkBtn;
	@FindBy(className = "popupBG")
	WebElement Share_popup;
	@FindBy(id = "lblShareLinkAssetName")
	WebElement NameOfContentToBeShared;

	@FindBy(id = "txtShareLinkUrl")
	WebElement shareLink;

	@FindBy(id = "COPY & EXIT1")
	WebElement copy_Exit;

	@FindBy(id = "COPY LINK1")
	WebElement copyLink;
	@FindBy(className = "alertMessageUCWrapper")
	WebElement alertMessage;
	@FindBy(css = ".FL.infTxt")
	WebElement linkPermissionDetails;
	@FindBy(id = "close1")
	WebElement closeSharePopup;
	@FindBy(xpath = "//div[contains(@class,'rightTab')]")
	WebElement shareLinkTab;
	@FindBy(xpath = "//div[contains(@class,'leftTab')]")
	WebElement shareAssetTab;
	@FindBy(id = "assetViewerHeaderTitle")
	WebElement assetTitleInViewerHeader;

	@FindBy(id = "lblShareAssetName")
	WebElement NameOfAssetToBeShared;
	@FindBy(id = "txtEmailId")
	WebElement enterEmailId;
	@FindBy(id = "btnAddEmailToDiv")
	WebElement addRecipientsBtn;
	@FindBy(id = "divEmailIdContainer")
	WebElement mailIdTextArea;
	@FindBy(xpath = "//label[.='Begin Share ']")
	WebElement beginShare;
	@FindBy(xpath = "//div[@class='shareStartDate shareAssetDate FL']/input")
	WebElement beginShareDate;
	@FindBy(xpath = "//div[@class='shareStartDate shareAssetDate FL']/span")
	WebElement beginShareCalIcon;
	@FindBy(xpath = "//label[.='End Share']")
	WebElement EndShare;
	@FindBy(xpath = "//div[@class='shareEndDate shareAssetDate FR']/input")
	WebElement endShareDate;
	@FindBy(xpath = "//div[@class='shareEndDate shareAssetDate FR']/span")
	WebElement endShareCalIcon;
	@FindBy(id = "SHARE1")
	WebElement assetShare_ShareBtn;
	@FindBy(id = "EXIT1")
	WebElement assetShare_exitBtn;
	@FindBy(css = ".DynarchCalendar-title")
	WebElement calHeader;
	@FindBy(id = "shareMain")
	WebElement shareWarningBody;
	@FindBy(css = ".warningPopupAssetTitle")
	WebElement assetTitle_ShareAssetPopup;

	public ShareLinkPage() {
		PageFactory.initElements(driver, this);
		genericUtils = new GenericUtils();
	}

	public String selectContentToShare(String Content) {
		String titleOftheSelectedContent = "";
		try {
			List<WebElement> libEntries = driver
					.findElements(By.xpath(ConfigUtils.getConfigData("SearchResultFoldertitles")));
			for (int i = 0; i < libEntries.size(); i++) {
				if (SeleniumUtility.getAttribute(libEntries.get(i), "assetclass").equals(Content)) {
					int j = i + 1;
					titleOftheSelectedContent = SeleniumUtility
							.getElementText(driver.findElement(By
									.xpath(ConfigUtils.getConfigData("folderTitle").replace("*", Integer.toString(j)))))
							.trim();
					log.info("title of the Content selected to share" + " " + titleOftheSelectedContent);
					log.info("Attribute of the content at" + libEntries.get(i) + " "
							+ SeleniumUtility.getAttribute(libEntries.get(i), "assetclass"));
					SeleniumUtility.Click(driver, 30, driver.findElement(By.xpath(
							ConfigUtils.getConfigData("listOf_ContextMenuIcon").replace("*", Integer.toString(j)))));
					log.info("Clicked on assetContextMenuIcon");
					JavascriptUtils.scrollIntoView(shareLinkBtn, driver);
					try {
						SeleniumUtility.Click(driver, 20, shareLinkBtn);
					} catch (Exception e) {
						log.error("Failed to click on share btn" + e.getMessage());
					}
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
					break;
				}

			}
		} catch (Exception e) {
			log.error("Failed to click on Share link" + e.getMessage());
			e.printStackTrace();
		}
		return titleOftheSelectedContent;
	}

	final public boolean shareLinkPopUpDisplayed() {
		return SeleniumUtility.elementDispalyed(Share_popup);

	}

	public String getNameOfSharingContent() {
		return SeleniumUtility.getElementText(NameOfContentToBeShared).trim();

	}

	public String getShareLink() {
		String ShareLinkValue = null;
		try {
			if (!SeleniumUtility.getAttribute(shareLink, "title").isBlank()) {
				ShareLinkValue = SeleniumUtility.getElementText(shareLink);
			}
		} catch (Exception e) {
			log.error("Failed to get sharelLink" + e.getMessage());
			e.printStackTrace();

		}
		return ShareLinkValue;
	}

	public String captureSuccessAlertMessage() {
		log.info("Share link copied success alert message" + " " + SeleniumUtility.getElementText(alertMessage));
		return SeleniumUtility.getElementText(alertMessage);
	}

	public ArrayList<Boolean> validateFieldsInSharePopUp(String contentpermissionDetails) {
		ArrayList<Boolean> sharePopUpStatus = new ArrayList<Boolean>();
		sharePopUpStatus.add(SeleniumUtility.elementDispalyed(NameOfContentToBeShared));
		sharePopUpStatus.add(SeleniumUtility.elementDispalyed(shareLink));
		if (SeleniumUtility.getElementText(linkPermissionDetails).trim().equals(contentpermissionDetails)) {
			sharePopUpStatus.add(true);
		}
		sharePopUpStatus.add(SeleniumUtility.elementDispalyed(copy_Exit));
		sharePopUpStatus.add(SeleniumUtility.elementDispalyed(copyLink));
		return sharePopUpStatus;

	}

	public void closeSharePopUpWindow() throws Exception {
		if (SeleniumUtility.elementDispalyed(Share_popup)) {
			SeleniumUtility.Click(driver, 20, closeSharePopup);
			Thread.sleep(3000);
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
			log.info("Closed the share popup window");
		} else {
			log.info("Share popup is not displaying");
		}
	}

	public String shareTheContentAndCaptureSuccessAlertMessage(String locatorId) {
		String shareLinkCopied = null;
		try {
			SeleniumUtility.Click(driver, 20, driver.findElement(By.id(locatorId)));
			shareLinkCopied = captureSuccessAlertMessage();
		} catch (Exception e) {
			log.error("Failed to capture alert message due to" + e.getMessage());
			e.printStackTrace();
		}
		return shareLinkCopied;

	}

	public String clickOnShareLinkTab() {

		String share_Link = null;
		if (SeleniumUtility.elementDispalyed(shareLinkTab)) {

			SeleniumUtility.Click(driver, 15, shareLinkTab);
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
			share_Link = getShareLink();
		} else {
			share_Link = getShareLink();
		}
		return share_Link;
	}

	public boolean verifyAssetViewerIsDisplayed() {
		boolean assetViewer = false;
		if (SeleniumUtility.elementDispalyed(Share_popup)) {
			assetViewer = true;
		}
		return assetViewer;
	}

	public String validateAssetTitleInViewer() {
		if (verifyAssetViewerIsDisplayed()) {

			log.info("Asset viewer is displayed for the asset" + " "
					+ SeleniumUtility.getElementText(assetTitleInViewerHeader));

		}
		return SeleniumUtility.getElementText(assetTitleInViewerHeader);
	}

	public boolean shareAssetFeatureIsEnabled() {
		boolean shareAssetEnabled = false;
		if (SeleniumUtility.elementDispalyed(shareAssetTab)) {
			shareAssetEnabled = true;
		}
		return shareAssetEnabled;
	}

	public List<Boolean> verifyShareAssetPopup() {
		List<Boolean> assetSharePopUpFields = null;
		try {
			if (shareAssetFeatureIsEnabled()) {
				if (SeleniumUtility.getAttribute(shareAssetTab, "class").contains("activeInfoTab")) {

					assetSharePopUpFields = shareAssetFields();
				} else {
					SeleniumUtility.Click(driver, 20, shareAssetTab);
				}

			} /*
				 * else if (!SeleniumUtility.elementDispalyed(shareAssetTab)) {
				 * 
				 * assetSharePopUpFields = sharePopUpTargetList();
				 * log.info("Asset share functionality is not enabled for the tenant"); }
				 */

		} catch (Exception e) {
			log.error("failed to verify share asset popup due to " + e.getMessage());
			e.printStackTrace();
		}
		return assetSharePopUpFields;

	}

	public String beginShareValue() {
		String beginDate = null;
		try {
			beginDate = SeleniumUtility.getAttribute(beginShareDate, "value");
			log.info("Begin date" + " " + beginDate);
		} catch (Exception e) {
			log.error("Begin share date value is null" + e.getMessage());
			e.printStackTrace();
		}
		return beginDate;

	}

	public ArrayList<Boolean> shareAssetFields() {
		ArrayList<Boolean> shareAssetPopUp = new ArrayList<Boolean>();

		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(NameOfAssetToBeShared));
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(enterEmailId));
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(addRecipientsBtn));
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(mailIdTextArea));
		if (SeleniumUtility.getElementText(beginShare).trim().equals("Begin Share")) {
			shareAssetPopUp.add(true);
		}
		if (SeleniumUtility.getElementText(EndShare).trim().equals("End Share")) {
			shareAssetPopUp.add(true);
		}
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(beginShareCalIcon));
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(endShareCalIcon));
		if (beginShareValue() != null) {
			SeleniumUtility.getElementText(beginShareDate);
			shareAssetPopUp.add(true);
		}
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(endShareDate));
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(assetShare_ShareBtn));
		shareAssetPopUp.add(SeleniumUtility.elementDispalyed(assetShare_exitBtn));
		return shareAssetPopUp;

	}

	public List<Boolean> sharePopUpTargetList() {
		List<Boolean> sharePopUpTargetList = new ArrayList<Boolean>();
		boolean targetList[] = { true, true, true, true, true, true, true, true, true, true, true, true };
		sharePopUpTargetList = Booleans.asList(targetList);
		return sharePopUpTargetList;
	}

	public void selectEndDate(String month, String year, String dateVal) throws Exception {
		SeleniumUtility.Click(driver, 20, endShareCalIcon);
		// JavascriptUtils.clickElementByJS(endShareCalIcon, driver);
		// SeleniumUtility.moveToElement(driver.findElement(By.cssSelector(".DynarchCalendar-topCont")));

		SeleniumUtility.Click(driver, 15, calHeader);
		// JavascriptUtils.clickElementByJS(calHeader, driver);
		pickYear(year);
		Thread.sleep(2000);
		pickMonth(month);
		pickDate(dateVal);

	}

	public void pickMonth(String month) {

		boolean monthSelected = false;

		if (driver.findElements(By.xpath(ConfigUtils.getConfigData("months_path"))).size() > 0) {
			Months = ConfigUtils.getConfigData("months");
			for (int i = 1; i < 5; i++) { // rows
				String months_col = Months.replace("#", Integer.toString(i));
				for (int j = 1; j < 4; j++) {// cols
					log.info(SeleniumUtility.getElementText(
							driver.findElement(By.xpath(months_col.replace("*", Integer.toString(j))))));
					if (SeleniumUtility
							.getElementText(driver.findElement(By.xpath(months_col.replace("*", Integer.toString(j)))))
							.equalsIgnoreCase(month)) {
						SeleniumUtility.Click(driver, 20,
								driver.findElement(By.xpath(months_col.replace("*", Integer.toString(j)))));
						monthSelected = true;
						break;
					}
				}

				if (monthSelected) {
					break;
				}
			}

		}
	}

	public void pickYear(String year) {

		log.info("current year value displaying in calendar is" + " " + SeleniumUtility
				.getElementText(driver.findElement(By.xpath(ConfigUtils.getConfigData("calYearPath")))));
		SeleniumUtility.Clear(driver, 15, driver.findElement(By.xpath(ConfigUtils.getConfigData("calYearPath"))));
		driver.findElement(By.xpath(ConfigUtils.getConfigData("calYearPath"))).sendKeys(year);
	}

	public void pickDate(String dateVal) {
		boolean dateSelected = false;
		for (int i = 1; i < 7; i++) {// rows
			String date = ConfigUtils.getConfigData("dateValueXpath").replace("#", Integer.toString(i));
			for (int j = 1; j < 9; j++) {
				if (SeleniumUtility.getElementText(driver.findElement(By.xpath(date.replace("*", Integer.toString(j)))))
						.equals(dateVal)
						&& SeleniumUtility
								.getAttribute(driver.findElement(By.xpath(date.replace("*", Integer.toString(j)))),
										"class")
								.contains("DynarchCalendar-day")) {

					SeleniumUtility.Click(driver, 20,
							driver.findElement(By.xpath(date.replace("*", Integer.toString(j)))));
					dateSelected = true;
					break;
				}

			}
			if (dateSelected) {
				break;
			}

		}
	}

	public String enterDataInTheAssetSharePopup(String mailId, String month, String year, String dateVal,
			String AssetTitle) {

		String assetShared = "";
		try {
			enterEmailId.sendKeys(mailId);
			SeleniumUtility.Click(driver, 20, addRecipientsBtn);
			selectEndDate(month, year, dateVal);
			SeleniumUtility.Click(driver, 20, assetShare_ShareBtn);
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
			if (SeleniumUtility.elementDispalyed(shareWarningBody)) {
				log.info("************");
				assetTitleWithExtension = SeleniumUtility.getElementText(assetTitle_ShareAssetPopup);
				String[] asset_Title = SeleniumUtility.getElementText(assetTitle_ShareAssetPopup).split("\\.");
				if (AssetTitle.equals(asset_Title[0])) {
					SeleniumUtility.Click(driver, 20, assetShare_ShareBtn);
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
					assetShared = SeleniumUtility.getElementText(alertMessage);

				}

			}

		} catch (Exception e) {
			log.error("Failed to enter data in the asset share pop-up" + e.getMessage());
			e.printStackTrace();
		}
		return assetShared;

	}

	public String enterDataInTheAssetSharePopupUsingJs(String mailId, String dateVal, String AssetTitle) {
		String assetShared = "";
		try {
			enterEmailId.sendKeys(mailId);
			SeleniumUtility.Click(driver, 20, addRecipientsBtn);
			SimpleDateFormat sd = new SimpleDateFormat("hh:mm aa");
			dateVal = dateVal.concat(sd.format(new Date()).toString());
			JavascriptUtils.selectDateByJS(endShareDate, dateVal);
			SeleniumUtility.Click(driver, 20, assetShare_ShareBtn);
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
			if (SeleniumUtility.elementDispalyed(shareWarningBody)) {
				log.info("************");
				assetTitleWithExtension = SeleniumUtility.getElementText(assetTitle_ShareAssetPopup);
				String[] asset_Title = SeleniumUtility.getElementText(assetTitle_ShareAssetPopup).split("\\.");
				if (AssetTitle.equals(asset_Title[0])) {
					SeleniumUtility.Click(driver, 20, assetShare_ShareBtn);
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
					assetShared = SeleniumUtility.getElementText(alertMessage);

				}

			}

		} catch (Exception e) {
			log.error("Failed to enter data in the asset share pop-up" + e.getMessage());
			e.printStackTrace();

		}
		return assetShared;
	}

}
