package com.autotesting.framework.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.autotesting.framework.utils.Photogropher;
import com.autotesting.framework.utils.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

public class WebDriverBaseActions extends DefaultSelenium {
	
	public static final int TIMEOUT_WAIT = PropertiesReader.getTimeoutWaitForElement();
	public WebDriver driver = WebDriverRunner.getWDR().getDriver();
	protected static final Logger logger = LoggerFactory.getLogger(WebDriverBaseActions.class);
	//ПРОЯСНИТЬ КАК ВСЕ-ТАКИ ЗДЕСЬ СДЕЛАТЬ КРАСИВО. ЧТО БЫ СОЗДАВАЛСЯ ЛЮБОЙ ДРАЙВЕР!!!!
	
	//методы для работы с ajax запросами на ожидание
	public WebDriverBaseActions(String serverHost, int serverPort, String browserStartCommand, String browserURL) {
	    super(serverHost, serverPort, browserStartCommand, browserURL);
	}

    public WebDriverBaseActions(CommandProcessor processor) {
	    super(processor);
	}
	    
	public void waitForAjaxRequests(final int timeout) {
		commandProcessor.doCommand("waitForAjaxRequests", new String[]{String.valueOf(timeout)});
	}

	//методы описания базовых действий над объектами
	public void clickByXpath (String xpath, String nameOfElement, String nameOfTest, String serviceName) {
	    logger.info("[TEST]"+nameOfTest+"[ACTION]: Clicking on element: '" + nameOfElement + ". By xpath: '" + xpath + "'");
	    waitForElementVisible (xpath, TIMEOUT_WAIT);
		//click(driver.findElement(By.xpath(xpath))); - не работает. просит описание метода click
	    driver.findElement(By.xpath(xpath)).click();
	    logger.info("[TEST]"+nameOfTest+"[ACTION]: Clicked on element: '" + nameOfElement + ".'"+ "by xpath: '" + xpath + "'");
	    Photogropher.doScreenshot(nameOfTest+"_"+nameOfElement, serviceName);
	}
	
	public void setField (String xpathField,String valueField, String nameOfField, String nameOfTest, String serviceName){
		logger.info("[TEST]"+nameOfTest+"[ACTION]: Setting in field: '" + nameOfField + "'. By xpath: '"+ xpathField + "'. Value: '"+ valueField+"'");
		driver.findElement(By.xpath(xpathField)).sendKeys(valueField);
		logger.info("[TEST]"+nameOfTest+"[ACTION]: Seted in field: '" + nameOfField + "'. By xpath: '"+ xpathField + "'. Value: '"+ valueField+"'");
		Photogropher.doScreenshot(nameOfTest+"_"+nameOfField, serviceName);
	}
	
	public void waitForElementVisible (String locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));		
	}
	

}
