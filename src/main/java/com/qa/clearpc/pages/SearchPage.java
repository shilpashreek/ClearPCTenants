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
import com.qa.clearpc.utility.SeleniumUtility;

public class SearchPage extends BaseClass{
	
	int searchResult=0;
	GenericUtils genericUtils;
	
	@FindBy(id="AdavncedSearchButton") WebElement Library_searchBtn;
	@FindBy(id="txtCurrentSearchText") WebElement searchTextArea;
	@FindBy(css=".walkme-tooltip-content") WebElement searchTips;
	@FindBy(id="autoSuggestULSearchLibrary") WebElement search_filters_suggestion;
	@FindBy(css=".filterType_Folders") WebElement Folder_Checkbox;
	@FindBy(id="spanSearchCount") WebElement searchCount;
	@FindBy(className="schNoResults") WebElement invalidSearchResult;
	
	public SearchPage()
	{
		PageFactory.initElements(driver, this);
	}
	
	
	public String getSearchTipsText()
	{
		return SeleniumUtility.getElementText(searchTips);
	}
	
	
	public void clickOnSearchBtn()
	{
		SeleniumUtility.Click(driver, 20, Library_searchBtn);
	}
	
	public void enterSearchText_CaptureFilterSuggestions(String searchAsset)
	{
		int FilterSize=0;
		List<WebElement> Filter_suggestions=null;
		
		SeleniumUtility.Clear(driver, 10, searchTextArea);
		SeleniumUtility.sendkeys(driver, 15, searchTextArea, searchAsset);
		SeleniumUtility.waitTillElementIsVisibile(driver, 20, search_filters_suggestion);
		if(SeleniumUtility.elementDispalyed(search_filters_suggestion))
		{
        Filter_suggestions = driver.findElements(By.xpath(ConfigUtils.getConfigData("FilterSuggestions")));
		FilterSize=Filter_suggestions.size();
		log.info("Total Library search filter options are--"+FilterSize);
		log.info("Library search filter options are-->");
		for(WebElement FilterNames:Filter_suggestions)
		{
			log.info(FilterNames.getText());
		}
		}else {
			log.error("Filter autosuggestion is not displayed");
		}
	
		for(int i=0;i<FilterSize;i++)
		{
			if(Filter_suggestions.get(i).getText().equals("Folders"))
			{
				SeleniumUtility.Click(driver, 15, Folder_Checkbox);
				break;
			}
		}
		
		searchTextArea.sendKeys(Keys.ENTER);
	}
	
	//gives total search results count
	public int getSearchResultsCount()
	{
		//int searchResult=0;
		try {
		String search_Result=SeleniumUtility.getElementText(searchCount);
		String[] searchResultCount = search_Result.split(" ");
		System.out.println(searchResultCount[0] +"/n " +searchResultCount[1]  +"/n  "
				+searchResultCount[2] +"/n" +searchResultCount[3] +"/n" +searchResultCount[4]
						+"/n" +searchResultCount[5] +"/n" +searchResultCount[6] 
								+"/n" +searchResultCount[7]);
		searchResult=Integer.parseInt(searchResultCount[5].replace(",", ""));
		log.info("count of search results is: " +searchResult);
		}catch(Exception e)
		{
			log.error("failed to get search Count");
		}
		return searchResult;
	}
	
	public boolean verifySearchResults(String contentType,String searchText)
	{
		boolean searchStatus=false;
		try {
		if(searchResult>0)
		{
			List<WebElement> searchResults = driver.findElements(By.xpath(ConfigUtils.getConfigData("SearchResultFoldertitles")));
			//String attributeValue=SeleniumUtility.getAttribute(driver.findElement(By.xpath(ConfigUtils.getConfigData("SearchResultFoldertitles"))), "assetclass");
			
			for(int i=0; i<searchResult; i++)
			{
				String attrvalue = searchResults.get(i).getAttribute("assetclass");
				String title = searchResults.get(i).getAttribute("title");
				if(attrvalue.equalsIgnoreCase(contentType) && title.contains(searchText))
				{
					searchStatus=true;
					log.info("Title of the"+" "+contentType +" " +" "+title);
					
				}
			
			}
			log.info("Content has been searched successfully within the organisation");
		}
		}catch(Exception e){
			log.error("Failed to search the content within the organisation");
		}
		return searchStatus;
	}
	
	public String enterInvalidData(String searchData)
	{
		String search_res=null;
		try {
		SeleniumUtility.Clear(driver, 10, searchTextArea);
		SeleniumUtility.sendkeys(driver, 15, searchTextArea, searchData);
		Thread.sleep(2000);
		if(!SeleniumUtility.elementDispalyed(search_filters_suggestion))
		{
			genericUtils=new GenericUtils();
			log.info("Augosuggetion is not displayed for invalid search data");
			searchTextArea.sendKeys(Keys.ENTER);
			SeleniumUtility.waitTill_invisibility_of_Element(driver, 10, genericUtils.textLoadingSymbol() );
			search_res=SeleniumUtility.getElementText(invalidSearchResult);
		
		}
		}catch(Exception e){
			log.error("Autosuggestions is displaying for invalid search data" +e.getMessage());
			log.error("Could not find the invalid search result" +e.getMessage());
		}
		return search_res;
		
	}
	
	
	public int captureAutosuggestion(String searchAsset,String locator)
	{
		ArrayList<String> autosuggestionsList = new ArrayList<String>();
		int suggestionsCount=0;
		try {
			SeleniumUtility.Clear(driver, 10, searchTextArea);
			SeleniumUtility.sendkeys(driver, 15, searchTextArea, searchAsset);
			SeleniumUtility.waitTillElementIsVisibile(driver, 20, search_filters_suggestion);
			List<WebElement> autoSuggestions_list = driver.findElements(By.xpath(locator));
			suggestionsCount=autoSuggestions_list.size();
			log.info("Autosuggested list is as follows:");
			for(int i=0;i<autoSuggestions_list.size();i++)
			{
				autosuggestionsList.add(autoSuggestions_list.get(i).getText());
				Collections.sort(autosuggestionsList);
			}
			System.out.println(autosuggestionsList);
		}catch(Exception e) {
			log.error("could not print the autosuggestions " +e.getMessage());
		}
		return suggestionsCount;
	}
	
	
	
	

}
