package com.autotesting.framework.screens;

/**
 * author: Ramaniuk Anton
 * Класс описывает объекты и основные методы работы с ними для СКМВ сервиса GetPersonNumber
 * Объекты общие для всех сервисов (урл свагера, урл сервисов, кнопка Explore) описаны в классе BaseScreen 
 * 			и при наследовании передаются сюда
 * 
 * 
 */


import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import com.autotesting.framework.utils.Photogropher;
import com.autotesting.framework.utils.PropertiesReader;
import com.autotesting.framework.utils.WebDriverBaseActions;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetPersonNumberServicePage extends BaseScreen {
	
	private final Logger logger = LoggerFactory.getLogger(GetPersonNumberServicePage.class);
	private WebDriverBaseActions action = new WebDriverBaseActions(null);
	private static final int TIME_OUT_FOR_SLEEP=PropertiesReader.getIntProperty("timeOutForSleep");
	private static final String FILE_PATH_EXPECTED_RESULTS = PropertiesReader.getPersonNumberExpectedResultsFiles();
	private static final String SERVICE_NAME="GET_PERSON_NUMBER"; //имя сервиса для отчетности и скринов
	//описание основных элементов для сервиса Get Person Number
	private static final String GET_PERSON_NUMBER_EXPAND_OPERATIONS="//li[@id='resource_GetPersonNumber']//a[contains(@onclick,'expandOperationsForResource')]";
	private static final String GET_PERSON_NUMBER_BODY="//div[@id='GetPersonNumber_getPersonNumber_post_0_content']//textarea[@class='body-textarea']";
	private static final String GET_PERSON_NUMBER_SUBMIT_REQUEST="//div[@id='GetPersonNumber_getPersonNumber_post_0_content']//input[@value='Try it out!']";
	private static final String GET_PERSON_NUMBER_RESPONSE_BODY="//div[@id='GetPersonNumber_getPersonNumber_post_0_content']//pre[@class='json']/code";
	private static final String GET_PERSON_NUMBER_RESPONSE_CODE="//div[@id='GetPersonNumber_getPersonNumber_post_0_content']//div[@class='block response_code']/pre";
	
	public GetPersonNumberServicePage getPersonNumberByData(String testData, String nameOfTest) throws InterruptedException {
		driver.get(SWAGGER_URL);
		driver.findElement(By.xpath(SKMV_URL_FIELD)).clear();
		action.setField(SKMV_URL_FIELD, SKMV_SERVICES_URL, "SKMV_URL_FIELD",nameOfTest, SERVICE_NAME);
		action.clickByXpath(EXPLORE_BUTTON, "EXPLORE_BUTTON",nameOfTest, SERVICE_NAME);		
		TimeUnit.SECONDS.sleep(TIME_OUT_FOR_SLEEP); 
		action.clickByXpath(GET_PERSON_NUMBER_EXPAND_OPERATIONS, "EXPAND_OPERATIONS_BUTTON",nameOfTest, SERVICE_NAME);
		action.setField(GET_PERSON_NUMBER_BODY, testData, "REQUEST_BODY",nameOfTest, SERVICE_NAME);
		action.clickByXpath(GET_PERSON_NUMBER_SUBMIT_REQUEST,"BUTTON_TRY_IT_OUT",nameOfTest, SERVICE_NAME);
		
		//создание элемента и скролинг до этого элемента(тело ответа) по джава скрипту для снятие скриншота
		WebElement responseBody = driver.findElement(By.xpath(GET_PERSON_NUMBER_RESPONSE_BODY));
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+ responseBody.getLocation().y + ")"); //сдвигает страницу до нужного элемента. в данном случае то тела ответа
		
		logger.info("[ACTION]: CAPTURE SCREENSHOT OF RESPONSE BODY FOR TEST:"+nameOfTest);
		TimeUnit.SECONDS.sleep(TIME_OUT_FOR_SLEEP); 
		Photogropher.doScreenshot(nameOfTest+"_RESPONSE_BODY", SERVICE_NAME);
		return this;	
	}
	
    public String getResponseBodyText() throws UnsupportedEncodingException {
    	String result = driver.findElement(By.xpath(GET_PERSON_NUMBER_RESPONSE_BODY)).getText();
    	logger.info("[ACTION]: COLLECTED RESPONSE TEXT");
    	return result;
    }
    
    public String getResponseCode(){
    	String responseCode = driver.findElement(By.xpath(GET_PERSON_NUMBER_RESPONSE_CODE)).getText();
    	logger.info("[ACTION]: COLLECTED RESPONSE CODE");
    	return responseCode;
    }
    
    
}
