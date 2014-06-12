package com.autotesting.framework.screens;

/**
 * author: Ramaniuk Anton
 * Класс описывает объекты и основные методы работы с ними для СКМВ сервиса Get Person
 * Объекты общие для всех сервисов (урл свагера, урл сервисов, кнопка Explore) описаны в классе BaseScreen 
 * 			и при наследовании передаются сюда
 * 
 * 
 */

import java.io.FileInputStream;
import java.io.IOException;
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

public class GetPersonServicePage extends BaseScreen {
	private WebDriverBaseActions action = new WebDriverBaseActions(null);
	protected final Logger logger = LoggerFactory.getLogger(GetPersonServicePage.class);
	BaseScreen baseScreen = new BaseScreen();
	private static final String SERVICE_NAME="GET_PERSON"; //имя сервиса для отчетности и скринов
	private static final String FILE_PATH_EXPECTED_RESULTS = PropertiesReader.getPersonExpectedResultsFiles();
	private static final String SMOKE_FILE_PATH_EXPECTED_RESULTS = PropertiesReader.smokeTestsExpectedResults();
	private static final int TIME_OUT_FOR_SLEEP=PropertiesReader.getIntProperty("timeOutForSleep");
	//описание основных элементов для сервиса Get Person
	private static final String GET_PERSON_EXPAND_OPERATIONS="//li[@id='resource_GetPerson']//a[contains(@onclick,'expandOperationsForResource')]";
	private static final String GET_PERSON_BODY="//div[@id='GetPerson_getPersonDataByNumber_post_0_content']//textarea[@class='body-textarea']";
	private static final String GET_PERSON_SUBMIT_REQUEST="//div[@id='GetPerson_getPersonDataByNumber_post_0_content']//input[@value='Try it out!']";
	private static final String GET_PERSON_RESPONSE_BODY="//div[@id='GetPerson_getPersonDataByNumber_post_0_content']//pre[@class='json']/code";
	private static final String GET_PERSON_RESPONSE_CODE="//div[@id='GetPerson_getPersonDataByNumber_post_0_content']//div[@class='block response_code']/pre";
	//private static final String GET_PERSON_LINK ="//li[@id='resource_GetPerson']/div[@class='heading']//a[contains(@onclick,'toggleEndpointListForResource')]";
	//private static final String GET_PERSON_POST="//li[@id='GetPerson_getPersonDataByNumber_post_0']/div[@class='heading']//span[@class='http_method']/a[@class='toggleOperation']";
	//private static final String GET_PERSON_MODEL_SCHEMA = "//div[@id='GetPerson_getPersonDataByNumber_post_0_content']//a[@class='snippet-link']";
	
	public GetPersonServicePage validatePersonInfoByNumber(String personNumber, String nameOfTest) throws InterruptedException {		
		driver.get(SWAGGER_URL);
		driver.findElement(By.xpath(SKMV_URL_FIELD)).clear();
		action.setField(SKMV_URL_FIELD, SKMV_SERVICES_URL, "SKMV_URL_FIELD",nameOfTest, SERVICE_NAME);
		action.clickByXpath(EXPLORE_BUTTON, "EXPLORE_BUTTON",nameOfTest, SERVICE_NAME);
		TimeUnit.SECONDS.sleep(TIME_OUT_FOR_SLEEP); 
		action.clickByXpath(GET_PERSON_EXPAND_OPERATIONS, "EXPAND_OPERATIONS_BUTTON",nameOfTest, SERVICE_NAME);
		action.setField(GET_PERSON_BODY, personNumber, "REQUEST_BODY",nameOfTest, SERVICE_NAME);
		action.clickByXpath(GET_PERSON_SUBMIT_REQUEST,"BUTTON_TRY_IT_OUT",nameOfTest, SERVICE_NAME);
		TimeUnit.SECONDS.sleep(TIME_OUT_FOR_SLEEP); 
		
		//создание элемента и скролинг до этого элемента(тело ответа) по джава скрипту для снятие скриншота
		WebElement responseBody = driver.findElement(By.xpath(GET_PERSON_RESPONSE_BODY));
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+ responseBody.getLocation().y + ")"); //сдвигает страницу до нужного элемента. в данном случае то тела ответа
		
		logger.info("[ACTION]: CAPTURE SCREENSHOT OF RESPONSE BODY FOR TEST:"+nameOfTest);
		TimeUnit.SECONDS.sleep(TIME_OUT_FOR_SLEEP); 
		Photogropher.doScreenshot(nameOfTest+"_RESPONSE_BODY", SERVICE_NAME);
		return this;
		
	}
	
    public String getResponseBodyText() throws UnsupportedEncodingException {
    	String result = driver.findElement(By.xpath(GET_PERSON_RESPONSE_BODY)).getText();
    	logger.info("[ACTION]: COLLECTED RESPONSE TEXT: "+result);
    	return result;
    }
    
    public String getResponseCode(){
    	String responseCode = driver.findElement(By.xpath(GET_PERSON_RESPONSE_CODE)).getText();
    	logger.info("[ACTION]: COLLECTED RESPONSE CODE: "+responseCode);
    	return responseCode;
    }
    
    @SuppressWarnings("resource")
	public String returnExpectedResult(String nameOfFile) throws IOException{
    	FileInputStream inFile = new FileInputStream(FILE_PATH_EXPECTED_RESULTS+"/"+nameOfFile);
    	byte[] str = new byte[inFile.available()];
    	inFile.read(str);
    	String expectedResult = new String(str);
    	return expectedResult;
    }
    
    @SuppressWarnings("resource")
	public String smokeReturnExpectedResult(String nameOfFile) throws IOException{
		FileInputStream inFile = new FileInputStream(SMOKE_FILE_PATH_EXPECTED_RESULTS+"/"+nameOfFile);
    	byte[] str = new byte[inFile.available()];
    	inFile.read(str);
    	String expectedResult = new String(str);
    	return expectedResult;
    }
    
/* 
	public LoginPageScreen setLogin(String login) {
		action.setField(LOGIN_FIELD, login);
		return this;
	}
/*	
	public LoginPageScreen setPassword(String password) {
		action.setField(PASSWORD_FIELD, password);
		return this;
	}
	
    public LoginPageScreen pressButtonLogin () {
    	action.clickByXpath(BUTTON_LOGIN);
    	return this;
    }
    
    public LoginPageScreen loginAction (String login, String password) {
    	setLogin(login).setPassword(password).pressButtonLogin();
    	return this;
    }
  /*  
    public String getErrorText() {
    	String result = driver.findElement(By.xpath(ERROR_MESSAGE)).getText();
    	return result;
    }
	
	public String getHeaderText() {
		String result = driver.findElement(By.id(LOGIN_PAGE_HEADER_ID)).getText();
		return result;
	}
	
	public boolean ButtonIsPresent(){
		return driver.findElement(By.xpath(BUTTON_LOGIN)).isDisplayed();
	}
*/
}