package com.autotesting.framework.utils;

/**
 * author: Ramaniuk Anton
 * Класс вытягивания пропертей из проперти файла. описаны общие проперти и несколько частных
 * 
 */

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesReader {
	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	private static final String CONFIG_FILE_PATH = "configuration.properties";
	private static Properties PROPERTIES;

	static {
		PROPERTIES = new Properties();
		try {
			PROPERTIES.load(ClassLoader.getSystemClassLoader().getResourceAsStream(CONFIG_FILE_PATH));
			logger.info("PROPERTIES FILE IS OPENED AND READ:"+PROPERTIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// берет url
	// в файлу конфигов должно выглядеть
	// application.url=0.0.0.0
	public static String getApplicationURL() {
		return getProperty("application.url");
	}
	
	public static String getSnapshootPath(){
		return getProperty("screenshot.path");
	}
	
	public static String getServicesURL() {
		return getProperty("services.url");
	}
	
	public static int getTimeoutWaitForElement() {
		return getIntProperty("timeOut");
	}

	//путь для смоук теста
	public static String smokeTestsExpectedResults(){
		return getProperty("smokeTests.expectedPath");
	}
	
	public static String getPersonExpectedResultsFiles(){
		return getProperty("getPerson.expectedPath");
	}
	
	public static String validatePersonExpectedResultsFiles(){
		return getProperty("validatePerson.expectedPath");
	}
	
	public static String getPersonNumberExpectedResultsFiles(){
		return getProperty("getNumber.expectedPath");
	}
	
	public static String getSalaryExpectedResultsFiles(){
		return getProperty("getSalary.expectedPath");
	}
	
	// берет конфиги по ключу
	public static String getProperty(String key) {
		String propertyValue = PROPERTIES.getProperty(key);
		return propertyValue;
	}
	
	// берет конфиги по ключу для интовых значения
		public static int getIntProperty(String intKey) {
			String propertyStrValue = PROPERTIES.getProperty(intKey);
			int propertyIntValue = Integer.parseInt(propertyStrValue);
			return propertyIntValue;
		}

}
