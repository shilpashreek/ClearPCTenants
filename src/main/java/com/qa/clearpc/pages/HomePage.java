package com.qa.clearpc.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.SeleniumUtility;

public class HomePage extends BaseClass {
	
	@FindBy(id="navigiationOpenClosePanel") WebElement navigationPanel;
	@FindBy(xpath="//*[@role='presentation' and @title='Library']") WebElement libraryLeftPanel;
	@FindBy(css=".aciTreeText") WebElement LibraryLeft_PanelElements;
	@FindBy(css=".name.FL") WebElement usernameRole;
	
	public HomePage()
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
	
	public void selectOrganization(String organizationName)
	{
		String library = SeleniumUtility.getAttribute(libraryLeftPanel, "class");
		boolean leftpanelDisplayed=checkNavigationPanelStatus();
		if(leftpanelDisplayed)
		{
			if(library.contains("aciTreeOpen"))
			{
				
				List<WebElement> foldersList = driver.findElements(By.cssSelector(ConfigUtils.getConfigData("LibraryLeft_PanelElements")));
				for(WebElement ListOfFolders:foldersList)
				{
					System.out.println();ListOfFolders.getText();
				}
				
				//List<WebElement> OrganisationList = driver.findElements(By.xpath(ConfigUtils.getConfigData("orgList")));
				
			}
		}
		
	}
	
	public String logoutFromPortal()
	{
		SeleniumUtility.Click(driver, 20, usernameRole);
		WebElement logOut=driver.findElement(By.xpath(ConfigUtils.getConfigData("logout_btn")));
		SeleniumUtility.Click(driver, 15, logOut);
		String logoutUrl=driver.getCurrentUrl();
		return logoutUrl;
	}
	

}
