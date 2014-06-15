package com.autotesting.framework.primaryFiles;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.autotesting.framework.utils.DBConnection;
import com.autotesting.framework.utils.FileUtils;
import com.autotesting.framework.utils.PropertiesReader;
import com.autotesting.framework.utils.RestConnect;

public class LoadPrimaryFilesRest {

	private static final String URL = PropertiesReader.getProperty("restSPU.url");
	private static final Logger logger = LoggerFactory.getLogger(LoadPrimaryFilesRest.class);
	private static final String TEST_DATA_FILE_PATH = PropertiesReader.getProperty("primaryFiles.sendPath");
	//доступ на тестовую базу DBT1
	private static String DBT1_URL = PropertiesReader.getProperty("dataBaseDBT1url");
	private static String DBT1_LOGIN = PropertiesReader.getProperty("loginDBT1");
	private static String DBT1_PASSWORD = PropertiesReader.getProperty("passwordDBT1");
	
	//доступ на девелоперовскую базу DBD1
	private static String DBD1_URL = PropertiesReader.getProperty("dataBaseDBD1url");
	private static String DBD1_LOGIN = PropertiesReader.getProperty("loginDBD1");
	private static String DBD1_PASSWORD = PropertiesReader.getProperty("passwordDBD1");
	//private static PrimaryFilesTestData dbTestData = new PrimaryFilesTestData();
	DBConnection dbConnection =null; 

	@BeforeMethod
	public DBConnection getDBConnection () {
		//переменная для выбора из проперти файла к какой базе будем коннектиться
		String dataBase = PropertiesReader.getProperty("dataBase");
		switch (dataBase) {
		case "DBT1": dbConnection = new DBConnection(DBT1_URL, DBT1_LOGIN, DBT1_PASSWORD);
					 break;
		case "DBD1": dbConnection = new DBConnection(DBD1_URL, DBD1_LOGIN, DBD1_PASSWORD);
					 break;
		}
		return dbConnection; 
	}
	
	@Test
	public void verifyPrimaryADV () throws IOException {
		
		if (dbConnection==null) {
			getDBConnection();
		}
		ResultSet count = dbConnection.selectQuery("select count(ACC_ID) from SPUMST.SPU_ACC where ACC_ID = 1001041;");
		Assert.assertEquals(count, 1, "ACC_ID = 1001041 note one in DB");

	}

	@Test (dependsOnMethods = {"verifyPrimaryADV"})
	public void LoadPrimaryFilesViaRest() {
		List<String> listOfPrimaryFiles = FileUtils.listFilesForFolder(PropertiesReader.getProperty("primaryFiles.sendPath"));
		for (String fileName : listOfPrimaryFiles) {
			try {
				String contentOfFile = FileUtils.returnFileContent(TEST_DATA_FILE_PATH, fileName);
				logger.info("[REST-PRIMARY]: Sending test data for primary files");
				RestConnect.sendPrimaryFile(URL, contentOfFile);
				logger.info("[REST-PRIMARY]: Sent test data for primary files");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
/*		
		while (itr.hasNext()) {
			try {
				String contentOfFile = FileUtils.returnFileContent(TEST_DATA_FILE_PATH, (String) itr.next());
				logger.info("[REST-PRIMARY]: Sending test data for primary files");
				RestConnect.sendPrimaryFile(URL, contentOfFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} */

	} 

}
