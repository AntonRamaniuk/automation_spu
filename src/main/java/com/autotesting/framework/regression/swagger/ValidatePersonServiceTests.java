package com.autotesting.framework.regression.swagger;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.autotesting.framework.screens.GetPersonServicePage;
import com.autotesting.framework.screens.ValidatePersonServicePage;
import com.autotesting.framework.testdata.GetPersonTestData;

public class ValidatePersonServiceTests {
	ValidatePersonServicePage validatePerson = new ValidatePersonServicePage();
	private static final String VALID_RESPONSE_CODE = "200";
	//private 

}
