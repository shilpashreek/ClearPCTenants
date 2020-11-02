package com.qa.clearpc.utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.clearpc.BaseClass.BaseClass;

public class GenericUtils extends BaseClass {
	GenericUtils genericUtils;

	@FindBy(id = "navigiationOpenClosePanel")
	WebElement navigationPanel;
	@FindBy(xpath = "//*[@role='presentation' and @title='Library']")
	WebElement libraryLeftPanel;
	@FindBy(css = ".aciTreeText")
	WebElement LibraryLeft_PanelElements;
	@FindBy(css = ".waitingImage")
	WebElement loading_icon;
	@FindBy(id = "infoPanelDrawer")
	WebElement panelDrawer;
	@FindBy(id = "div_OBJECTCLASS_FACET")
	WebElement Categories;
	@FindBy(id = "FacetDate")
	WebElement uploadDateFilter;
	@FindBy(xpath = "//div[@title='ALL']")
	WebElement All_searchTag;
	@FindBy(id = "tagElement2")
	WebElement secondarySearchTag;
	@FindBy(xpath = "(//div[@class='tagClose'])[2]")
	WebElement search_closeTag;
	@FindBy(id = "spanSearchCount")
	WebElement searchCountResults;

	public GenericUtils() {
		PageFactory.initElements(driver, this);
	}

	public boolean checkNavigationPanelStatus() {

		if (SeleniumUtility.getAttribute(navigationPanel, "title")
				.equalsIgnoreCase(ConfigUtils.getConfigData("navigationPanelDisplayed"))) {
			log.info("Navigation panel is displayed");
			return SeleniumUtility.elementDispalyed(libraryLeftPanel);
		} else if (SeleniumUtility.getAttribute(navigationPanel, "title")
				.equalsIgnoreCase(ConfigUtils.getConfigData("navigationPanelHidden"))) {
			SeleniumUtility.Click(driver, 15, navigationPanel);
			log.info("Clicked on show navigation panel icon");
			SeleniumUtility.waitTillElementIsVisibile(driver, 15, libraryLeftPanel);
			log.info("library panel is displayed");
			return SeleniumUtility.elementDispalyed(libraryLeftPanel);
		}
		return SeleniumUtility.elementDispalyed(libraryLeftPanel);
	}

	public String selectOrganization(String organizationName) {
		String orgName = null;

		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, loading_icon);

