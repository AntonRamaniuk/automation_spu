package com.autotesting.framework.primaryFiles;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.autotesting.framework.testdata.PrimaryFilesTestData;
import com.autotesting.framework.utils.PropertiesReader;
import com.autotesting.framework.utils.DBConnection;


public class LoadPrimaryFilesDataBase {
	//private static DBConnection dbConnection = new DBConnection();
	
	//доступ на тестовую базу DBT1
	private static String DBT1_URL = PropertiesReader.getProperty("dataBaseDBT1url");
	private static String DBT1_LOGIN = PropertiesReader.getProperty("loginDBT1");
	private static String DBT1_PASSWORD = PropertiesReader.getProperty("passwordDBT1");
	
	//доступ на девелоперовскую базу DBD1
	private static String DBD1_URL = PropertiesReader.getProperty("dataBaseDBD1url");
	private static String DBD1_LOGIN = PropertiesReader.getProperty("loginDBD1");
	private static String DBD1_PASSWORD = PropertiesReader.getProperty("passwordDBD1");
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	private static String TEST_DATA_ZUBROVA_ADV1 = "zubrova_osnova.txt";
	private static PrimaryFilesTestData dbTestData = new PrimaryFilesTestData();
	private static String query = "INSERT INTO SPUMST.SPU_ACC(ACC_ID, ACC_STS, CLS_DOC_TP_ID, DOC_DT, OP_UNI) VALUES(1001041, 1, 3, CURRENT DATE, NEXT VALUE FOR SPUMST.SQ_CLC_OPER);";
	private static String person = "INSERT INTO SPUMST.SPU_PRSN(PRSN_SURNM, PRSN_NAME, PRSN_SECNM,PRSN_SURNM_E, PRSN_NAME_E, PRSN_SECNM_E,PRSN_SEX, PRSN_BRN_TP, PRSN_BRN, PRSN_BRN_DD, PRSN_BRN_MM, PRSN_BRN_YY,ACC_ID )VALUES('ЗУБРОВА', 'ИРИНА', 'НИКОЛАЕВНА', TRIM(REPLACE(REPLACE(UPPER('СМИРНОВА'), CAST('Ё' AS VARCHAR(255)), CAST('Е' AS VARCHAR(255))), CAST('-' AS VARCHAR(255)), CAST(' ' AS VARCHAR(255)))),TRIM(REPLACE(REPLACE(UPPER('ИРИНА'), CAST('Ё' AS VARCHAR(255)), CAST('Е' AS VARCHAR(255))), CAST('-' AS VARCHAR(255)), CAST(' ' AS VARCHAR(255)))),TRIM(REPLACE(REPLACE(UPPER('НИКОЛАЕВНА'), CAST('Ё' AS VARCHAR(255)), CAST('Е' AS VARCHAR(255))), CAST('-' AS VARCHAR(255)), CAST(' ' AS VARCHAR(255)))),2, 2,'1971-09-13', null,null, null, 1001041);  ";
	DBConnection dbConnection =null; 

	@BeforeSuite
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
	public void adv1Test () throws IOException {
		
		if (dbConnection==null) {
			getDBConnection();
		}
		dbConnection.manipulationQuery("select * from SPUMST.SPU_PRSN where PRSN_SURNM='ЗУБРОВА';");
		try {
			dbConnection.insertQuery(person);
			dbConnection.manipulationQuery("commit;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		logger.info("[TEST1] COMPLETED");
	}
	
} 
