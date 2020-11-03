package com.qa.clearpc.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.GenericUtils;
import com.qa.clearpc.utility.JavascriptUtils;
import com.qa.clearpc.utility.SeleniumUtility;

public class ShareLinkPage extends BaseClass {
	GenericUtils genericUtils;

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
					SeleniumUtility.Click(driver, 15, driver.findElement(By.xpath(
							ConfigUtils.getConfigData("listOf_ContextMenuIcon").replace("*", Integer.toString(j)))));
					log.info("Clicked on assetContextMenuIcon");
					JavascriptUtils.scrollIntoView(shareLinkBtn, driver);
					SeleniumUtility.Click(driver, 10, shareLinkBtn);
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.loadingSymbol());
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

	public void closeSharePopUpWindow() {
		if (SeleniumUtility.elementDispalyed(Share_popup)) {
			SeleniumUtility.Click(driver, 20, closeSharePopup);
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
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
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

}
