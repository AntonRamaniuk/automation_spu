package com.autotesting.framework.testdata;

/**
 * author: Ramaniuk Anton
 * Класс для считывания данных из файлов для первичной загрузки (или xml или txt или sql)
 * 
 * 
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autotesting.framework.utils.PropertiesReader;

public class PrimaryFilesTestData {
	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	private static final String TEST_DATA_DB_FILE_PATH=PropertiesReader.getProperty("primaryFilesDB.sendPath");
	
	//метод считывания информации из файла для тестовых данных. 
	//приходит имя - по этому имени выципляется файл для отправки как тестовые данные
    @SuppressWarnings("resource")
	public static String returnTestDataDB(String nameOfFile) throws IOException{
    	FileInputStream inFile = new FileInputStream(TEST_DATA_DB_FILE_PATH+"/"+nameOfFile);
    	byte[] str = new byte[inFile.available()];
    	inFile.read(str);
    	//String testData = new String(str);
    	//logger.info("[ACTION]: Test data for validate person service read from file "+nameOfFile+"with data: "+str);
    	
    	String testDataBeforeEncoding = new String(str);
    	byte[] resultByteFileUTF8 = testDataBeforeEncoding.getBytes("UTF8");
		String resultFileUTF8 = new String(resultByteFileUTF8, "UTF8");
		PrintStream out = new PrintStream(System.out, true, "UTF-8");
		out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+resultFileUTF8);
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+resultFileUTF8);
    	return resultFileUTF8;
    }

}
