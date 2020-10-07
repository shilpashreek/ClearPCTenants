package com.qa.clearpc.utility;

import java.io.File;

import com.qa.clearpc.BaseClass.BaseClass;

public class Constants extends BaseClass {

	//waits
	public static final long PAGE_LOAD_TIMEOUT=60;
	public static final long IMPLICIT_WAIT=30;
	
	//gives the path of current working directory 
	public static final String PROJECT_PATH = System.getProperty("user.dir");
	
	//config paths
	public static final String LOG4J_PROPERTIES_PATH = "D:\\automationPULLprojects\\ClearPCTenants\\src\\main\\resources\\log4j.properties";
	
	public static final String TENANT_CONFIGURATION_FILE = "D:\\automationPULLprojects\\ClearPCTenants\\src\\main\\java\\com\\qa\\clearpc\\configuration\\";
	
	//drivers path
	public static final String CHROME_DRIVER = PROJECT_PATH +File.separator + "Drivers" +File.separator
			+ "chromedriver.exe";
	public static final String GECKO_DRIVER = PROJECT_PATH +File.separator+"Drivers"+File.separator
			+ "geckodriver.exe";
	public static final String IE_DRIVER = PROJECT_PATH+File.separator+"Drivers"+File.separator
			+"IEDriverServer.exe";
	
	//screenshots folder path
	public static final String SCREENSHOT_FOLDER_PATH = PROJECT_PATH +File.separator + "Screenshots" +File.separator;
	
	//Report folder path
	public static final String REPORT_FOLDER_PATH = PROJECT_PATH + File.separator + "Reports"+File.separator +"ExtentReport.html";
	
	//testdata path
	public static final String TESTDATA_PATH = "D:\\automationPULLprojects\\ClearPCTenants\\src\\main\\java\\com\\qa\\clearpc\\testdata\\PCtenantsTestData.xlsx";
	
	public static final String CHROME_USER_DATA_PATH= PROJECT_PATH + File.separator + "chromedata" + File.separator
			+ "User Data" +File.separator;
	
	public static final String USER_DATA_TEMP_FOLDER = PROJECT_PATH + File.separator + "temp" +File.separator;
	
	//mail body
	public static final String mail_Body_path = "D:\\automationPULLprojects\\ClearPCTenants\\src\\main\\resources\\mailBody";
	
	
	
}
