package com.qa.clearpc.utility;

import java.io.FileInputStream;
import java.util.Properties;
import org.testng.Assert;
import com.qa.clearpc.BaseClass.BaseClass;

public class TestDataUtil extends BaseClass 
{
	static Properties properties;

	public static void loadTenantTestData(String tenant, String environmentip) {
		properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(
					Constants.TESTDATA_PATH + "\\" + environmentip + "\\" + tenant + ".properties");
			properties.load(fis);
			fis.close();
		} catch (Exception e) {
			log.error("Failed to load " + tenant + "'s Test data properties file: " + e.getMessage());
			Assert.fail("Failed to load " + tenant + "'s Test data properties file: " + e.getMessage());
		}
	}

	public static String getTestData(String key) {
		String propertyValue;
		try {
			propertyValue = properties.getProperty(key);
			if (propertyValue == null) {
				log.error("property value of key \"" + key + "\" is not present or value is not assigned in testdata");
			}
		} catch (Exception e) {
			propertyValue = "";
			e.printStackTrace();
		}
		return propertyValue.trim();
	}

}
