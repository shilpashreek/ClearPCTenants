package com.qa.clearpc.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.GenericUtils;
import com.qa.clearpc.utility.JavascriptUtils;
import com.qa.clearpc.utility.SeleniumUtility;

public class SearchPage extends BaseClass {

	int searchResult = 0;
	GenericUtils genericUtils;
	String testFolderTitle = null;
	List<WebElement> folders_Title = null;
	String folderTitle = null;
	int folderSize = 0;
	String folderOpenCloseStatus_xpath = null;
	public String testFolderName = "";
	String Folders_copyPopup_xpath = null;
	String folderIconXpath = null;
	String level1_folder_xpath = null;
	String subfolder_folderIcon_xpath = null;
	String subfolder_level2Folders_xpath = null;
	String newName = "";
	public String AssetToBeCopied = "";
	String copiedToFolder = "";
	@FindBy(id = "AdavncedSearchButton")
	WebElement Library_searchBtn;
	@FindBy(id = "txtCurrentSearchText")
	WebElement searchTextArea;
	@FindBy(css = ".walkme-tooltip-content")
	WebElement searchTips;
	@FindBy(id = "autoSuggestULSearchLibrary")
	WebElement search_filters_suggestion;
	@FindBy(css = ".filterType_Folders")
	WebElement Folder_Checkbox;
	@FindBy(css = ".filterType_Documents")
	WebElement Documents_Checkbox;
	@FindBy(css = ".filterType_Images")
	WebElement images_Checkbox;
	@FindBy(css = ".filterType_Videos")
	WebElement videos_Checkbox;
	@FindBy(id = "spanSearchCount")
	WebElement searchCount;
	@FindBy(className = "schNoResults")
	WebElement invalidSearchResult;
	@FindBy(id = "activeGridView")
	WebElement assetViews;
	@FindBy(xpath = "//*[@id=\"browse\"]/li[1]/div/div[1]/div/div[1]/div")
	WebElement folderThumbnail;
	@FindBy(id = "AddFolder")
	WebElement addFolderIcon;
	@FindBy(id = "LibraryBrowseTree")
	WebElement libBrowseTree;
	@FindBy(className = "popupBG")
	WebElement addFolderPopUp;
	@FindBy(id = "folderPath")
	WebElement pathofFolder;
	@FindBy(xpath = "//input[@class=' smallControlViewForm']")
	WebElement FolderTitle;
	@FindBy(xpath = "//span[starts-with(@id,'customCheckBoxSpan') and @title='Inherit Access from Parent']")
	WebElement permissionAccess_Btn;
	@FindBy(id = "cancelFolderBtn")
	WebElement addPopUp_closeBtn;
	@FindBy(id = "saveFolderBtn")
	WebElement save_Add_another;
	@FindBy(xpath = "//div[@title='Save & close']")
	WebElement save_close;
	@FindBy(id = "close1")
	WebElement closePopUp_cross;
	@FindBy(className = "alertMessageUCWrapper")
	WebElement alertMessage;
	@FindBy(id = "AssetContextMenu")
	WebElement assetContextMenuIcon;
	@FindBy(id = "_GroupActionPanel")
	WebElement assetContextMenuOptions;
	@FindBy(id = "popUpHeader1")
	WebElement popupHeader;
	@FindBy(xpath = "//ul[@id='_GroupActionPanel']/li[@id='EditFolder']")
	WebElement editFolder;
	@FindBy(xpath = "//ul[@id='_GroupActionPanel']/li[@id='initiateSaveAs']")
	WebElement copyAsset;
	@FindBy(xpath = "//ul[@id='_GroupActionPanel']/li[@id='initiateMove']")
	WebElement moveAsset;
	@FindBy(xpath = "//ul[@id='_GroupActionPanel']/li[@id='renameAssetsId']")
	WebElement renameAsset;
	@FindBy(id = "Copy Now1")
	WebElement copyNowBtn;
	@FindBy(id = "ManageAssetSaveConfirmation")
	WebElement copyAlertPopup;
	@FindBy(xpath = "//div[@class='alertText']/div")
	WebElement copyPopupBody;
	@FindBy(id = "Yes2")
	WebElement Copy_YesBtn;
	@FindBy(id = "Duplicates")
	WebElement renamePopUp;
	@FindBy(id = "Rename2")
	WebElement renameBtn;
	@FindBy(css = ".GroupAction.FR.iconSpacer.linked")
	WebElement actionsTab;
	@FindBy(css = "li#moveObject")
	WebElement pasteOption;
	@FindBy(id = "Yes1")
	WebElement paste_YesBtn;// Rename1
	@FindBy(id = "Rename1")
	WebElement paste_RenameBtn;
	@FindBy(id = "renameAsset")
	WebElement assetRenameTextField;
	@FindBy(id = "Save1")
	WebElement rename_saveBtn;

	public SearchPage() {
		PageFactory.initElements(driver, this);
		genericUtils = new GenericUtils();
	}

	public String getSearchTipsText() {
		return SeleniumUtility.getElementText(searchTips);
	}

	public void clickOnSearchBtn() {
		SeleniumUtility.Click(driver, 20, Library_searchBtn);
	}

