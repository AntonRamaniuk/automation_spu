package com.autotesting.framework.testdata;

/**
 * 
 * Автор - Романюк Антон
 * Класс для вытягивания тестовых данных для сервиса GetPerson.
 * Все тестовые данные хранятся в одном файле getPersonTestData.txt
 * Данные для каждого из тестов для сервиса GetPerson берутся аналогично пропертям, где имя проперти соотествует тесту,
 * 		значение проперети - тестовым данным для отправки
 * 
 * 
 * 
 */

import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.autotesting.framework.utils.PropertiesReader;

public class GetPersonTestData {
	private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);
	private static final String TEST_DATA_FILE_PATH = PropertiesReader.getProperty("getPerson.sendNumberPath")+"/getPersonTestData.txt";
	private static Properties TEST_DATA_GET_PERSON;

	static {
		TEST_DATA_GET_PERSON = new Properties();
		try {
			TEST_DATA_GET_PERSON.load(ClassLoader.getSystemClassLoader().getResourceAsStream(TEST_DATA_FILE_PATH));
			logger.info("FILE WITH TEST DATA FOR GET PERSON SERVICE IS OPENED AND READ:"+TEST_DATA_GET_PERSON);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// берет конфиги по ключу
	public static String getProperty(String key) {
		String propertyValue = TEST_DATA_GET_PERSON.getProperty(key);
		return propertyValue;
	}
	
	// берет конфиги по ключу для интовых значения
		public static int getIntProperty(String intKey) {
			String propertyStrValue = TEST_DATA_GET_PERSON.getProperty(intKey);
			int propertyIntValue = Integer.parseInt(propertyStrValue);
			return propertyIntValue;
		}

}
