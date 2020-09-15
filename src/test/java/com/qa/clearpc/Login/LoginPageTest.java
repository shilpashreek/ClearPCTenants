package com.qa.clearpc.Login;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.clearpc.BaseClass.BaseClass;
import com.qa.clearpc.pages.LoginPage;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.Constants;
import com.qa.clearpc.utility.MailUtils;
import com.qa.clearpc.utility.SeleniumUtility;
import com.qa.clearpc.utility.Xls_Reader;

public class LoginPageTest extends BaseClass {
	BaseClass baseClass;
	LoginPage loginPage;
	Xls_Reader reader;
	String sheetname = "LoginData";
	String mailDetailsSheet = "mailProps";
	SeleniumUtility seleniumUtility;
	MailUtils mailUtils;

	public LoginPageTest() {
		super();
	}

	@BeforeTest
	public void setUp() {
		baseClass = new BaseClass();
		BaseClass.loadPortal();
		loginPage = new LoginPage();

	}

	@DataProvider
	public Object[][] getLoginData() {
		reader = new Xls_Reader();
		Object[][] loginData = reader.GetData(sheetname);
		return loginData;
	}

	@Test(priority=1, enabled=false, dataProvider= "getLoginData", description = "Verify login with invalid data and capture error message")
	public void validateLoginForInvalidData(String username, String password) {
		try {
			extentTest = extent.startTest("validateLoginForInvalidData");
			loginPage.loginToPortal(username, password);
			String errorMessage = driver.findElement(By.id("invalidCredientials")).getText();
			System.out.println(errorMessage);
			Assert.assertTrue(
					errorMessage.trim().equalsIgnoreCase(ConfigUtils.getConfigData("invalidCred_errorMessage")));
		} catch (Exception e) {
			log.error("LoginCredentials are invalid, error message is not displayed", e);
			Assert.fail("LoginCredentials are invalid, error message is not displayed");
		}
	}

	@DataProvider
	public Object[][] getMailProperties() {
		reader = new Xls_Reader();
		Object[][] mailData = reader.GetData(mailDetailsSheet);
		return mailData;
	}
	
	@Test(priority = 2, dataProvider="getMailProperties",enabled = true, description = "Verify login to clear portal with valid credentials")
	public void validateLogin(String host, String username, String password, String Expected_Sender,String Expected_subject) 
	{
		try {
			seleniumUtility = new SeleniumUtility();
			extentTest = extent.startTest("validateLogin");
			loginPage.loginToPortal(ConfigUtils.getConfigData("username"), ConfigUtils.getConfigData("password"));
			String currentUrl = seleniumUtility.returnCurrentUrl();
			if (currentUrl.equalsIgnoreCase(ConfigUtils.getConfigData("Mfa_page_url"))) {
				try {
					Thread.sleep(20000);
					mailUtils = new MailUtils();
					String MFA_Code = mailUtils.readTheLatestMail(host, username, password, Expected_Sender,Expected_subject, Constants.mail_Body_path);
					loginPage.enterOtp(MFA_Code);
					if(currentUrl.equalsIgnoreCase(ConfigUtils.getConfigData("Home_page_url")))
					{
						Assert.assertTrue(seleniumUtility.returnCurrentUrl().equals(ConfigUtils.getConfigData("Home_page_url")));
					}else if(currentUrl.equalsIgnoreCase(ConfigUtils.getConfigData("pwdExp_url")))
					{
						loginPage.changePassword();
					}
				
				} catch (Exception e) {
					log.error("Failed to enter Otp" + e.getMessage());
					e.printStackTrace();
				}
			} else if (currentUrl.equalsIgnoreCase(ConfigUtils.getConfigData("Home_page_url"))) {
				Assert.assertTrue(seleniumUtility.returnCurrentUrl().equals(ConfigUtils.getConfigData("Home_page_url")));     	
			}
		} catch (Exception e) {
			log.error("Failed to login to the application");
			Assert.fail("Failed to login to the application");
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