	public void enterSearchText_CaptureFilterSuggestions(String searchAsset, String content, WebElement ele) {
		int FilterSize = 0;
		List<WebElement> Filter_suggestions = null;

		SeleniumUtility.Clear(driver, 10, searchTextArea);
		SeleniumUtility.sendkeys(driver, 15, searchTextArea, searchAsset);
		SeleniumUtility.waitTillElementIsVisibile(driver, 20, search_filters_suggestion);
		if (SeleniumUtility.elementDispalyed(search_filters_suggestion)) {
			Filter_suggestions = driver.findElements(By.xpath(ConfigUtils.getConfigData("FilterSuggestions")));
			FilterSize = Filter_suggestions.size();
			log.info("Total Library search filter options are--" + FilterSize);
			log.info("Library search filter options are-->");
			for (WebElement FilterNames : Filter_suggestions) {
				log.info(FilterNames.getText());
			}
		} else {
			log.error("Filter autosuggestion is not displayed");
		}

		for (int i = 0; i < FilterSize; i++) {
			if (Filter_suggestions.get(i).getText().equalsIgnoreCase(content)) { // Folders
				// WebElement ele=driver.findElement(By.xpath(selector)); //added on 23/10
				SeleniumUtility.Click(driver, 15, ele); // Folder_Checkbox
				break;
			}
		}
		searchTextArea.sendKeys(Keys.ENTER);
	}

	public void searchResultsVerificationForAllContentTypes(String searchAsset, String content, String locator) {
		List<WebElement> Filter_suggestions = null;

		SeleniumUtility.Clear(driver, 10, searchTextArea);
		SeleniumUtility.sendkeys(driver, 15, searchTextArea, searchAsset);
		SeleniumUtility.waitTillElementIsVisibile(driver, 20, search_filters_suggestion);
		Filter_suggestions = driver.findElements(By.xpath(ConfigUtils.getConfigData("FilterSuggestions")));
		for (int i = 0; i < Filter_suggestions.size(); i++) {
			if (Filter_suggestions.get(i).getText().equalsIgnoreCase(content)) { // Folders
				WebElement ele = driver.findElement(By.cssSelector(locator)); // added on 23/10

				try {
					SeleniumUtility.Click(driver, 15, driver.findElement(By.cssSelector(locator)));// Folder_Checkbox
				} catch (Exception e) {
					log.error("Failed to click ele" + e.getMessage());
					e.printStackTrace();

				}

				break;
			}
		}
		searchTextArea.sendKeys(Keys.ENTER);

	}

	public void captureSearchResults() {

		enterSearchText_CaptureFilterSuggestions(ConfigUtils.getConfigData("searchInput"), "Folders", Folder_Checkbox);
	}

	// gives total search results count
	public int getSearchResultsCount() {
		// int searchResult=0;
		try {
			String search_Result = SeleniumUtility.getElementText(searchCount);
			String[] searchResultCount = search_Result.split(" ");
			System.out.println(searchResultCount[0] + "/n " + searchResultCount[1] + "/n  " + searchResultCount[2]
					+ "/n" + searchResultCount[3] + "/n" + searchResultCount[4] + "/n" + searchResultCount[5] + "/n"
					+ searchResultCount[6] + "/n" + searchResultCount[7]);
			searchResult = Integer.parseInt(searchResultCount[5].replace(",", ""));
			log.info("count of search results is: " + searchResult);
		} catch (Exception e) {
			log.error("failed to get search Count");
		}
		return searchResult;
	}

	public boolean verifySearchResults(String contentType, String searchText) {
		boolean searchStatus = false;
		try {
			if (getSearchResultsCount() > 0) // searchResult>0
			{
				List<WebElement> searchResults = driver
						.findElements(By.xpath(ConfigUtils.getConfigData("SearchResultFoldertitles")));
				// String
				// attributeValue=SeleniumUtility.getAttribute(driver.findElement(By.xpath(ConfigUtils.getConfigData("SearchResultFoldertitles"))),
				// "assetclass");

				for (int i = 0; i < searchResult; i++) {

					String attrvalue = searchResults.get(i).getAttribute("assetclass");
					String title = searchResults.get(i).getAttribute("title");
					if (attrvalue.equalsIgnoreCase(contentType) && title.contains(searchText)) // title.contains(searchText)
																								// changed on 5/10/2020
					{
						searchStatus = true;
						log.info("Title of the" + " " + contentType + " " + " " + title);

					}
					if (searchStatus) { // added on 19/10
						break;
					}
					log.info("Content has been searched successfully within the organisation");
				}

			}
		} catch (Exception e) {
			log.error("Failed to search the content within the organisation");
		}
		return searchStatus;
	}

	public String enterInvalidData(String searchData) {
		String search_res = null;
		try {
			SeleniumUtility.Clear(driver, 10, searchTextArea);
			SeleniumUtility.sendkeys(driver, 15, searchTextArea, searchData);
			Thread.sleep(2000);
			if (!SeleniumUtility.elementDispalyed(search_filters_suggestion)) {
				// genericUtils = new GenericUtils();
				log.info("Augosuggetion is not displayed for invalid search data");
				searchTextArea.sendKeys(Keys.ENTER);
				SeleniumUtility.waitTill_invisibility_of_Element(driver, 10, genericUtils.textLoadingSymbol());
				search_res = SeleniumUtility.getElementText(invalidSearchResult);

			}
		} catch (Exception e) {
			log.error("Autosuggestions is displaying for invalid search data" + e.getMessage());
			log.error("Could not find the invalid search result" + e.getMessage());
		}
		return search_res;

	}

