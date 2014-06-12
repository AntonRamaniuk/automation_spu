package com.autotesting.framework.testdata;

/**
 * 
 * Автор - Романюк Антон
 * Класс для вытягивания тестовых данных для сервиса GetPersonNumber.
 * Все тестовые данные хранятся в файлах вида getPersonNumberTestData_numberOfTest.txt
 * Данные для каждого из тестов для сервиса GetPersonNumber берутся аналогично пропертям, где имя проперти соотествует тесту,
 * 		значение проперети - тестовым данным для отправки
 * 
 * 
 * 
 */

import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autotesting.framework.utils.PropertiesReader;


public class GetPersonNumberTestData {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	private static final String TEST_DATA_FILE_PATH=PropertiesReader.getProperty("getNumber.sendPath");
	
	//метод считывания информации из файла для тестовых данных. 
	//приходит имя - по этому имени выципляется файл для отправки как тестовые данные
    @SuppressWarnings("resource")
	public static String returnTestData(String nameOfFile) throws IOException{
    	System.out.println("TEST_DATA_FILE_PATH: "+TEST_DATA_FILE_PATH);
    	FileInputStream inFile = new FileInputStream(TEST_DATA_FILE_PATH+"/"+nameOfFile);
    	byte[] str = new byte[inFile.available()];
    	inFile.read(str);
    	String testData = new String(str);
    	logger.info("[ACTION]: Test data for get person number service read from file "+nameOfFile+"with data: "+str);
    	return testData;
    }

}
