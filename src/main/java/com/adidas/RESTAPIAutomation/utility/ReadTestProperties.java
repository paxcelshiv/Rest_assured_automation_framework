package com.adidas.RESTAPIAutomation.utility;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadTestProperties {
	public static String BaseUrl;
	public static String ContextPath;
	public static Properties prop;

	public ReadTestProperties() {

	}

	static {
		FileInputStream ip;
		try {
			ip = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/testing.properties");
			prop = new Properties();
			prop.load(ip);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		try {
			BaseUrl = prop.getProperty("BaseUrl");
			ContextPath = prop.getProperty("ContextPath");

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
