package com.autotesting.framework.testdata;


/**
 * author: Ramaniuk Anton
 * Класс для считывания данных из тестовых файлов для сервиса Get Salary.
 * Считанные данные будут использоваться для отправки либо на свагер либо на рест сервис
 * 
 */

import java.io.FileInputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.autotesting.framework.utils.PropertiesReader;


public class GetSalaryTestData {

	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	private static final String TEST_DATA_FILE_PATH=PropertiesReader.getProperty("getSalary.sendPath");

	
	//метод считывания информации из файла для тестовых данных. 
	//приходит имя - по этому имени выципляется файл для отправки как тестовые данные
    @SuppressWarnings("resource")
	public static String returnTestData(String nameOfFile) throws IOException{
    	System.out.println("TEST_DATA_FILE_PATH: "+TEST_DATA_FILE_PATH);
    	FileInputStream inFile = new FileInputStream(TEST_DATA_FILE_PATH+"/"+nameOfFile);
    	byte[] str = new byte[inFile.available()];
    	inFile.read(str);
    	String testData = new String(str);
    	logger.info("[ACTION]: Test data for GET SALARY service read from file "+nameOfFile+"with data: "+str);
    	return testData;
    }
	
}