	public int captureAutosuggestion(String searchAsset, String locator) {
		ArrayList<String> autosuggestionsList = new ArrayList<String>();
		int suggestionsCount = 0;
		try {
			SeleniumUtility.Clear(driver, 10, searchTextArea);
			SeleniumUtility.sendkeys(driver, 15, searchTextArea, searchAsset);
			SeleniumUtility.waitTillElementIsVisibile(driver, 20, search_filters_suggestion);
			List<WebElement> autoSuggestions_list = driver.findElements(By.xpath(locator));
			suggestionsCount = autoSuggestions_list.size();
			log.info("Autosuggested list is as follows:");
			for (int i = 0; i < autoSuggestions_list.size(); i++) {
				autosuggestionsList.add(autoSuggestions_list.get(i).getText());
				Collections.sort(autosuggestionsList);
			}
			System.out.println(autosuggestionsList);
		} catch (Exception e) {
			log.error("could not print the autosuggestions " + e.getMessage());
		}
		return suggestionsCount;
	}

	public void performBlankSearch() {
		SeleniumUtility.Click(driver, 15, Library_searchBtn);
		searchTextArea.sendKeys(Keys.ENTER);

	}

	public boolean applyGridView() {
		boolean gridAssetView = false;
		try {
			SeleniumUtility.Click(driver, 10, assetViews);
			SeleniumUtility.Click(driver, 5, driver.findElement(By.xpath(ConfigUtils.getConfigData("gridView"))));
			String mode = SeleniumUtility.getAttribute(assetViews, "class");
			log.info(mode);
			if (SeleniumUtility.getAttribute(assetViews, "class").equals("FR listGridView thumbnailViewIcon")) {
				log.info("Grid asset view is displaying");
				gridAssetView = true;
			}
		} catch (Exception e) {
			log.error("Could not select Grid asset view");
		}
		return gridAssetView;

	}

	public boolean applylistAssetView() {
		boolean listAssetView = false;
		try {
			SeleniumUtility.Click(driver, 10, assetViews);
			SeleniumUtility.Click(driver, 5, driver.findElement(By.xpath(ConfigUtils.getConfigData("listView"))));
			// SeleniumUtility.waitTill_invisibility_of_Element(driver, 15,
			// genericUtils.textLoadingSymbol());
			String mode = SeleniumUtility.getAttribute(assetViews, "class");
			log.info(mode);
			if (SeleniumUtility.getAttribute(assetViews, "class").equals("FR listGridView listViewIcon")) {
				log.info("List Asset view is displaying");
				listAssetView = true;
			}
		} catch (Exception e) {
			log.error("Could not list Grid asset view");
		}
		return listAssetView;

	}

	public boolean applyDetailedAssetView() {
		boolean detailAssetView = false;
		try {
			SeleniumUtility.Click(driver, 10, assetViews);
			SeleniumUtility.Click(driver, 5, driver.findElement(By.xpath(ConfigUtils.getConfigData("detailedView"))));
			// SeleniumUtility.waitTill_invisibility_of_Element(driver, 15,
			// genericUtils.textLoadingSymbol());
			String mode = SeleniumUtility.getAttribute(assetViews, "class");
			log.info(mode);
			if (SeleniumUtility.getAttribute(assetViews, "class").equals("FR listGridView detailViewIcon")) {
				log.info("Detail Asset view is displaying");
				detailAssetView = true;
			}
		} catch (Exception e) {
			log.error("Could not select detailed asset view");
		}
		return detailAssetView;

	}

	public void navigateToTestFolder() {

		try {
			for (int i = 0; i < getSearchResultsCount(); i++) {
				testFolderTitle = SeleniumUtility.getAttribute(
						driver.findElement(By.xpath(ConfigUtils.getConfigData("FolderTitle_path"))), "title");
				String eleText = SeleniumUtility
						.getElementText(driver.findElement(By.xpath(ConfigUtils.getConfigData("FolderTitle_path"))));
				log.info("Folder title" + testFolderTitle);
				log.info("Folder titleText" + eleText);
				if (testFolderTitle.contains("QATest") || testFolderTitle.contains("qatest")
						|| testFolderTitle.contains("QAtest")) {

					SeleniumUtility.doubleClick(folderThumbnail);
					break;
				}
			}
		} catch (Exception e) {
			log.error("Failed to get the folder name" + e.getMessage());
			e.printStackTrace();
		}

	}

	public void verifyAddFolderIconIsPresent() {
		try {
			if (SeleniumUtility.elementDispalyed(addFolderIcon)) {
				SeleniumUtility.Click(driver, 15, addFolderIcon);
				log.info("Add folder functionality is enabled and clicked successfully");

			}

		} catch (Exception e) {
			log.error("Failed to click on Add Folder icon");
		}

	}

	public void selectTestFolder() {
		for (int i = 1; i < 5; i++) {
			String j = Integer.toString(i);
			String folder_Title = ConfigUtils.getConfigData("foldersTitle_xpath");
			String folderTitle_xpath = folder_Title.replace("*", j);
			folderTitle = SeleniumUtility.getElementText(driver.findElement(By.xpath(folderTitle_xpath)));

			String folderOpenCloseStatus = ConfigUtils.getConfigData("libraryBrowseTreeFolderStatus");
			folderOpenCloseStatus_xpath = folderOpenCloseStatus.replace("*", j);

			if (!(SeleniumUtility.getAttribute(driver.findElement(By.xpath(folderOpenCloseStatus_xpath)), "class")
					.contains("clear-folderOpenF"))
					&& (folderTitle.contains("SUPPORT PROJECT") || folderTitle.contains("supports test project")
							|| folderTitle.contains("Support Test Project")
							|| folderTitle.contains("support test project") || folderTitle.contains("QAtestFolder")
							|| folderTitle.contains("QA Test Project"))) {
				SeleniumUtility.Click(driver, 10, driver.findElement(By.xpath(folderTitle_xpath)));
				break;
			}

		}

	}

