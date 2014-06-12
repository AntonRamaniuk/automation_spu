package com.autotesting.framework.utils;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/** 
 *         Класс для доступа к базе данных, лучше ничего не менять.
 * 		   Реализован по паттерну Singleton
 * 
 */

public class DBConnection {
	
	private static DBConnection instanse = null;
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	private Connection connection = null;
	
	

	public DBConnection(String location, String userName, String password) {		
			
		
		
			logger.info(String.format("[DATABASE]: Connecting to database: host %s ...",location));
		try {
			//DriverManager.registerDriver(driver);
			connection = DriverManager.getConnection("jdbc:db2://" + location, userName, password);
			connection.setAutoCommit(true);
			logger.info("[DATABASE]: Connected to DB successfully");
		} catch (SQLException e) {
			logger.error("[DATABASE]: Cannot create database connection", e);
		}

	}

	public static synchronized DBConnection getConnection(String location, String userName, String password) {

		if (instanse == null) {
			instanse =  new DBConnection(location, userName, password);
		}

		return instanse;
	}

	public void manipulationQuery(String query) {

		Statement stmt;
		try {
			logger.info(String.format("[DATABASE]: Sending SQL request to database, query: %s ...",query));
			stmt = connection.createStatement();
			stmt.execute(query);			
		} catch (SQLException e) {
			logger.error("[DATABASE]: Exception at sql query", e);
		}

	}
	
	public void insertQuery(String query) throws SQLException{
		Statement stmt = connection.createStatement(); 	 
		String sqlInsert = query;
	         System.out.println("The SQL query is: " + sqlInsert);  // Echo for debugging
	         int countInserted = 0;
			try {
				countInserted = stmt.executeUpdate(sqlInsert);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         System.out.println(countInserted + " records inserted.\n");
	}

	public ResultSet selectQuery(String query) {

		try {
			
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			logger.info(String.format("[DATABASE]: Sending SQL request to database, query: %s",query));
			ResultSet rs = stmt.executeQuery(query);
			logger.info("[DATABASE]: Getting response");
			return rs;
		} catch (SQLException e) {
			logger.error("[DATABASE]: Exception at sql query", e);
		}

		return null;

	}

	public void close() throws Exception {
		if (connection != null) {
			try {
				connection.commit();
				connection.close();
			} catch (Exception e) {
				logger.error("[DATABASE]: Cannot close connection", e);
			}
			connection = null;
		}
	}
}
