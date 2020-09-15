package com.qa.clearpc.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.qa.clearpc.BaseClass.BaseClass;

public class ChromeFlash extends BaseClass {
	
private final ChromeDriver driver;
    
    public ChromeFlash(final ChromeDriver driver) {
        this.driver = driver;
    }
    
    public ChromeFlash addSite(final String site) {
        this.driver.get("chrome://settings/content/siteDetails?site=" + site);
        WebElement root1 = driver.findElement(By.tagName("settings-ui"));
		WebElement shadowRoot1 = expandRootElement(root1);
		
		WebElement root2 = shadowRoot1.findElement(By.cssSelector("settings-main"));
		WebElement shadowroot2 = expandRootElement(root2);
		
		WebElement root3 = shadowroot2.findElement(By.cssSelector("settings-basic-page"));
		WebElement shadowroot4 = expandRootElement(root3);
		
		WebElement root5 = shadowroot4.findElement(By.tagName("settings-privacy-page"));
		WebElement shadowroot6 = expandRootElement(root5);
		
		WebElement root8 = shadowroot6.findElement(By.tagName("site-details"));
		WebElement shadowroot9 = expandRootElement(root8);
		
		WebElement root9 = shadowroot9.findElement(By.id("plugins"));
		WebElement shadowroot10 = expandRootElement(root9);
		
		WebElement FlashDropdown = shadowroot10.findElement(By.id("permission"));
		Select s = new Select(FlashDropdown);
		s.selectByValue("allow");
        return this;
    }
    
    private WebElement expandRootElement(final WebElement element) {
    	WebElement ele=(WebElement)((JavascriptExecutor)driver).executeScript("return arguments[0].shadowRoot", element);
    	return ele;
    }

}
