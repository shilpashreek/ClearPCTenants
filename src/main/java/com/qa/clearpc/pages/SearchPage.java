package com.qa.clearpc.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	int folderSize=0;
	String folderOpenCloseStatus_xpath=null;
	public String testFolderName="";

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
	@FindBy(className="popupBG") WebElement addFolderPopUp;
	@FindBy(id="folderPath") WebElement pathofFolder;
	@FindBy(xpath="//input[@class=' smallControlViewForm']") WebElement FolderTitle;
	@FindBy(xpath="//span[starts-with(@id,'customCheckBoxSpan') and @title='Inherit Access from Parent']") WebElement permissionAccess_Btn;
	@FindBy(id="cancelFolderBtn") WebElement addPopUp_closeBtn;
	@FindBy(id="saveFolderBtn") WebElement save_Add_another;
	@FindBy(xpath="//div[@title='Save & close']") WebElement save_close;
    @FindBy(id="close1") WebElement closePopUp_cross;
    @FindBy(className="alertMessageUCWrapper") WebElement alertMessage;
    @FindBy(id="AssetContextMenu") WebElement assetContextMenuIcon;
    @FindBy(id="_GroupActionPanel") WebElement assetContextMenuOptions;
    @FindBy(id="popUpHeader1") WebElement popupHeader;
    @FindBy(xpath="//ul[@id='_GroupActionPanel']/li[@id='EditFolder']") WebElement editFolder;
    
    
	public SearchPage() {
		PageFactory.initElements(driver, this);
	}

	public String getSearchTipsText() {
		return SeleniumUtility.getElementText(searchTips);
	}

	public void clickOnSearchBtn() {
		SeleniumUtility.Click(driver, 20, Library_searchBtn);
	}

	public void enterSearchText_CaptureFilterSuggestions(String searchAsset) {
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
			if (Filter_suggestions.get(i).getText().equals("Folders")) {
				SeleniumUtility.Click(driver, 15, Folder_Checkbox);
				break;
			}
		}

		searchTextArea.sendKeys(Keys.ENTER);
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

				}
				log.info("Content has been searched successfully within the organisation");
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
				genericUtils = new GenericUtils();
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
			testFolderTitle = SeleniumUtility
					.getAttribute(driver.findElement(By.xpath(ConfigUtils.getConfigData("FolderTitle_path"))), "title");
			String eleText = SeleniumUtility
					.getElementText(driver.findElement(By.xpath(ConfigUtils.getConfigData("FolderTitle_path"))));
			log.info("Folder title" + testFolderTitle);
			log.info("Folder titleText" + eleText);
			if (testFolderTitle.contains("QATest") || testFolderTitle.contains("qatest")
					|| testFolderTitle.contains("QAtest")) {

				SeleniumUtility.doubleClick(folderThumbnail);

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
		for(int i=1; i<5; i++) {
		String j = Integer.toString(i);
		String folder_Title = ConfigUtils.getConfigData("foldersTitle_xpath");
		String folderTitle_xpath = folder_Title.replace("*", j);
		folderTitle=SeleniumUtility.getElementText(driver.findElement(By.xpath(folderTitle_xpath)));
		
		String folderOpenCloseStatus = ConfigUtils.getConfigData("libraryBrowseTreeFolderStatus");
		folderOpenCloseStatus_xpath = folderOpenCloseStatus.replace("*", j);
			
			if(!(SeleniumUtility.getAttribute(driver.findElement(By.xpath(folderOpenCloseStatus_xpath)), "class").contains("clear-folderOpenF"))
					&& (folderTitle.contains("SUPPORT PROJECT") || folderTitle.contains("supports test project") ||
							folderTitle.contains("Support Test Project") || folderTitle.contains("support test project") ||
							folderTitle.contains("QAtestFolder" )) )
			{
				SeleniumUtility.Click(driver, 10, driver.findElement(By.xpath(folderTitle_xpath)));
				break;
			}
		
		}
		
	}
	
	public boolean addFolderPopUpDisplayed()
	{
		boolean popUpDisplayed=false;
		try {
		if(SeleniumUtility.elementDispalyed(addFolderPopUp)) {
			popUpDisplayed=true;
			log.info("Add folder popup is displayed");
			}
		}catch(Exception e) {
			log.error("Add Folder popup is not displayed");
		}
		return popUpDisplayed;
	}
	
    public void closePopUp()
    {
    	if(SeleniumUtility.elementDispalyed(addFolderPopUp)) {
    		SeleniumUtility.Click(driver, 15, closePopUp_cross);
    	}
    }
    
    
    public boolean pathDisplayed() {
    	
        boolean pathStatus=false;
    	try {
    		if(SeleniumUtility.elementDispalyed(pathofFolder)) {
					pathStatus=true;
    		}
    	}catch(Exception e) {
    		log.error("Failed to find the path field and its status");
    	}
    	return pathStatus;
    }
    
    public boolean folderTitle() {
    	boolean FolderTitleStatus=false;
    	try {
    		if(SeleniumUtility.elementDispalyed(FolderTitle))	
			{
    			FolderTitleStatus=true;
			}
    	}catch(Exception e) {
    		log.error("" +e.getMessage());
    		e.printStackTrace();
    	}
    	return FolderTitleStatus;
    }
    
    public boolean closeAddPopup()
    {
    	boolean closeBtn=false;
         try {
        	 if(SeleniumUtility.elementDispalyed(addPopUp_closeBtn))	{
        		 closeBtn=true;
 			}
    		
    	}catch(Exception e) {
    		log.error("" +e.getMessage());
    		e.printStackTrace();
    	}
         return closeBtn;
    }
    
    public boolean saveBtnStatus()
    {
    	boolean saveBtn=false;
         try {
        	 if(save_Add_another.isDisplayed() && save_close.isDisplayed()) {
 				if(SeleniumUtility.getAttribute(save_Add_another, "class").contains("fadeAndDisable")
 						&& SeleniumUtility.getAttribute(save_close, "class").contains("fadeAndDisable"))
 				{
 					saveBtn=true;
 				}
 			}
    		
    	}catch(Exception e) {
    		log.error("" +e.getMessage());
    		e.printStackTrace();
    	}
         return saveBtn;
    }
    
    public boolean inheritAccess() {
    	boolean InheritingAccess=false;
    	try {
    		
    		if(SeleniumUtility.elementDispalyed(permissionAccess_Btn))	{
    			InheritingAccess=true;
			}	
    		
    	}catch(Exception e) {
    		log.error("" +e.getMessage());
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
		
		String alertMsg=null;
		
		SeleniumUtility.waitTillElementIsVisibile(driver, 20, addFolderPopUp);
		SeleniumUtility.sendkeys(driver, 10, FolderTitle, generateTestFolderName() );
		if(SeleniumUtility.getAttribute(permissionAccess_Btn, "id").contains("customCheckBoxSpan_0"))
		{
			log.info("Inherit from parent CheckBox is Disbaled");
		}else if(SeleniumUtility.getAttribute(permissionAccess_Btn, "id").contains("customCheckBoxSpan_1")){
			SeleniumUtility.Click(driver, 10, permissionAccess_Btn);
			log.info("Clicked on inherit properties");
		}
		
		SeleniumUtility.Click(driver, 10, save_close);
		genericUtils=new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
		
		  if(SeleniumUtility.elementDispalyed(alertMessage)) { 
			  alertMsg =SeleniumUtility.getElementText(alertMessage); 
			  log.info("Alert message is" +alertMsg);
		 
		}
		  return alertMsg;
			
	}
	
	public String generateTestFolderName() {
		GenericUtils generUtils = new GenericUtils();
		String date = generUtils.getTodaysDate();
		String folderName="QATestFolder";
        testFolderName=folderName.concat(date);
        return testFolderName;
      
	}
	
	public void searchNewFolder() {
		clickOnSearchBtn();
		SeleniumUtility.Clear(driver, 10, searchTextArea);
		SeleniumUtility.sendkeys(driver, 15, searchTextArea, testFolderName );
		searchTextArea.sendKeys(Keys.ENTER);
		
	}
	
	public String saveAndAddAnother() {
		
		String alertMsg=null;
		
		SeleniumUtility.waitTillElementIsVisibile(driver, 20, addFolderPopUp);
		SeleniumUtility.Clear(driver, 10, FolderTitle);
		SeleniumUtility.sendkeys(driver, 10, FolderTitle, generateTestFolderName() );
		if(permissionAccess_Btn.isEnabled())
		{
			SeleniumUtility.Click(driver, 10, permissionAccess_Btn);
			log.info("clicked on inherit proprties from parent checkbox");
		}
		
		SeleniumUtility.Click(driver, 10, save_Add_another);
		genericUtils=new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
		if(SeleniumUtility.elementDispalyed(alertMessage)) { 
			  alertMsg =SeleniumUtility.getElementText(alertMessage); 
			  log.info("Alert message is" +alertMsg);
		 
		}
		  return alertMsg;
		
	}
	
	
   public boolean editFolderPopUpDisplayed(){
	   boolean editFolderPopupDisplayed=false;
	   
	   try {
	   
	   SeleniumUtility.waitTillElementIsVisibile(driver, 10, assetContextMenuIcon);
	   SeleniumUtility.Click(driver, 10, assetContextMenuIcon);
	   //SeleniumUtility.waitTillElementIsVisibile(driver, 10, assetContextMenuOptions);
	   //SeleniumUtility.Click(driver, 10, assetContextMenuOptions);
	   SeleniumUtility.Click(driver, 10, editFolder); 
	   SeleniumUtility.waitTillElementIsVisibile(driver, 10, addFolderPopUp);
		   if(SeleniumUtility.getElementText(popupHeader).contains("Edit in ")) {
			   editFolderPopupDisplayed=true;
			log.info("Edit folder popup is displayed");   
		   }
	
	   }catch(Exception e) {
		   log.error("Failed to display edit folder popup" +e.getMessage());
		   e.printStackTrace();
	   }
	   
	   return editFolderPopupDisplayed;
	
   }
  
	public String editFolderName(String currentFolderName) {
		String newFolderName=currentFolderName.concat("_Edited");
		String alertMsg=null;
		try {
		SeleniumUtility.Clear(driver, 10, FolderTitle);
		SeleniumUtility.sendkeys(driver, 10, FolderTitle, newFolderName );
		SeleniumUtility.Click(driver, 10, save_close);
		genericUtils=new GenericUtils();
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, genericUtils.loadingSymbol());
		
		  if(SeleniumUtility.elementDispalyed(alertMessage)) { 
			  alertMsg =SeleniumUtility.getElementText(alertMessage); 
			  log.info("Alert message after editing folder" +alertMsg);
		 
		}
		  
		}catch(Exception e) {
			log.error("Failed to edit the folder" +e.getMessage());
			e.printStackTrace();
		}
		return alertMsg;
	}
	
}
