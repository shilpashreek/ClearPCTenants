package com.qa.clearpc.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import com.qa.clearpc.BaseClass.BaseClass;

public class ConfigUtils extends BaseClass {
	
	private FileInputStream fis;
	static Properties prop;
	
	public void loadTenantConfig(String tenant)
	{
		try {
			File src = new File(Constants.TENANT_CONFIGURATION_FILE +tenant+ ".properties");
			log.info("Config file of the following tenant is being loaded  " +tenant);
			fis=new FileInputStream(src);
			prop=new Properties();
			prop.load(fis);
		} catch (Exception e) {
			log.error("Failed to load the respective tenant configuration file" +tenant +" " +e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	public static String getConfigData(String key)
	{
		String value=null;
		try {
			value=prop.getProperty(key);
			if(value==null)
			{
				System.out.println("unable to find the value for the provided key-->" +key);
			}
		} catch (Exception e) {
			value="";
			log.error("could not find value for the key", e);
			e.printStackTrace();
		}
		return value.trim();
	}

}
