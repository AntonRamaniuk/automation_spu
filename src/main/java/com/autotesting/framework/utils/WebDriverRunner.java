package com.autotesting.framework.utils;

import java.io.File;


import java.util.concurrent.TimeUnit;

//import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class WebDriverRunner {
	protected static final Logger log = LoggerFactory.getLogger(WebDriverRunner.class);
	private static final String PATH_TO_CHROMEDRIVER = "resource//chromedriver.exe";
	public static final int TIMEOUT_FOR_ACTION_SECONDS = PropertiesReader.getTimeoutWaitForElement();
	protected static  WebDriver driver;
	private static  ChromeDriverService service;
	
	private static WebDriverRunner instanse = null;

	
	public static WebDriverRunner getWDR(){
		if(instanse==null){
			instanse = new WebDriverRunner();
		}
		return instanse;
	}
	
	
	public WebDriverRunner() {

		try {
			service = new ChromeDriverService.Builder().usingChromeDriverExecutable(new File(PATH_TO_CHROMEDRIVER)).usingAnyFreePort().build();
			service.start();
			driver = new ChromeDriver(service);
			systemWait();
			log.debug("Dirver is created");
		} catch (Exception e) {
			log.error("Ошибка создания сущности драйвера", e);
		}
	}

	public WebDriver getDriver(){
		return driver;
	}
	
	public void systemWait(){
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(TIMEOUT_FOR_ACTION_SECONDS, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		
	}
	
	public void stopWebDriver() {
		driver.quit();
		service.stop();
}

}
