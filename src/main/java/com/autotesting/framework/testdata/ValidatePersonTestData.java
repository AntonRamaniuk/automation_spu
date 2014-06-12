package com.autotesting.framework.testdata;

/**
 * 
 * Автор - Романюк Антон
 * Класс для вытягивания тестовых данных для сервиса ValidatePerson.
 * Все тестовые данные хранятся в файлах вида validatePersonTestData_numberOfTest.txt
 * Данные для каждого из тестов для сервиса ValidatePerson берутся аналогично пропертям, где имя проперти соотествует тесту,
 * 		значение проперети - тестовым данным для отправки
 * 
 * 
 * 
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.autotesting.framework.utils.PropertiesReader;


public class ValidatePersonTestData {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	private static final String TEST_DATA_FILE_PATH=PropertiesReader.getProperty("validatePerson.sendPath");

	
	//метод считывания информации из файла для тестовых данных. 
	//приходит имя - по этому имени выципляется файл для отправки как тестовые данные
    @SuppressWarnings("resource")
	public static String returnTestData(String nameOfFile) throws IOException{
    	FileInputStream inFile = new FileInputStream(new File(TEST_DATA_FILE_PATH+"/"+nameOfFile));
    	byte[] str = new byte[inFile.available()];
    	inFile.read(str);
    	String testData = new String(str,"UTF-8");
    	logger.info("[ACTION]: Test data for validate person service read from file "+nameOfFile+"with data: "+testData);
    	return testData; 
		
    	}
    	
    } 
	

