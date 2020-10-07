package com.qa.clearpc.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.MailUtils;
import com.qa.clearpc.utility.SeleniumUtility;
import com.qa.clearpc.utility.Xls_Reader;

public class LoginPage extends BaseClass
{
	SeleniumUtility seleniumUtility;
	MailUtils mailUtils;
	Xls_Reader reader;
	/* LoginPage loginPage= new LoginPage(); */
	 

@FindBy(className="loginText") WebElement UserName;
@FindBy(id="loginContinue") WebElement continueBtn;
@FindBy(name="x-password") WebElement Password;
@FindBy(id="loginSubmit") WebElement loginBtn;
@FindBy(id="editUserName") WebElement edit_userName;
@FindBy(id="ctl00_pageContent_passwordEmail") WebElement OtpTextArea;
@FindBy(css = ".promptBtn.submitbtn.MFAButtonSubmit") WebElement submitBtn;
@FindBy(id="Resend") WebElement resendBtn;
@FindBy(css=".marT5") WebElement passwordExp_InfoMessage;
@FindBy(css= ".FL.newBackBtn") WebElement skipButton;
@FindBy(className="ResetPasswordSubmit") WebElement changePassword;
@FindBy(css=".name.FL") WebElement ActiveUserName;


  
     public LoginPage()
     {
    	 PageFactory.initElements(driver, this);
     }
	
     public void loginToPortal(String username, String password)
     {
    	 
    	 if(SeleniumUtility.elementDispalyed(continueBtn))
    	 {
    		 SeleniumUtility.sendkeys(driver, 20, UserName, username);
        	 SeleniumUtility.Click(driver, 10, continueBtn);
        	 SeleniumUtility.sendkeys(driver, 20, Password, password );
        	 SeleniumUtility.Click(driver, 10, loginBtn);
    	 }else {
    		 SeleniumUtility.sendkeys(driver, 20, Password, password );
        	 SeleniumUtility.Click(driver, 10, loginBtn);
    	 }
    	 
    	 //return driver.getCurrentUrl();
     }
	
     public String login(String Username, String PassworD, String host, String username, 
    		 String password, String Expected_Sender, String Expected_subject, String Filepath) throws Exception
     {
    	 String PageURL=null;
    	 
    	 if(SeleniumUtility.elementDispalyed(continueBtn))
    	 {
    		 SeleniumUtility.sendkeys(driver, 20, UserName, Username);
        	 SeleniumUtility.Click(driver, 10, continueBtn);
        	 SeleniumUtility.sendkeys(driver, 20, Password, PassworD );
        	 SeleniumUtility.Click(driver, 10, loginBtn);

        	 if(driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("Mfa_page_url")))
        	 {
        		 try {
        		 Thread.sleep(20000);
        		 mailUtils = new MailUtils();
        		 String otp = mailUtils.readTheLatestMail(host, username, password, Expected_Sender, Expected_subject, Filepath);
        	     enterOtp(otp);
        	     PageURL=driver.getCurrentUrl();
        		 return PageURL;
        		 }catch(Exception e){
        			 log.error("Failed to enter OTP" +e.getMessage());
        			 e.printStackTrace();
        		 }
        	 
        	 }else if(driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("Home_page_url")))
        	 {
        		 PageURL=driver.getCurrentUrl();
        		 return PageURL;
        	 }
    	 }else {
    		 SeleniumUtility.sendkeys(driver, 20, Password, password );
        	 SeleniumUtility.Click(driver, 10, loginBtn);
        	 if(driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("Mfa_page_url")))
        	 {
        		try {
        		Thread.sleep(20000);
        		mailUtils = new MailUtils();
        		String otp = mailUtils.readTheLatestMail(host, username, password, Expected_Sender, Expected_subject, Filepath);
        	    
        	    enterOtp(otp);
        	    PageURL=driver.getCurrentUrl();
       		    return PageURL;
        		}catch(Exception e) { 
        			log.error("Failed to enter OTP" +e.getMessage());
        			e.printStackTrace();
        		}
       		   
        	 }else if(driver.getCurrentUrl().equalsIgnoreCase(ConfigUtils.getConfigData("Home_page_url")))
        	 {
        		 PageURL=driver.getCurrentUrl();
        		 return PageURL;
        	 }
    	 }
    	
    	 
    	 return PageURL;
     }
     
	public HomePage enterOtp(String value)
	{
		SeleniumUtility.Clear(driver, 10, OtpTextArea);
		SeleniumUtility.sendkeys(driver, 20, OtpTextArea, value);
		SeleniumUtility.Click(driver, 10, submitBtn);
		return new HomePage();
		//return driver.getCurrentUrl();
	}
	
	public void changePassword()
	{
		String expMessage=null;
		
		expMessage=passwordExp_InfoMessage.getText();
		if(expMessage!=null)
		{
			String msg[]=expMessage.split(" ");
			int NumOfDays=Integer.parseInt(msg[5]);
			log.info("password expire days" +NumOfDays);
			if(NumOfDays>1){
				SeleniumUtility.Click(driver, 15, skipButton);
			}else if(NumOfDays==1){
               SeleniumUtility.Click(driver, 15, changePassword);
			}
		}
	}
	
	public String logoutFromPortal()
	{
		String loginPageURL=null;
		try {
			SeleniumUtility.waitTillElementIsVisibile(driver, 15, ActiveUserName);
		SeleniumUtility.moveToElement(ActiveUserName);
		String username=SeleniumUtility.getAttribute(ActiveUserName, "title");
		log.info("Active username in the portal" +username);
		}catch(Exception e)
		{
			log.error("Failed to fetch the active username from the portal");
		}
		SeleniumUtility.Click(driver, 10, ActiveUserName);
		List<WebElement> userDropDown = driver.findElements(By.xpath("//ul[@class='userDropdownUL']/li"));
		log.info("Values in the user dropdown are as follows:");
		for(WebElement userdropDownValues:userDropDown)
		{
			log.info(userdropDownValues.getText());
		}
		WebElement logoutBtn = driver.findElement(By.xpath("//ul[@class='userDropdownUL']/li[@title='Logout']"));
		
		SeleniumUtility.Click(driver, 15, logoutBtn);
		log.info("Clicked on logout button");
		log.info("Getting the login page url");
		loginPageURL=driver.getCurrentUrl();
		log.info("login page url is" +loginPageURL);
		return loginPageURL;
	
	}
	
	
	
	
	
}