	public boolean addFolderPopUpDisplayed() {
		boolean popUpDisplayed = false;
		try {
			if (SeleniumUtility.elementDispalyed(addFolderPopUp)) {
				popUpDisplayed = true;
				log.info("Add folder popup is displayed");
			}
		} catch (Exception e) {
			log.error("Add Folder popup is not displayed");
		}
		return popUpDisplayed;
	}

	public void closePopUp() {
		if (SeleniumUtility.elementDispalyed(addFolderPopUp)) {
			SeleniumUtility.Click(driver, 15, closePopUp_cross);
		}
	}

	public boolean pathDisplayed() {

		boolean pathStatus = false;
		try {
			if (SeleniumUtility.elementDispalyed(pathofFolder)) {
				pathStatus = true;
			}
		} catch (Exception e) {
			log.error("Failed to find the path field and its status");
		}
		return pathStatus;
	}

	public boolean folderTitle() {
		boolean FolderTitleStatus = false;
		try {
			if (SeleniumUtility.elementDispalyed(FolderTitle)) {
				FolderTitleStatus = true;
			}
		} catch (Exception e) {
			log.error("" + e.getMessage());
			e.printStackTrace();
		}
		return FolderTitleStatus;
	}

	public boolean closeAddPopup() {
		boolean closeBtn = false;
		try {
			if (SeleniumUtility.elementDispalyed(addPopUp_closeBtn)) {
				closeBtn = true;
			}

		} catch (Exception e) {
			log.error("" + e.getMessage());
			e.printStackTrace();
		}
		return closeBtn;
	}

	public boolean saveBtnStatus() {
		boolean saveBtn = false;
		try {
			if (save_Add_another.isDisplayed() && save_close.isDisplayed()) {
				if (SeleniumUtility.getAttribute(save_Add_another, "class").contains("fadeAndDisable")
						&& SeleniumUtility.getAttribute(save_close, "class").contains("fadeAndDisable")) {
					saveBtn = true;
				}
			}

		} catch (Exception e) {
			log.error("" + e.getMessage());
			e.printStackTrace();
		}
		return saveBtn;
	}

	public boolean inheritAccess() {
		boolean InheritingAccess = false;
		try {

			if (SeleniumUtility.elementDispalyed(permissionAccess_Btn)) {
				InheritingAccess = true;
			}

		} catch (Exception e) {
			log.error("" + e.getMessage());
			e.printStackTrace();
		}
		return InheritingAccess;
	}

	public ArrayList<Boolean> verifyAddFolderPopUpFields() {

		ArrayList<Boolean> popupValuesStatus = new ArrayList<Boolean>();
		popupValuesStatus.add(pathDisplayed());
		popupValuesStatus.add(folderTitle());
		popupValuesStatus.add(closeAddPopup());
		popupValuesStatus.add(saveBtnStatus());
		popupValuesStatus.add(inheritAccess());
		return popupValuesStatus;

	}

	public ArrayList<Boolean> targetList() {
		ArrayList<Boolean> target = new ArrayList<Boolean>();
		target.add(true);
		target.add(true);
		target.add(true);
		target.add(true);
		target.add(true);
		return target;
	}

	public String addNewFolder() {

		String alertMsg = null;

		SeleniumUtility.waitTillElementIsVisibile(driver, 20, addFolderPopUp);
		SeleniumUtility.sendkeys(driver, 10, FolderTitle, generateTestFolderName());
		if (SeleniumUtility.getAttribute(permissionAccess_Btn, "id").contains("customCheckBoxSpan_0")) {
			log.info("Inherit from parent CheckBox is Disbaled");
		} else if (SeleniumUtility.getAttribute(permissionAccess_Btn, "id").contains("customCheckBoxSpan_1")) {
			SeleniumUtility.Click(driver, 10, permissionAccess_Btn);
			log.info("Clicked on inherit properties");
		}

		SeleniumUtility.Click(driver, 10, save_close);
		// genericUtils = new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());

		if (SeleniumUtility.elementDispalyed(alertMessage)) {
			alertMsg = SeleniumUtility.getElementText(alertMessage);
			log.info("Alert message is" + alertMsg);

		}
		return alertMsg;

	}

	public String generateTestFolderName() {
		// GenericUtils generUtils = new GenericUtils();
		String date = genericUtils.getTodaysDate("ddMMyyyy mm:ss");
		String folderName = "QATestFolder";
		testFolderName = folderName.concat(date);
		return testFolderName;

	}

	public void searchNewFolder() {
		clickOnSearchBtn();
		SeleniumUtility.Clear(driver, 10, searchTextArea);
		SeleniumUtility.sendkeys(driver, 15, searchTextArea, testFolderName);
		searchTextArea.sendKeys(Keys.ENTER);

	}

