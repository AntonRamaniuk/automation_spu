package com.autotesting.framework.regression.swagger;



import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.autotesting.framework.screens.BaseScreen;
import com.autotesting.framework.screens.GetPersonServicePage;

public class GetPersonServiceTests {
	//надо создавать объекты для каждой страницы которая будет использоваться в тесте
	GetPersonServicePage getPerson = new GetPersonServicePage();
	private static final String VALID_RESPONSE_CODE = "200";

	@BeforeMethod
	public void startAnyTest(){
		getPerson.openSession();
	}
	
	@AfterSuite
	public void finishTests(){
		BaseScreen.closeBrowser();
	}
/*	
	//Test number 1. Не валидный СНИЛС (убраны 2 числа из самого СНИЛС). проверить что возвращается ошибка о том что СНИЛС не найден
	@Test
	public void getPersonTestInvalidNumber() throws InterruptedException, IOException {
		getPerson.validatePersonInfoByNumber(PERSON_NUMBER_FIRST_TEST,"getPersonTestInvalidNumber");
		//переделать асерты на асерты с сообщением если асерт сработал
		Assert.assertEquals(getPerson.getResponseCode(), VALID_RESPONSE_CODE,"INCORRECT RESPONSE CODE IS APPEARED: "+getPerson.getResponseCode());
		Assert.assertEquals(getPerson.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				getPerson.returnExpectedResult("getPerson_firstTest.txt").replaceAll("\r\n", "").trim().toUpperCase(),"INCORRECT RESPONSE MESSAGE");
		
	}
	
	//Test number 2. Не валидный СНИЛС (убрана контрольная сумма). проверить что возвращается ошибка о том что СНИЛС не найден
	@Test
	public void getPersonTestInvalidNumberNoKs() throws InterruptedException, IOException {
		getPerson.validatePersonInfoByNumber(PERSON_NUMBER_SECOND_TEST,"getPersonTestInvalidNumberNoKs");
		//переделать асерты на асерты с сообщением если асерт сработал
		Assert.assertEquals(getPerson.getResponseCode(), VALID_RESPONSE_CODE,"INCORRECT RESPONSE CODE IS APPEARED: "+getPerson.getResponseCode());
		Assert.assertEquals(getPerson.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				getPerson.returnExpectedResult("getPerson_secondTest.txt").replaceAll("\r\n", "").trim().toUpperCase(),"INCORRECT RESPONSE MESSAGE");
	}
/*	
	@Test (dependsOnMethods = {"getPersonFirstTest"})
	public void simpleTest2() throws InterruptedException {
		getPerson.validatePersonInfoByNumber(personNumber,"simpleTest2");		
		//Assert.assertEquals(loginPage.getHeaderText(), EXPECTED_TEXT_LOGIN_PAGE_HEADER);
	} */
}  