		String library = SeleniumUtility.getAttribute(libraryLeftPanel, "class");
		boolean leftpanelDisplayed = checkNavigationPanelStatus();
		if (leftpanelDisplayed) {
			if (library.contains("aciTreeOpen")) {

				List<WebElement> foldersList = driver
						.findElements(By.cssSelector(ConfigUtils.getConfigData("LibraryLeft_PanelElements")));
				for (WebElement ListOfFolders : foldersList) {
					System.out.println(ListOfFolders.getText());
					if (organizationName.toLowerCase().equalsIgnoreCase(ListOfFolders.getText())) {
						JavascriptUtils.scrollIntoView(ListOfFolders, driver);
						SeleniumUtility.Click(driver, 20, ListOfFolders);
						log.info("Clicked on the folder successfully  " + organizationName);
						orgName = organizationName;
						break;
					}

				}

			}
		}
		return orgName;
	}

	// Page loading utils
	public WebElement loadingSymbol() {
		WebElement loadingImage = driver.findElement(By.id("globalWaiting"));
		return loadingImage;
	}

	public WebElement textLoadingSymbol() {
		WebElement loadingText = driver.findElement(By.id("searchWaiting"));
		return loadingText;
	}

	public WebElement WaitingImage() {
		return driver.findElement(By.className("waitingImage"));
	}

	public String getBreadCrumbName() {
		log.info("BreadCrum path is displaying as " + " "
				+ driver.findElement(By.cssSelector(".Ellipsis.leftTrimCont")).getText());
		return driver.findElement(By.cssSelector(".Ellipsis.leftTrimCont")).getText();

	}

	public String returnBredaCrumbCurrentFolder() {
		log.info("Currently user is in the following folder/asset page"
				+ SeleniumUtility.getElementText(driver.findElement(By.className("breadcrumbsCurrentFolder"))));
		return SeleniumUtility.getElementText(driver.findElement(By.className("breadcrumbsCurrentFolder")));

	}

	public void CheckFilterPanelDrawerStatus() {
		if (SeleniumUtility.getAttribute(panelDrawer, "title").equalsIgnoreCase("Hide Preview Panel")) {
			log.info("Filters panel is displayed");
		} else if (SeleniumUtility.getAttribute(panelDrawer, "title").equalsIgnoreCase("Show Preview Panel")) {
			SeleniumUtility.Click(driver, 10, panelDrawer);
			log.info("Clicked on panel drawer successfully and filter options are visible");
		}
	}

	public void FILTERcategoriesStatus() {
		if (SeleniumUtility.getAttribute(Categories, "title").equalsIgnoreCase("Collapse Categories Filter")) {
			log.info("Filter options are displaying");
		} else if (SeleniumUtility.getAttribute(Categories, "title").equalsIgnoreCase("Expand Categories Filter")) {
			SeleniumUtility.Click(driver, 15, Categories);
			log.info("Clicked on Categories button successfully");
		}
	}

	public ArrayList<String> filterOptions() {
		ArrayList<String> filters = new ArrayList<String>();
		try {
			List<WebElement> filterOptions = driver
					.findElements(By.xpath("//ul[@class='facetDivClass facetDivClassUngrouped']"));
			log.info("Filter options are as follows");
			for (WebElement FilterNames : filterOptions) {
				filters.add(FilterNames.getText());
				log.info(FilterNames.getText());
			}
		} catch (Exception e) {
			log.error("Failed to get filter options" + e.getMessage());
			e.printStackTrace();
		}
		return filters;

	}

	public ArrayList<String> targetFilterOptions() {
		ArrayList<String> searchLibraryFilterOptions = new ArrayList<String>();
		searchLibraryFilterOptions
				.addAll(Arrays.asList("Categories", "Folder Type", "Archival", "Video Quality", "Videos", "Documents",
						"Images", "Audios", "Playlists", "Folders", "1.893", "16:10", "16:9", "2.40:1", "4:3", "NA"));
		/*
		 * searchLibraryFilterOptions.add("Categories");
		 * searchLibraryFilterOptions.add("Folder Type");
		 * searchLibraryFilterOptions.add("Archival");
		 * searchLibraryFilterOptions.add("Video Quality");
		 */
		return searchLibraryFilterOptions;
	}

	public boolean uploadDateFilter_displayed() {
		return SeleniumUtility.elementDispalyed(uploadDateFilter);
	}

	public boolean unCheckCurrentAndApplyNewFilter() throws Exception {
		String filter_count = null;
		boolean searchResult = false;

		genericUtils = new GenericUtils();
		List<WebElement> categoryFilters = driver
				.findElements(By.xpath(ConfigUtils.getConfigData("Category_searchFilter_path")));
		int categoryFilters_size = categoryFilters.size();
		if (SeleniumUtility.elementDispalyed(All_searchTag)) {
			for (int i = 1; i <= categoryFilters_size; i++) {

				if (SeleniumUtility.elementDispalyed(secondarySearchTag)) {
					SeleniumUtility.Click(driver, 15, search_closeTag);
					Thread.sleep(5000);
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
					log.info("Closed the secondary search category");
					String Filters_xpath = ConfigUtils.getConfigData("Category_searchFilter_path");
					filter_count = ConfigUtils.getConfigData("Filter_Count");
					String j = Integer.toString(i);
					String filter_xpath = Filters_xpath.replace("*", j);
					String filterCountXpath = filter_count.replace("*", j);
					String count = SeleniumUtility.getElementText(driver.findElement(By.xpath(filterCountXpath)));
					String count_value = splitString(count);
					log.info("Splitted count value" + count_value);
					SeleniumUtility.Click(driver, 15, driver.findElement(By.xpath(filter_xpath)));
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
					Thread.sleep(3000);
					String search_text = splitSearchSpanText(SeleniumUtility.getElementText(searchCountResults));
					log.info("Extracted search result" + search_text);
					if (count_value.equals(search_text)) {
						searchResult = true;
					}
					Thread.sleep(5000);

				} else {
					Thread.sleep(3000);
					String Filters_xpath = ConfigUtils.getConfigData("Category_searchFilter_path");
					filter_count = ConfigUtils.getConfigData("Filter_Count");
					String j = Integer.toString(i);
					String filter_xpath = Filters_xpath.replace("*", j);
					String filterCountXpath = filter_count.replace("*", j);
					String count = SeleniumUtility.getElementText(driver.findElement(By.xpath(filterCountXpath)));
					String count_value = splitString(count);
					log.info("Splitted count value" + count_value);
					SeleniumUtility.Click(driver, 15, driver.findElement(By.xpath(filter_xpath)));
					SeleniumUtility.waitTill_invisibility_of_Element(driver, 15, genericUtils.textLoadingSymbol());
					Thread.sleep(3000);
					String search_text = splitSearchSpanText(SeleniumUtility.getElementText(searchCountResults));
					log.info("Extracted search result" + search_text);
					if (count_value.equals(search_text)) {
						searchResult = true;
					}
					Thread.sleep(5000);
				}

			}
			return searchResult;
		}

		return searchResult;
	}

	/*
	 * public String filterAssetsCount() { WebElement filterCount =
	 * driver.findElement(By.xpath(ConfigUtils.getConfigData("Filter_Count")));
	 * String count=SeleniumUtility.getElementText(filterCount);
	 * log.info("Assets count are as follows" +count); return count;
	 * 
	 * }
	 */
	// method to split search results count value
	public String splitString(String value) {
		String[] k = value.split("\\(");
		System.out.println("first 1-->" + k[0] + " " + "second-->" + k[1]);
		String[] j = k[1].split("\\)");
		log.info(j[0]);
		return j[0];
	}

	// method to split search span text value
	public String splitSearchSpanText(String text) {
		String[] v = text.split(" ");
		System.out.println(v[5]);
		return v[5];
	}

	// return's date with cuurent seconds
	public String getTodaysDate(String Dateformat) {
		SimpleDateFormat format = new SimpleDateFormat(Dateformat);
		Date date = new Date();
		String d = format.format(date);
		return d;

	}

	final public String openNewTab() {
		String childWindowId = null;

		String parentWinId = driver.getWindowHandle();
		log.info("Parent window id is" + parentWinId);
		JavascriptUtils.launchNewTab();
		Set<String> allWindows = driver.getWindowHandles();
		for (String windows : allWindows) {
			log.info("child window id" + windows);
			if (!parentWinId.equalsIgnoreCase(windows)) {
				driver.switchTo().window(windows);
				childWindowId = driver.getWindowHandle();
				log.info("child window id" + childWindowId);

			}

		}
		return childWindowId;
	}

	final public void closeOtherTabsExpectChildWindow(String childWinID) {
		try {
			for (String allWindows : driver.getWindowHandles()) {
				if (!childWinID.equalsIgnoreCase(allWindows)) {
					driver.switchTo().window(allWindows);
					driver.close();

				}

			}
			driver.switchTo().window(childWinID);
		} catch (Exception e) {
			log.error("Failed to close extra tabs in the browser" + e.getMessage());
			e.printStackTrace();

		}

	}

}
