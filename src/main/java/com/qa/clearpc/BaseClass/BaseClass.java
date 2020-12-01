package com.qa.clearpc.BaseClass;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.clearpc.Listeners.WebEventListener;
import com.qa.clearpc.utility.ChromeFlash;
import com.qa.clearpc.utility.ConfigUtils;
import com.qa.clearpc.utility.Constants;
import com.qa.clearpc.utility.Xls_Reader;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class BaseClass {
	public static WebDriver driver;
	public static ExtentReports extent;
	public static ExtentTest extentTest;
	public static Logger log;
	static EventFiringWebDriver event_driver;
	static WebEventListener eventListener;
	Xls_Reader reader;
	ConfigUtils configUtils;
	public String tenantName = "", browserName = "";
	final static String temp_folder_name = "chromedata" + new Date().getTime();
	protected String BrowserName;
	private static ChromeOptions chromeOptions;
	static String portalUrl;

	/*
	 * public BaseClass() { reader=new Xls_Reader(); }
	 */

	@Parameters(value = { "tenant", "browser" })
	@BeforeSuite(alwaysRun = true)
	// @Parameters(value= {"tenant", "browser"})
	public void BeforesuiteSetup(String tenant, String browser) {
		tenantName = tenant;
		browserName = browser;

		// extent report setup
		extent = new ExtentReports(Constants.REPORT_FOLDER_PATH + tenantName + File.separator + (tenantName + ".html"));
		extent.addSystemInfo("enviroment", "production");
		extent.addSystemInfo("Tenant", "DaxPC");

		// logs configuration
		// DOMConfigurator.configure(Constants.LOG4J_PROPERTIES_PATH);
		PropertyConfigurator.configure(Constants.LOG4J_PROPERTIES_PATH);
		log = Logger.getLogger(this.getClass().getName());

		// loading testData file and tenant configuration properties
		configUtils = new ConfigUtils();
		configUtils.loadTenantConfig(tenant);
		reader = new Xls_Reader();

		log.info("Following Tenant Configuration is being loaded--" + tenant);
		// loading chrome user data
		if (browser.equalsIgnoreCase("chrome")) {
			try {
				File tempDir = new File(Constants.USER_DATA_TEMP_FOLDER);
				if (tempDir.exists()) {
					try {
						FileUtils.deleteDirectory(tempDir);
					} catch (Exception e) {

					}
					tempDir.mkdirs();
					File source = new File(Constants.CHROME_USER_DATA_PATH);
					File destination = new File(Constants.USER_DATA_TEMP_FOLDER + temp_folder_name);
					FileUtils.copyDirectory(source, destination);
				}
			} catch (IOException e) {
				log.info(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	@BeforeTest(alwaysRun = true)
	public void initialisation() {
		BrowserName = ConfigUtils.getConfigData("browser");
		log.info("selected browser is--" + BrowserName);
		switch (BrowserName.toLowerCase()) {
		case "firefox":
			System.setProperty("webdriver.gecko.driver", Constants.GECKO_DRIVER);
			driver = new FirefoxDriver();
			break;

		case "chrome":
			chromeOptions = new ChromeOptions();
			// setting up basic desired settings for chrome browser
			HashMap<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.plugins", 1);
			prefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player", 1);
			prefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player", 1);

			// disable content realted popup blocking
			prefs.put("profile.default_content_settings.popups", 1);
			// enable safe browsing
			prefs.put("safebrowsing.enabled", "true");
			// set user defined download path

			chromeOptions.setExperimentalOption("prefs", prefs);

			// To disable all the certificate errors
			chromeOptions.addArguments("-test-type");
			// To open the browser in incognito mode
			// chromeOptions.addArguments("--disable-notifications");
			// chromeOptions.addArguments("--incognito");
			// openbrowser maximized state
			chromeOptions.addArguments("start-maximized");
			// Always allow the authorized plugins
			chromeOptions.addArguments("--always-authorize-plugins=true");
			// chromeOptions.addArguments("disable-extensions");
			chromeOptions.addArguments("--enable-automation");
			// enable native client
			// chromeOptions.addArguments("--enable-npapi");
			chromeOptions.addArguments("-enable-pnacl");
			// chromeOptions.addArguments("--enable-pnacl");
			// chromeOptions.addArguments("enable-pnacl");
			chromeOptions.addArguments("-enable-nacl");
			// chromeOptions.addArguments("--enable-nacl");
			// Disable popup blocking
			chromeOptions.addArguments("-disable-popup-blocking=true");
			chromeOptions.addArguments("user-data-dir=" + Constants.USER_DATA_TEMP_FOLDER + temp_folder_name);
			// Disable info bars
			chromeOptions.addArguments("disable-infobars");
			chromeOptions.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			// always accept secure socket layer alerts
			chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			chromeOptions.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);

			break;

		case "ie":
			System.setProperty("webdriver.ie.driver", Constants.IE_DRIVER);
			driver = new InternetExplorerDriver();
			break;
		}
	}

	// loads portal
	public static void loadPortal() {

		// suppress chrome browser error logs
		System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
		System.setProperty("webdriver.chrome.driver", Constants.CHROME_DRIVER);

		driver = new ChromeDriver(chromeOptions);
		// driver = new ChromeDriver();

		// webdriverFiring event setup
		event_driver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		event_driver.register(eventListener);
		// driver = event_driver;

		// driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Constants.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);

		try {
			portalUrl = ConfigUtils.getConfigData("url");
			log.info("launching portal url--" + portalUrl);
			if (driver instanceof ChromeDriver) {
				new ChromeFlash((ChromeDriver) driver).addSite(ConfigUtils.getConfigData("url"));
				Thread.sleep(2000);
				driver.get(portalUrl);
			}
		} catch (InterruptedException e) {
			log.error("Failed to load driver for the BC portal" + e.getMessage());
			Assert.fail("Failed to load driver for the BC portal" + e.getMessage());
			e.printStackTrace();
		}
	}

	private void deleteTempFolder() {
		try {
			File temp = new File(Constants.USER_DATA_TEMP_FOLDER);
			if (temp.exists()) {
				FileDeleteStrategy.FORCE.delete(temp);
			}
		} catch (IOException e) {
			log.info(e.getMessage());
			e.printStackTrace();
		}
	}

	@AfterSuite(alwaysRun = true)
	public void endSuite() {
		extent.flush();
		deleteTempFolder();

	}

}
