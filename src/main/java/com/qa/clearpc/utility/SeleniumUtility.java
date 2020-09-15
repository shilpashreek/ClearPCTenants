package com.qa.clearpc.utility;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.qa.clearpc.BaseClass.BaseClass;

public class SeleniumUtility extends BaseClass {

	// method to wait till the Visibility of the element using dynamic wait
	// and pass the value
	public static void sendkeys(WebDriver driver, int timeout, WebElement element, String value) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
		element.sendKeys(value);
	}

	public static void Click(WebDriver driver, int timeout, WebElement element) {
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
			// JavascriptUtils.flash(element, driver);
			JavascriptUtils.drawBorder(element, driver);
			Thread.sleep(2000);
			element.click();
		} catch (InterruptedException e) {
			log.error("Failed to click the element" + element);
			Assert.fail("Failed to click the element", e);
			e.printStackTrace();
		}
	}

	public static void Clear(WebDriver driver, int timeout, WebElement element) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
		element.clear();
	}

	
	public static void waitTillElementIsVisibile(WebDriver driver, int timeout,WebElement element)
	{
		new WebDriverWait(driver,timeout)
		.until(ExpectedConditions.visibilityOf(element));
	}
	// method to switch frame
	public void switchFrame(String nameORId) {
		driver.switchTo().frame(nameORId);
	}

	// method to take screenshot
	public String getScreenshot(String testMethodName) throws Exception {
		String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String Destination = Constants.SCREENSHOT_FOLDER_PATH + testMethodName + date + " " + ".png";
		File dest = new File(Destination);
		FileUtils.copyFile(src, dest);
		return Destination;

	}

	public String returnCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public static void moveToElement(WebElement element)
	{
		//WebElement element=null;
		Actions action = new Actions(driver);
		try {
			//element = driver.findElement(locator);
			JavascriptUtils.scrollIntoView(element, driver);
		    action.moveToElement(element).build().perform();
		    log.info("successfully mouse hovered to the element " +element);
		}catch(StaleElementReferenceException e){
			try {
			action.moveToElement(element).build().perform();
			}
		catch(Exception e1){
			log.error(element +"cannot be mouse hovered due to the exception " +e1.getMessage());
			Assert.fail(element +"cannot be mouse hovered due to the exception " +e1.getMessage());
		}
	}
	}

	/**
	 * checks whether element is displayed or not
	 * @param= element should be passed   
	 */
	public static boolean elementDispalyed(WebElement element) {
		try {
			boolean displayStatus = element.isDisplayed();
			if (displayStatus) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static String getAttribute(WebElement element, String attribute) {
		String attValue=null;
		try {
		if(elementDispalyed(element))
		{
			moveToElement(element);
		}
		log.info("Getting the attribute :: \" "+attribute + "\" from the element ::" +element+ "\"");
		attValue = element.getAttribute(attribute);
	    log.info("The" +attribute +"value of the element" +element +"is" +attValue);
	}catch(Exception e) {
		log.error("Cannot get the attribute" +attribute +"value of the element " +element 
				+ "due to exception" +e.getMessage());
	}
	return attValue;
		
	}
	
	public static void waitTill_invisibility_of_Element(WebDriver driver,int timeout,WebElement element)
	{
		new WebDriverWait(driver,timeout).
		until(ExpectedConditions.invisibilityOf(element));
	}
	
	public static String getElementText(WebElement element)
	{
		String text=null;
		
		try {
			if(elementDispalyed(element))
			{
				text=element.getText();
			}
			log.info("getting text from the element --"+element +" " +"and text is" +" " +text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Failed to get text from the element --"+element +" " +e.getMessage());
		}
		return text;
	}
	
	
	
}
