package com.autotesting.framework.utils;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestConnect {
	
	private static HttpClient httpClient = new DefaultHttpClient();
	private static Logger logger = LoggerFactory.getLogger(RestConnect.class);
	
	
	//метод коннекта к рест сервису для скмв с хардкодным хедэром
	public static StringBuffer getResponseContentJson (String url, String content) throws Exception {
		StringBuffer responseContent = new StringBuffer();
		logger.info("[REST-SKMV]: Content for request is obtained: "+content);
		try {
	        HttpPost request = new HttpPost(url);

	        StringEntity params =new StringEntity(content, "application/json", "UTF-8");
	        request.addHeader("Content-Type", "application/json; charset=UTF-8");
	        request.setHeader("Accept", "application/json");
	        request.setEntity(params);        
	        logger.info("[REST-SKMV]: request to rest-skmv is sent witn contet: "+content);
	        HttpResponse response = httpClient.execute(request);
	        logger.info("[REST-SKMV]: Response code obtained: "+response);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseContent.append(line);
				logger.info("[REST-SKMV]: Response content obtained: "+responseContent);
			}
	    }catch (Exception ex) {
	    	ex.printStackTrace();
	    	logger.error("[REST-SKMV]: ERROR: "+ex);
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	        logger.info("[REST-SKMV]: Connection to rest is closed");
	    }
		return responseContent;
	}
	
	
	public static StringBuffer sendPrimaryFile (String url, String content) throws Exception {
		StringBuffer responseContent = new StringBuffer();
		logger.info("[REST-PRIMARY]: Content for request is obtained: "+content);
		try {
	        HttpPost request = new HttpPost(url);

	        StringEntity params =new StringEntity(content, "text/html", "UTF-8");
	        request.addHeader("Content-Type", "text/html; charset=UTF-8");
	        request.setEntity(params);        
	        logger.info("[REST-PRIMARY]: request to rest-skmv is sent witn contet: "+content);
	        HttpResponse response = httpClient.execute(request);
	        logger.info("[REST-PRIMARY]: Response code obtained: "+response);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseContent.append(line);
				logger.info("[REST-PRIMARY]: Response content obtained: "+responseContent);
			}
	    }catch (Exception ex) {
	    	ex.printStackTrace();
	    	logger.error("[REST-PRIMARY]: ERROR: "+ex);
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	        logger.info("[REST-PRIMARY]: Connection to rest is closed");
	    }
		return responseContent;
	}
		
}