	public String saveAndAddAnother() {

		String alertMsg = null;

		SeleniumUtility.waitTillElementIsVisibile(driver, 20, addFolderPopUp);
		SeleniumUtility.Clear(driver, 10, FolderTitle);
		SeleniumUtility.sendkeys(driver, 10, FolderTitle, generateTestFolderName());
		if (permissionAccess_Btn.isEnabled()) {
			SeleniumUtility.Click(driver, 10, permissionAccess_Btn);
			log.info("clicked on inherit proprties from parent checkbox");
		}

		SeleniumUtility.Click(driver, 10, save_Add_another);
		// genericUtils = new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
		if (SeleniumUtility.elementDispalyed(alertMessage)) {
			alertMsg = SeleniumUtility.getElementText(alertMessage);
			log.info("Alert message is" + alertMsg);

		}
		return alertMsg;

	}

	public boolean editFolderPopUpDisplayed() {
		boolean editFolderPopupDisplayed = false;

		try {

			SeleniumUtility.waitTillElementIsVisibile(driver, 10, assetContextMenuIcon);
			SeleniumUtility.Click(driver, 10, assetContextMenuIcon);
			// SeleniumUtility.waitTillElementIsVisibile(driver, 10,
			// assetContextMenuOptions);
			// SeleniumUtility.Click(driver, 10, assetContextMenuOptions);
			SeleniumUtility.Click(driver, 10, editFolder);
			SeleniumUtility.waitTillElementIsVisibile(driver, 10, addFolderPopUp);
			if (SeleniumUtility.getElementText(popupHeader).contains("Edit in ")) {
				editFolderPopupDisplayed = true;
				log.info("Edit folder popup is displayed");
			}

		} catch (Exception e) {
			log.error("Failed to display edit folder popup" + e.getMessage());
			e.printStackTrace();
		}

		return editFolderPopupDisplayed;

	}

