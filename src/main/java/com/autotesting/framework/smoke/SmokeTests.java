package com.autotesting.framework.smoke;

/**
 * @author Anton.Romanjuk
 * Сьют для смоук теста. 1 тест на каждый из 4 сервисов. вынесено в отдельный класс и пакет для удобства запуска
 *  
 *
 */


import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.autotesting.framework.screens.BaseScreen;
import com.autotesting.framework.screens.GetPersonNumberServicePage;
import com.autotesting.framework.screens.GetPersonServicePage;
import com.autotesting.framework.screens.GetSalaryServicePage;
import com.autotesting.framework.screens.ValidatePersonServicePage;
import com.autotesting.framework.testdata.GetPersonNumberTestData;
import com.autotesting.framework.testdata.GetPersonTestData;
import com.autotesting.framework.testdata.GetSalaryTestData;
import com.autotesting.framework.testdata.ValidatePersonTestData;

public class SmokeTests {
	
	GetPersonServicePage getPerson = new GetPersonServicePage();
	ValidatePersonServicePage validatePerson = new ValidatePersonServicePage();
	GetPersonNumberServicePage getPersonNumber = new GetPersonNumberServicePage();
	GetSalaryServicePage getSalary = new GetSalaryServicePage();
	private static final String PERSON_NUMBER_SMOKE_TEST = GetPersonTestData.getProperty("getPerson.smokeTest");
	private static final String SMOKE_TEST_FILE_VALIDATE_PERSON = "validate_person_smoke_test.txt";
	private static final String SMOKE_TEST_FILE_GET_PERSON_NUMBER = "get_person_number_smoke_test.txt";
	private static final String SMOKE_TEST_FILE_GET_SALARY= "get_salary_smoke_test.txt";
	private static final String VALID_RESPONSE_CODE = "200";
	
	
	@AfterSuite
	public void finishTests(){
		BaseScreen.closeBrowser();
	}

	//Smoke Test for service Get Person. Фамилия - Зуброва. Все данные валидны.
	@Test 
	public void getPersonTestValidNumber() throws InterruptedException, IOException {
		getPerson.openSession();
		getPerson.validatePersonInfoByNumber(PERSON_NUMBER_SMOKE_TEST,"Smoke_getPersonTestValidNumber");
		Assert.assertEquals(getPerson.getResponseCode(), VALID_RESPONSE_CODE,"INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(getPerson.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				getPerson.smokeReturnExpectedResult("getPerson_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(),"INCORRECT RESPONSE MESSAGE");

	}
	
	//Smoke Test for service ValidatePerson. Фамилия - Зуброва. Все данные валидны.
	@Test (dependsOnMethods = {"getPersonTestValidNumber"})
	public void validatePersonValidData() throws IOException, InterruptedException {
		validatePerson.openSession();
		String VALIDATE_PERSON_DATA_SMOKE_TEST = ValidatePersonTestData.returnTestData(SMOKE_TEST_FILE_VALIDATE_PERSON);
		validatePerson.validatePerson(VALIDATE_PERSON_DATA_SMOKE_TEST, "Smoke_validatePersonValidData");
		Assert.assertEquals(validatePerson.getResponseCode(), VALID_RESPONSE_CODE, "INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(validatePerson.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				validatePerson.smokeReturnExpectedResult("validatePerson_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(), "INCORRECT RESPONSE MESSAGE");
	}

	//Smoke Test for service GetPersonNumber. Фамилия - Зуброва. Все данные валидны.
	@Test (dependsOnMethods = {"getPersonTestValidNumber", "validatePersonValidData"})
	public void getPersonNumberValidData() throws IOException, InterruptedException {
		getPersonNumber.openSession();
		String GET_PERSON_NUMBER_DATA_SMOKE_TEST = GetPersonNumberTestData.returnTestData(SMOKE_TEST_FILE_GET_PERSON_NUMBER);
		getPersonNumber.getPersonNumberByData(GET_PERSON_NUMBER_DATA_SMOKE_TEST, "Smoke_getPersonNumberValidData");
		Assert.assertEquals(getPersonNumber.getResponseCode(), VALID_RESPONSE_CODE, "INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(getPersonNumber.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				getPersonNumber.smokeReturnExpectedResult("getPersonNumber_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(), "INCORRECT RESPONSE MESSAGE");
	}

	//Smoke Test for service GetSalary. Фамилия - Зуброва. Все данные валидны.
	@Test (dependsOnMethods = {"getPersonTestValidNumber", "validatePersonValidData", "getPersonNumberValidData"})
	public void getSalaryValidData() throws IOException, InterruptedException {
		getSalary.openSession();
		String GET_SALARY_DATA_SMOKE_TEST = GetSalaryTestData.returnTestData(SMOKE_TEST_FILE_GET_SALARY);
		getSalary.getPersonSalary(GET_SALARY_DATA_SMOKE_TEST, "Smoke_getSalaryValidData");
		Assert.assertEquals(getSalary.getResponseCode(), VALID_RESPONSE_CODE, "INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(getSalary.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				getSalary.smokeReturnExpectedResult("getSalary_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(), "INCORRECT RESPONSE MESSAGE");
	}
}
