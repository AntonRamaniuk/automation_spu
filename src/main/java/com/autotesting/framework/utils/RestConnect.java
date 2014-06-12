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

import com.autotesting.framework.testdata.ValidatePersonTestData;


public class RestConnect {
	
	private static HttpClient httpClient = new DefaultHttpClient();
	private static Logger logger = LoggerFactory.getLogger(RestConnect.class);
	
	
	//метод коннекта к рест сервису для скмв с хардкодным хедэром
	public static StringBuffer getResponseContentJson (String url, String content) throws Exception {
		StringBuffer responseContent = new StringBuffer();
		logger.info("[REST]: Content for request is obtained: "+content);
		try {
	        HttpPost request = new HttpPost(url);

	        StringEntity params =new StringEntity(content, "application/json", "UTF-8");
	        request.addHeader("Content-Type", "application/json; charset=UTF-8");
	        request.setHeader("Accept", "application/json");
	        //request.addHeader("Accept-Language", "	ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3");
	        request.setEntity(params);        
	        logger.info("[REST]: request to rest-skmv is sent witn contet: "+content);
	        HttpResponse response = httpClient.execute(request);
	        logger.info("[REST]: Response code obtained: "+response);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				responseContent.append(line);
				logger.info("[REST]: Response content obtained: "+responseContent);
			}
	    }catch (Exception ex) {
	    	ex.printStackTrace();
	    	logger.error("[REST]: ERROR: "+ex);
	    } finally {
	        httpClient.getConnectionManager().shutdown();
	        logger.info("[REST]: Connection to rest is closed");
	    }
		return responseContent;
	}
	
	

	 
	public static void main(String[] args) throws Exception {
		String resultFileUTF8 = ValidatePersonTestData.returnTestData("validate_person_smoke_test.txt");
		StringBuffer test=  RestConnect.getResponseContentJson("http://192.168.5.77:9080/spu-interfaces/api/person/validatePerson", resultFileUTF8);
		System.out.println(test);
	} 
}


