package com.autotesting.framework.utils;

/**
 * author: Ramaniuk Anton
 * Класс снятия скриншотов во время прохождения тестов
 * 
 * 
 */

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autotesting.framework.utils.WebDriverRunner;

public class Photogropher {
	private static Logger logger = LoggerFactory.getLogger(Photogropher.class);
	private static String SNAPSHOTS_FOLDER = PropertiesReader.getSnapshootPath();

	
	//метод снимает скриншот. входные данные - имя теста и имя сервиса. обращется к методу getSnapshotName для формирования имени файла
	public static void doScreenshot(String caseName, String serviceName) {
		logger.info("Getting a snapshoot");
		String filename = getSnapshotName(caseName, serviceName) + ".png";

		WebDriver driver = WebDriverRunner.getWDR().getDriver();

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(filename));
		} catch (IOException e) {
			logger.error(String.format("Error copy screenshot file from %s to %s",scrFile.getAbsolutePath(), filename));
		}
	}

	public static String getSnapshotName(String driverCommand, String serviceName) {

		File folder = new File(SNAPSHOTS_FOLDER+"/"+serviceName);
		if (!folder.exists()) {
			try {
				folder.mkdirs();
			} catch (Exception e) {
				logger.error("Cannot create folder", e);
			}
		}
		String timestamp = nowAsString("yyyyMMdd_HHmmss");
		return folder.getAbsolutePath() + "/" + driverCommand +"_"+ timestamp;
	}

	public static String nowAsString(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
}