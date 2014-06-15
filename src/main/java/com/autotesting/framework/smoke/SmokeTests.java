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
import com.autotesting.framework.utils.FileUtils;
import com.autotesting.framework.utils.PropertiesReader;

public class SmokeTests {
	
	GetPersonServicePage getPerson = new GetPersonServicePage();
	ValidatePersonServicePage validatePerson = new ValidatePersonServicePage();
	GetPersonNumberServicePage getPersonNumber = new GetPersonNumberServicePage();
	GetSalaryServicePage getSalary = new GetSalaryServicePage();
	private static final String VALID_RESPONSE_CODE = "200";
	private static final String SMOKE_TESTS_EXPECTED_RESULTS_FILES = PropertiesReader.smokeTestsExpectedResults();
	
	
	@AfterSuite
	public void finishTests(){
		BaseScreen.closeBrowser();
	}

	//Smoke Test for service Get Person. Фамилия - Зуброва. Все данные валидны.
	@Test 
	public void getPersonTestValidNumber() throws InterruptedException, IOException {
		
		String SMOKE_TEST_FILE_VALIDATE_PERSON = "get_person_smoke_test.txt";
		String GET_PERSON_DATA_SMOKE_TEST = FileUtils.returnFileContent(PropertiesReader.getProperty("getPerson.sendPath"),
				SMOKE_TEST_FILE_VALIDATE_PERSON);
		getPerson.validatePersonInfoByNumber(GET_PERSON_DATA_SMOKE_TEST, "Smoke_getPersonTestValidNumber");
		Assert.assertEquals(getPerson.getResponseCode(), VALID_RESPONSE_CODE, "INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(getPerson.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				FileUtils.returnFileContent(SMOKE_TESTS_EXPECTED_RESULTS_FILES, "getPerson_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(), "INCORRECT RESPONSE MESSAGE");
	}
	
	//Smoke Test for service ValidatePerson. Фамилия - Зуброва. Все данные валидны.
	@Test (dependsOnMethods = {"getPersonTestValidNumber"})
	public void validatePersonValidData() throws IOException, InterruptedException {
		validatePerson.openSession();
		String SMOKE_TEST_FILE_VALIDATE_PERSON = "validate_person_smoke_test.txt";
		String VALIDATE_PERSON_DATA_SMOKE_TEST = FileUtils.returnFileContent(PropertiesReader.getProperty("validatePerson.sendPath"),
				SMOKE_TEST_FILE_VALIDATE_PERSON);
		validatePerson.validatePerson(VALIDATE_PERSON_DATA_SMOKE_TEST, "Smoke_validatePersonValidData");
		Assert.assertEquals(validatePerson.getResponseCode(), VALID_RESPONSE_CODE, "INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(validatePerson.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				FileUtils.returnFileContent(SMOKE_TESTS_EXPECTED_RESULTS_FILES, "validatePerson_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(), "INCORRECT RESPONSE MESSAGE");
	}

	//Smoke Test for service GetPersonNumber. Фамилия - Зуброва. Все данные валидны.
	@Test (dependsOnMethods = {"getPersonTestValidNumber", "validatePersonValidData"})
	public void getPersonNumberValidData() throws IOException, InterruptedException {
		getPersonNumber.openSession();
		String SMOKE_TEST_FILE_GET_PERSON_NUMBER = "get_person_number_smoke_test.txt";
		String GET_PERSON_NUMBER_DATA_SMOKE_TEST = FileUtils.returnFileContent(PropertiesReader.getProperty("getSalary.sendPath"), 
				SMOKE_TEST_FILE_GET_PERSON_NUMBER);
		getPersonNumber.getPersonNumberByData(GET_PERSON_NUMBER_DATA_SMOKE_TEST, "Smoke_getPersonNumberValidData");
		Assert.assertEquals(getPersonNumber.getResponseCode(), VALID_RESPONSE_CODE, "INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(getPersonNumber.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				FileUtils.returnFileContent(SMOKE_TESTS_EXPECTED_RESULTS_FILES,"getPersonNumber_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(), "INCORRECT RESPONSE MESSAGE");
	}

	//Smoke Test for service GetSalary. Фамилия - Зуброва. Все данные валидны.
	@Test (dependsOnMethods = {"getPersonTestValidNumber", "validatePersonValidData", "getPersonNumberValidData"})
	public void getSalaryValidData() throws IOException, InterruptedException {
		getSalary.openSession();
		String SMOKE_TEST_FILE_GET_SALARY= "get_salary_smoke_test.txt";
		String GET_SALARY_DATA_SMOKE_TEST = FileUtils.returnFileContent(PropertiesReader.getProperty("getSalary.sendPath"),
				SMOKE_TEST_FILE_GET_SALARY);
		getSalary.getPersonSalary(GET_SALARY_DATA_SMOKE_TEST, "Smoke_getSalaryValidData");
		Assert.assertEquals(getSalary.getResponseCode(), VALID_RESPONSE_CODE, "INCORRECT RESPONSE CODE IS APPEARED");
		Assert.assertEquals(getSalary.getResponseBodyText().replaceAll("\n", "").trim().toUpperCase(), 
				FileUtils.returnFileContent(SMOKE_TESTS_EXPECTED_RESULTS_FILES,"getSalary_smokeTest.txt").replaceAll("\r\n", "").trim().toUpperCase(), "INCORRECT RESPONSE MESSAGE");
	}
}
