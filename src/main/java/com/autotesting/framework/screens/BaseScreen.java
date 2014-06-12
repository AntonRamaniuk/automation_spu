package com.autotesting.framework.screens;

/**
 * author: Ramaniuk Anton
 * Общий класс для каждой страницы. создание объекта драйвера и общие ссылки для каждого из сервисов
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import com.autotesting.framework.utils.PropertiesReader;
import com.autotesting.framework.utils.WebDriverRunner;

public class BaseScreen {

	protected static final Logger logger = LoggerFactory.getLogger(BaseScreen.class);
	protected WebDriver driver = null;
	protected WebDriverRunner runner = null;
	protected static final String SWAGGER_URL = PropertiesReader.getApplicationURL();
	protected static final String SKMV_SERVICES_URL=PropertiesReader.getServicesURL();
	protected static final String SKMV_URL_FIELD = "//input[@id='input_baseUrl']";
	protected static final String EXPLORE_BUTTON = "//a[@id='explore']";
	
	public void openSession() {  
		driver  = WebDriverRunner.getWDR().getDriver();
		logger.info("[DRIVER]: Browser is opened");
	}

	public static void closeBrowser() {
		logger.debug("[DRIVER]: Browser is closed");
		WebDriverRunner.getWDR().stopWebDriver();
	}

	
} 
