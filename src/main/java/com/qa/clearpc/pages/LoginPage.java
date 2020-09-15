package com.qa.clearpc.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.utility.MailUtils;
import com.qa.clearpc.utility.SeleniumUtility;
import com.qa.clearpc.utility.Xls_Reader;

public class LoginPage extends BaseClass
{
	SeleniumUtility seleniumUtility;
	MailUtils mailUtils;
	Xls_Reader reader;

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
	
     
	public HomePage enterOtp(String value)
	{
		SeleniumUtility.Clear(driver, 10, OtpTextArea);
		SeleniumUtility.sendkeys(driver, 20, OtpTextArea, value);
		SeleniumUtility.Click(driver, 10, submitBtn);
		return new HomePage();
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
	
	
}
