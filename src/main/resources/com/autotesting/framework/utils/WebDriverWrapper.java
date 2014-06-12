package com.autotesting.framework.utils;

import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autotesting.framework.utils.PropertiesReader;

public class WebDriverWrapper implements WebDriver {
	private RemoteWebDriver innerDriver = null;
	public static final int TIMEOUT_FOR_ACTION_SECONDS = PropertiesReader.getTimeoutWaitForElement();
	private static Logger logger = LoggerFactory.getLogger(WebDriverWrapper.class);
	
	public WebDriverWrapper(RemoteWebDriver remoteDriver) {
		innerDriver = remoteDriver;
	}
	
	public void clickByXpath(String xpath) {
		waitForElementPresentAndVisible(xpath, TIMEOUT_FOR_ACTION_SECONDS);
		logger.info("[ACTION]: CLICKING ON ELEMENT BY XPATH: '" + xpath +"'");
		WebElement element = innerDriver.findElement(By.xpath(xpath)); 
		if(element.isEnabled()){
			element.click();
			logger.info("[ACTION]: CLICKED ON ELEMENT BY XPATH: '" + xpath +"'");
		}
		
	}
	
	public void inputText (String xpathField, String valueField){
		logger.debug("[ACTION]: INPUTING in ELEMENT BY XPATH: '" + xpathField +"' value: '"+valueField+"'");
		waitForElementPresentAndVisible (xpathField, TIMEOUT_FOR_ACTION_SECONDS);
		WebElement element = innerDriver.findElement(By.xpath(xpathField));
		if (element.isEnabled()){
			element.sendKeys(valueField);
			logger.debug("[ACTION]: INPUTED in ELEMENT BY XPATH: '" + xpathField +"' value: '"+valueField+"'");
		}
	}

	public void waitForElementPresentAndVisible(String locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(this, timeout);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));			
		
	}

	
	@Override
	public void get(String url) {
		innerDriver.get(url);
	}

	@Override
	public String getCurrentUrl() {
		return innerDriver.getCurrentUrl();
	}

	@Override
	public String getTitle() {
		return innerDriver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by) {
		try{
		return innerDriver.findElements(by);
		} catch (Exception e) {
			logger.error("Exception during Driver find a element",e);
		}
		return null;
	}

	@Override
	public WebElement findElement(By by) {
		return innerDriver.findElement(by);
	}

	@Override
	public String getPageSource() {
		return innerDriver.getPageSource();
	}

	@Override
	public void close() {
		innerDriver.close();
	}

	@Override
	public void quit() {
		innerDriver.quit();

	}

	@Override
	public Set<String> getWindowHandles() {
		return innerDriver.getWindowHandles();
	}

	@Override
	public String getWindowHandle() {		
		return innerDriver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo() {
		return innerDriver.switchTo();
	}

	@Override
	public Navigation navigate() {
		return innerDriver.navigate();
	}

	@Override
	public Options manage() {
		return innerDriver.manage();
	}

}
