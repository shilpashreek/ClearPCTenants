package com.qa.clearpc.Listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.SeleniumUtility;

public class WebEventListener extends BaseClass implements WebDriverEventListener
{

	public void beforeAlertAccept(WebDriver driver) 
	{
		log.info("waiting for the alert message");
	}

	public void afterAlertAccept(WebDriver driver) 
	{
		log.info("alert message is captured");
		
	}

	public void afterAlertDismiss(WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void beforeAlertDismiss(WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateTo(String url, WebDriver driver) 
	{
	   //System.out.println(" Before Navigating to :" + url + " ' ");
		
	}

	public void afterNavigateTo(String url, WebDriver driver)
	{
		//System.out.println("Navigated to : " + url + " ' ");
		
	}

	public void beforeNavigateBack(WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateBack(WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateForward(WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateRefresh(WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateRefresh(WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		//System.out.println("trying to click on:" +element.toString());
		
	}

	public void afterClickOn(WebElement element, WebDriver driver) 
	{
		//System.out.println("Clicked on:" +element.toString());
		
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) 
	{
		//System.out.println("Value of the :" +element.toString()   + "before any changes made");
		
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		//System.out.println("Element value changed to :"  +element.toString());
		
	}

	public void beforeScript(String script, WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void afterScript(String script, WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) 
	{
		//System.out.println("Current window name" +windowName.toString());
		
	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) 
	{
		// TODO Auto-generated method stub
		
	}

	public void onException(Throwable error, WebDriver driver)
	{
		try {
			SeleniumUtility seleniumUtils = new SeleniumUtility();
			seleniumUtils.getScreenshot(this.getClass().getName());
		} catch (Exception e) {
			log.error("unable to capture screenshot on exception", e);
			e.printStackTrace();
		}
		
		
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) 
	{
		// TODO Auto-generated method stub
		
	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) 
	{
		// TODO Auto-generated method stub
		
	}

	public void beforeGetText(WebElement element, WebDriver driver) 
	{
		//System.out.println("Trying to get the text:" +element.toString());
		
	}

	public void afterGetText(WebElement element, WebDriver driver, String text) 
	{
		//System.out.println("Extracted text successfully from" +element.toString() +" " +text.toString() );
		
	}

}
