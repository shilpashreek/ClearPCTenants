package com.qa.clearpc.utility;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.clearpc.BaseClass.BaseClass;

public class GenericUtils extends BaseClass {
	
	@FindBy(id="navigiationOpenClosePanel") WebElement navigationPanel;
	@FindBy(xpath="//*[@role='presentation' and @title='Library']") WebElement libraryLeftPanel;
	@FindBy(css=".aciTreeText") WebElement LibraryLeft_PanelElements;
	@FindBy(css=".waitingImage") WebElement loading_icon;
	
	public GenericUtils()
	{
		PageFactory.initElements(driver, this);
	}
	
	public boolean checkNavigationPanelStatus()
	{
		
		if(SeleniumUtility.getAttribute(navigationPanel, "title")
				.equalsIgnoreCase(ConfigUtils.getConfigData("navigationPanelDisplayed")))
		{
			log.info("Navigation panel is displayed");
			return SeleniumUtility.elementDispalyed(libraryLeftPanel);
		}else if(SeleniumUtility.getAttribute(navigationPanel, "title")
				.equalsIgnoreCase(ConfigUtils.getConfigData("navigationPanelHidden"))) {
			SeleniumUtility.Click(driver, 15, navigationPanel);
			log.info("Clicked on show navigation panel icon");
			SeleniumUtility.waitTillElementIsVisibile(driver, 15, libraryLeftPanel);
			log.info("library panel is displayed");
			return SeleniumUtility.elementDispalyed(libraryLeftPanel);
			}
		return SeleniumUtility.elementDispalyed(libraryLeftPanel);
	}
	
	public String selectOrganization(String organizationName)
	{
		String orgName=null;
		
		SeleniumUtility.waitTill_invisibility_of_Element(driver, 20, loading_icon);
		
		String library = SeleniumUtility.getAttribute(libraryLeftPanel, "class");
		boolean leftpanelDisplayed=checkNavigationPanelStatus();
		if(leftpanelDisplayed)
		{
			if(library.contains("aciTreeOpen"))
			{
				
				List<WebElement> foldersList = driver.findElements(By.cssSelector(ConfigUtils.getConfigData("LibraryLeft_PanelElements")));
				for(WebElement ListOfFolders:foldersList)
				{
					System.out.println(ListOfFolders.getText());
					  if(organizationName.toLowerCase().equalsIgnoreCase(ListOfFolders.getText()))
					  { 
						  JavascriptUtils.scrollIntoView(ListOfFolders, driver);
					  SeleniumUtility.Click(driver, 20, ListOfFolders);
					  log.info("Clicked on the folder successfully  " +organizationName);
					  orgName=organizationName; 
					  break; 
					  }
				
				}
				
			}
		}
		return orgName;
	}
	
	//Page loading utils
	public WebElement loadingSymbol()
	{
		WebElement loadingImage=driver.findElement(By.id("globalWaiting"));
		return loadingImage;
	}
	
	public WebElement textLoadingSymbol()
	{
		WebElement loadingText=driver.findElement(By.id("searchWaiting"));
		return loadingText;
	}
	
	public WebElement WaitingImage()
	{
		return driver.findElement(By.className("waitingImage"));
	}
	
	
	
	
	public String getBreadCrumbName()
	{
		return driver.findElement(By.cssSelector(".Ellipsis.leftTrimCont")).getText();

	}
	
	
	
	

}
