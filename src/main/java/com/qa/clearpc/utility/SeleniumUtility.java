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

	private final int counter = 60;

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

	public static void waitTillElementIsVisibile(WebDriver driver, int timeout, WebElement element) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
	}

	// method to switch frame
	public void switchFrame(String nameORId) {
		driver.switchTo().frame(nameORId);
	}

	// method to take screenshot
	public String getScreenshot(String testMethodName) throws Exception {
		String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// String Destination = Constants.SCREENSHOT_FOLDER_PATH + testMethodName + date
		// + " " + ".png";
		String Destination = ConfigUtils.getConfigData("ScreenshotFolderPath") + testMethodName + date + " " + ".png";
		File dest = new File(Destination);
		FileUtils.copyFile(src, dest);
		return Destination;

	}

	public String returnCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public static void moveToElement(WebElement element) {
		// WebElement element=null;
		Actions action = new Actions(driver);
		try {
			// element = driver.findElement(locator);
			JavascriptUtils.scrollIntoView(element, driver);
			action.moveToElement(element).build().perform();
			log.info("successfully mouse hovered to the element " + element);
		} catch (StaleElementReferenceException e) {
			try {
				action.moveToElement(element).build().perform();
			} catch (Exception e1) {
				log.error(element + "cannot be mouse hovered due to the exception " + e1.getMessage());
				Assert.fail(element + "cannot be mouse hovered due to the exception " + e1.getMessage());
			}
		}
	}

	/**
	 * checks whether element is displayed or not @param= element should be passed
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
		String attValue = null;
		try {
			if (elementDispalyed(element)) {
				moveToElement(element);
			}
			log.info("Getting the attribute :: \" " + attribute + "\" from the element ::" + element + "\"");
			attValue = element.getAttribute(attribute);
			log.info("The" + attribute + "value of the element" + element + "is" + attValue);
		} catch (Exception e) {
			log.error("Cannot get the attribute" + attribute + "value of the element " + element + "due to exception"
					+ e.getMessage());
		}
		return attValue;

	}

	public static void waitTill_invisibility_of_Element(WebDriver driver, int timeout, WebElement element) {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.invisibilityOf(element));
	}

	public static String getElementText(WebElement element) {
		String text = null;

		try {
			if (elementDispalyed(element)) {
				text = element.getText();
			}
			log.info("getting text from the element --" + element + " " + "and text is" + " " + text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Failed to get text from the element --" + element + " " + e.getMessage());
		}
		return text;
	}

	/**
	 * runs the garbage collector and removes all objects which will referring null
	 */
	final private void gc() {
		Runtime.getRuntime().gc();
	}

	public static void doubleClick(WebElement element) {
		Actions act = new Actions(driver);
		try {
			SeleniumUtility seleniumUtility = new SeleniumUtility();
			seleniumUtility.waitUntilEleIsVisible(element);
			JavascriptUtils.scrollIntoView(element, driver);
			JavascriptUtils.drawBorder(element, driver);
			act.moveToElement(element).doubleClick().build().perform();
			log.info("successfully double clicked on element " + element);
		} catch (Exception e) {
			log.error("cannot double click on element :: " + element + " due to " + e.getMessage());

		} finally {
			act = null;
			// gc();
		}
	}

	public void waitUntilEleIsVisible(WebElement element) {
		try {
			int counter = 0;
			while (!element.isDisplayed()) {
				if (counter > this.counter) {
					log.error("Failed to Display Element  " + element + " in " + counter + " secs");
					break;
				} else {
					counter++;
					Thread.sleep(2000);
					log.info("Loading...!!!" + counter + " secs over element :: " + element);
				}
			}
		} catch (Exception e) {

		}
	}

}