	public String editFolderName(String currentFolderName) {
		String newFolderName = currentFolderName.concat("_Edited");
		String alertMsg = null;
		try {
			SeleniumUtility.Clear(driver, 10, FolderTitle);
			SeleniumUtility.sendkeys(driver, 10, FolderTitle, newFolderName);
			SeleniumUtility.Click(driver, 10, save_close);
			// genericUtils = new GenericUtils();
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());

			if (SeleniumUtility.elementDispalyed(alertMessage)) {
				alertMsg = SeleniumUtility.getElementText(alertMessage);
				log.info("Alert message after editing folder" + alertMsg);

			}

		} catch (Exception e) {
			log.error("Failed to edit the folder" + e.getMessage());
			e.printStackTrace();
		}
		return alertMsg;
	}

	public String selectTestAssetAndPerformAction(String contentType, String searchText, WebElement ele,
			WebElement filterOption) throws Exception {

		// String AssetToBeCopied = "";

		enterSearchText_CaptureFilterSuggestions(searchText, "Videos", filterOption);
		// genericUtils = new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		Thread.sleep(2000);
		try {
			if (getSearchResultsCount() > 0) {

				List<WebElement> searchResults = driver
						.findElements(By.xpath(ConfigUtils.getConfigData("SearchResultFoldertitles")));

				for (int i = 1; i < getSearchResultsCount(); i++) {
					String searchResults_path = ConfigUtils.getConfigData("listOf_SearchResultFoldertitles");
					String searchResults_xpath = searchResults_path.replace("*", Integer.toString(i));
					String attrvalue = SeleniumUtility.getAttribute(driver.findElement(By.xpath(searchResults_xpath)),
							"assetclass");
					String title = SeleniumUtility.getAttribute(driver.findElement(By.xpath(searchResults_xpath)),
							"title");
					if (attrvalue.equalsIgnoreCase(contentType)
							&& (title.contains("Test") || title.contains("test") || title.contains("TEST"))) // title.contains(searchText)
					{
						String j = Integer.toString(i);
						String contextMenuIcon = ConfigUtils.getConfigData("listOf_ContextMenuIcon");
						String contextMenuIcon_xpath = contextMenuIcon.replace("*", j);

						AssetToBeCopied = SeleniumUtility
								.getElementText(driver.findElement(By.xpath(searchResults_xpath)));
						log.info("Asset title which is to be copied is" + " " + AssetToBeCopied);
						SeleniumUtility.waitTillElementIsVisibile(driver, 15, assetContextMenuIcon);
						SeleniumUtility.Click(driver, 10, driver.findElement(By.xpath(contextMenuIcon_xpath)));
						// SeleniumUtility.Click(driver, 15, assetContextMenuIcon);
						JavascriptUtils.scrollIntoView(ele, driver);
						SeleniumUtility.Click(driver, 10, ele);
						log.info("Title of the" + " " + contentType + " " + " " + title);
						break;

					}

				}

			}
		} catch (Exception e) {
			log.info("Failed to copy the asset" + e.getMessage());
			e.printStackTrace();
		}
		return AssetToBeCopied;

	}

	public String ClickOncopyAsset() throws Exception {
		String AssetTitle = selectTestAssetAndPerformAction(ConfigUtils.getConfigData("CopyAsset_content_type"),
				ConfigUtils.getConfigData("searchInput"), copyAsset, videos_Checkbox);
		return AssetTitle;
	}

	public boolean copyAssetPopUpDisplayed() {
		boolean copyTo_popUp = false;
		SeleniumUtility.waitTillElementIsVisibile(driver, 15, popupHeader);
		log.info("Copy Popup header is" + SeleniumUtility.getElementText(popupHeader));
		if (SeleniumUtility.elementDispalyed(addFolderPopUp)
				&& SeleniumUtility.getElementText(popupHeader).equals("Copy To")) {

			copyTo_popUp = true;
		}
		return copyTo_popUp;
	}

	public void copyAssetTo() {
		try {
			int folderSize = driver.findElements(By.xpath(ConfigUtils.getConfigData("Folders_list"))).size();
			clickOnSubFolder(folderSize);
			selectCopyToFolder();
			SeleniumUtility.Click(driver, 15, copyNowBtn);
		} catch (Exception e) {
			log.error("Failed to select the folder" + e.getMessage());
			e.printStackTrace();
		}

	}

	private void clickOnSubFolder(int folderSize) {

		boolean isFolderDisplayed = false;

		for (int i = 1; i <= folderSize; i++) {

			Folders_copyPopup_xpath = ConfigUtils.getConfigData("Folders_list").replace("*", Integer.toString(i));
			folderIconXpath = ConfigUtils.getConfigData("folderIcon").replace("*", Integer.toString(i));
			level1_folder_xpath = ConfigUtils.getConfigData("subFolderLevel1").replace("*", Integer.toString(i));
			subfolder_folderIcon_xpath = ConfigUtils.getConfigData("subFolderIcon").replace("#", Integer.toString(i));
			subfolder_level2Folders_xpath = ConfigUtils.getConfigData("subFolderLevel2").replace("#",
					Integer.toString(i));
			// int a = driver.findElements(By.xpath(subfolder_level2Folders_xpath)).size();
			if (SeleniumUtility.getAttribute(driver.findElement(By.xpath(Folders_copyPopup_xpath)), "class")
					.contains("aciTreeOpen")) {

				isFolderDisplayed = true;
				if (driver.findElements(By.xpath(level1_folder_xpath)).size() == 1 && SeleniumUtility
						.getAttribute(driver.findElement(By.xpath(level1_folder_xpath)), "aria-expanded")
						.equals("false")) {

					for (int j = 1; j <= driver.findElements(By.xpath(level1_folder_xpath)).size(); j++) {

						log.info("Folder status" + SeleniumUtility
								.getAttribute(driver.findElement(By.xpath(level1_folder_xpath)), "aria-expanded"));

						SeleniumUtility.Click(driver, 10, driver
								.findElement(By.xpath(subfolder_folderIcon_xpath.replace("*", Integer.toString(j)))));
						log.info("Expanded the level1 position folder");
						break;
					}

				}

			}
			if (isFolderDisplayed) {
				break;
			}

		}
	}

	private void selectCopyToFolder() {
		String folderSelected = null;
		int folderSize = driver.findElements(By.xpath(ConfigUtils.getConfigData("Folders_list"))).size();
		boolean isFolderSelected = false;
		for (int i = 1; i <= folderSize; i++) {
			Folders_copyPopup_xpath = ConfigUtils.getConfigData("Folders_list").replace("*", Integer.toString(i));
			level1_folder_xpath = ConfigUtils.getConfigData("subFolderLevel1").replace("*", Integer.toString(i));
			subfolder_level2Folders_xpath = ConfigUtils.getConfigData("subFolderLevel2").replace("#",
					Integer.toString(i));
			if (SeleniumUtility.getAttribute(driver.findElement(By.xpath(Folders_copyPopup_xpath)), "class")
					.contains("aciTreeOpen")) {

				isFolderSelected = true;

				if (driver.findElements(By.xpath(subfolder_level2Folders_xpath)).size() >= 1) {
					for (int j = 1; j <= driver.findElements(By.xpath(subfolder_level2Folders_xpath)).size(); j++) {

						String level2Folders = subfolder_level2Folders_xpath.replace("*", Integer.toString(j));
						if (!SeleniumUtility.getAttribute(driver.findElement(By.xpath(level2Folders)), "class")
								.contains("aciTreeSelected")
								&& (SeleniumUtility
										.getElementText(driver.findElement(By.xpath(
												subfolder_level2Folders_xpath.replace("*", Integer.toString(j)))))
										.contains("test"))
								|| SeleniumUtility
										.getElementText(driver.findElement(By.xpath(
												subfolder_level2Folders_xpath.replace("*", Integer.toString(j)))))
										.contains("Test")) {

							SeleniumUtility.Click(driver, 20, driver.findElement(By.xpath(level2Folders)));
							break;
						}
					}

				} else if (driver.findElements(By.xpath(level1_folder_xpath)).size() >= 1) {

					for (int j = 1; j <= driver.findElements(By.xpath(level1_folder_xpath)).size(); j++) {

						if (!SeleniumUtility.getAttribute(driver.findElement(By
								.xpath(ConfigUtils.getConfigData("subFolderLevel1").replace("*", Integer.toString(j)))),
								"class").contains("aciTreeSelected")
								&& (SeleniumUtility
										.getElementText(driver.findElement(By.xpath(ConfigUtils
												.getConfigData("subFolderLevel1").replace("*", Integer.toString(j)))))
										.contains("test"))
								|| SeleniumUtility
										.getElementText(driver.findElement(By.xpath(ConfigUtils
												.getConfigData("subFolderLevel1").replace("*", Integer.toString(j)))))
										.contains("Test")) {

							SeleniumUtility.Click(driver, 20, driver.findElement(By.xpath(
									ConfigUtils.getConfigData("subFolderLevel1").replace("*", Integer.toString(j)))));
							break;
						}

					}

				}

			}
			if (isFolderSelected) {
				break;
			}
		}
	}

	public boolean captureFolderTitle_ifCopyPopupIsDisplayed() {

		boolean aboutToCopy = false;
		try {
			aboutToCopy = SeleniumUtility.elementDispalyed(copyAlertPopup);
			log.info(SeleniumUtility.getElementText(copyPopupBody));
			String a[] = SeleniumUtility
					.getElementText(driver.findElement(By.xpath(ConfigUtils.getConfigData("AssetTitle_copyPopUp"))))
					.split("\\.");
			if (AssetToBeCopied.startsWith(a[0])) {
				String folderTitle = SeleniumUtility.getElementText(
						driver.findElement(By.xpath(ConfigUtils.getConfigData("FolderTitle_copyPopup"))));
				log.info("Asset is copying to the folder" + folderTitle);
				String b[] = SeleniumUtility
						.getAttribute(driver.findElement(By.xpath(ConfigUtils.getConfigData("FolderTitle_copyPopup"))),
								"title")
						.split("\\>");
				copiedToFolder = b[b.length - 1];
				log.info(b[b.length - 1]);

			}

		} catch (Exception e) {
			log.error("Failed to get information from copy popup" + e.getMessage());
			e.printStackTrace();
		}
		return aboutToCopy;

	}

	public String getCopySuccessAlertMessage() {
		boolean assetCopiedAlert = false;

		try {
			SeleniumUtility.Click(driver, 15, Copy_YesBtn);
			// genericUtils = new GenericUtils();
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 5, genericUtils.loadingSymbol());
			if (SeleniumUtility.elementDispalyed(alertMessage)) {
				assetCopiedAlert = true;
				log.info("Asset copied success alert message" + " " + SeleniumUtility.getElementText(alertMessage));
			} else if (SeleniumUtility.elementDispalyed(renamePopUp)) {
				assetCopiedAlert = true;
				SeleniumUtility.Click(driver, 10, renameBtn);
				SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
				log.info("Asset copied success alert message" + " " + SeleniumUtility.getElementText(alertMessage));
				SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.textLoadingSymbol());

			}

		} catch (Exception e) {
			log.error("Failed to capture success alert message" + e.getMessage());
			e.printStackTrace();
		}
		return SeleniumUtility.getElementText(alertMessage);
	}

	public boolean verifyCopiedAsset() {

		SeleniumUtility.Clear(driver, 15, searchTextArea);
		SeleniumUtility.sendkeys(driver, 10, searchTextArea, copiedToFolder);
		searchTextArea.sendKeys(Keys.ENTER);
		// genericUtils = new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		return copiedAssetInDestinationFolder(ConfigUtils.getConfigData("CopyAsset_content_type"), AssetToBeCopied);

	}

	private boolean copiedAssetInDestinationFolder(String contentType, String searchText) {
		boolean searchStatus = false;
		try {
			if (getSearchResultsCount() > 0) // searchResult>0
			{
				List<WebElement> searchResults = driver
						.findElements(By.xpath(ConfigUtils.getConfigData("SearchResultFoldertitles")));

				List<WebElement> assetTitle = driver.findElements(By.xpath(ConfigUtils.getConfigData("AssetTitle")));
				for (int i = 0; i < searchResult; i++) {

					String attrvalue = searchResults.get(i).getAttribute("assetclass");
					String title = searchResults.get(i).getText();

					String SearchText[] = searchText.split("\n");
					String titles[] = title.split("\n");
					log.info("at index 0" + titles[0] + "** " + SearchText[0]);
					if (attrvalue.equalsIgnoreCase(contentType) && titles[0].equals(SearchText[0]) // equals
					/* && title[1].equals(genericUtils.getTodaysDate("MMM dd, yyyy")) */) {
						searchStatus = true;
						log.info("Title of the" + " " + contentType + " " + " " + title);

					}
					if (searchStatus) { // added on 19/10
						break;
					}
					log.info("Content has been searched successfully within the organisation");
				}

			}
		} catch (Exception e) {
			log.error("Failed to search the content within the organisation");
		}
		return searchStatus;

	}

	public void clickOnActions(WebElement ele) throws Exception {
		String count[] = SeleniumUtility.getElementText(searchCount).split(" ");

		for (int i = 1; i <= Integer.parseInt(count[5]); i++) {

			if (!("FOLDER").equalsIgnoreCase(SeleniumUtility.getAttribute(
					driver.findElement(By.xpath(
							ConfigUtils.getConfigData("SearchResultFoldertitles").replace("*", Integer.toString(i)))),
					"assetclass"))) {
				JavascriptUtils.scrollIntoView(
						driver.findElement(By.xpath(
								ConfigUtils.getConfigData("listOf_ContextMenuIcon").replace("*", Integer.toString(i)))),
						driver);
				SeleniumUtility.Click(driver, 15, driver.findElement(By
						.xpath(ConfigUtils.getConfigData("listOf_ContextMenuIcon").replace("*", Integer.toString(i)))));
				Thread.sleep(2000);
				break;
			}

		}
		JavascriptUtils.scrollIntoView(ele, driver); // moveAsset
		SeleniumUtility.Click(driver, 20, ele);

	}

	public boolean clickOnMoveAsset() throws Exception {
		boolean assetSelectedToMove = false;
		try {
			clickOnActions(moveAsset);
			SeleniumUtility.waitTillElementIsVisibile(driver, 15, alertMessage);
			log.info("Asset moved success alert message" + SeleniumUtility.getElementText(alertMessage));
			String alertMsg[] = SeleniumUtility.getElementText(alertMessage).split("\n");
			if ("Alert".equals(alertMsg[0]) && "Assets selected for move".equals(alertMsg[1])) {
				assetSelectedToMove = true;
			}
		} catch (Exception e) {
			log.error("Failed to select the asset to be moved" + e.getMessage());
			e.printStackTrace();
		}
		// SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, alertMessage);
		Thread.sleep(2000);
		return assetSelectedToMove;
	}

	public String pasteAssetToTestFolder() {
		String successAlert = "";
		try {
			int leftSideFolders = driver.findElements(By.xpath(ConfigUtils.getConfigData("LibraryLeftFoldes"))).size();
			for (int i = leftSideFolders; i >= 0; i--) {

				String folderTtile = SeleniumUtility.getElementText(driver.findElement(
						By.xpath(ConfigUtils.getConfigData("LibraryLeftFoldes").replace("*", Integer.toString(i)))));
				driver.findElement(
						By.xpath(ConfigUtils.getConfigData("LibraryLeftFoldes").replace("*", Integer.toString(i))));
				if (folderTtile.startsWith("Test") || folderTtile.startsWith("QAtest") || folderTtile.startsWith("Test")
						|| folderTtile.startsWith("QATest")) {
					SeleniumUtility.doubleClick(driver.findElement(By
							.xpath(ConfigUtils.getConfigData("LibraryLeftFoldes").replace("*", Integer.toString(i)))));
					// genericUtils = new GenericUtils();
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.textLoadingSymbol());
					break;
				}

			}

			successAlert = capturePasteSuccessAlert();

		} catch (Exception e) {
			log.error("Failed to paste/move the asset");
		}
		return successAlert;
	}

	private String capturePasteSuccessAlert() {
		String assetPasteAlert = "";
		SeleniumUtility.Click(driver, 20, actionsTab);
		SeleniumUtility.Click(driver, 15, pasteOption);
		SeleniumUtility.Click(driver, 15, paste_YesBtn);
		// genericUtils = new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
		if (SeleniumUtility.elementDispalyed(alertMessage)) {
			assetPasteAlert = SeleniumUtility.getElementText(alertMessage);
			log.info("Asset pasted successfully alert message" + " " + SeleniumUtility.getElementText(alertMessage));
		} else if (SeleniumUtility.elementDispalyed(renamePopUp)) {
			SeleniumUtility.Click(driver, 10, paste_RenameBtn);
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
			assetPasteAlert = SeleniumUtility.getElementText(alertMessage);
			log.info("Asset pasted successfully alert message" + " " + SeleniumUtility.getElementText(alertMessage));
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.textLoadingSymbol());
		}
		return assetPasteAlert;
	}

	public String verifyRenameAction() {
		String assetRenameAlert = "";

		try {
			clickOnActions(renameAsset);
			SeleniumUtility.waitTillElementIsVisibile(driver, 20, addFolderPopUp);
			if (SeleniumUtility.getElementText(popupHeader).equals("Rename Asset")) {
				SeleniumUtility.Clear(driver, 20, assetRenameTextField);
				newName = "Test_" + "rename_" + genericUtils.getTodaysDate("MMM_dd_mm_ss");
				log.info("Asset is getting renamed as" + newName);
				SeleniumUtility.sendkeys(driver, 10, assetRenameTextField, newName);

			}
			SeleniumUtility.Click(driver, 10, rename_saveBtn);
			SeleniumUtility.Click(driver, 10, Copy_YesBtn);
			if (SeleniumUtility.elementDispalyed(alertMessage)) {
				assetRenameAlert = SeleniumUtility.getElementText(alertMessage);
				log.info("Asset renamed successfully alert message" + " "
						+ SeleniumUtility.getElementText(alertMessage));
			} else if (SeleniumUtility.elementDispalyed(renamePopUp)) {
				SeleniumUtility.Click(driver, 10, renameBtn);
				SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.loadingSymbol());
				assetRenameAlert = SeleniumUtility.getElementText(alertMessage);
				log.info("Asset renamed successfully alert message" + " "
						+ SeleniumUtility.getElementText(alertMessage));
				SeleniumUtility.waitTill_invisibility_of_Element(driver, 30, genericUtils.textLoadingSymbol());
			}

		} catch (Exception e) {
			log.error("Failed to rename the asset" + e.getMessage());
			e.printStackTrace();

		}
		return assetRenameAlert;
	}

	public boolean verifyRenamedAssetSearchResult() {
		boolean renameAssetFound = false;

		SeleniumUtility.Clear(driver, 10, searchTextArea);
		SeleniumUtility.sendkeys(driver, 15, searchTextArea, newName);
		searchTextArea.sendKeys(Keys.ENTER);
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
		if ("1".equalsIgnoreCase(Integer.toString(getSearchResultsCount()))) {
			renameAssetFound = true;
			log.info("Renamed asset is searched successfully");
		} else {
			log.error("Renamed asset is not found");
		}
		return renameAssetFound;

	}

}
