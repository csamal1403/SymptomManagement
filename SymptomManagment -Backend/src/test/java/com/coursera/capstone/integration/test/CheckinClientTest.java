package com.coursera.capstone.integration.test;

import org.junit.Test;

import com.coursera.capstone.checkin.client.CheckinSvcApi;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

public class CheckinClientTest {

	
	
	private final String TEST_URL = "http://1.carbide-crowbar-749.appspot.com";
	//private final String TEST_URL = "http://localhost:8080";
	
	private CheckinSvcApi checkinService = new RestAdapter.Builder()
	.setEndpoint(TEST_URL)
	.setLogLevel(LogLevel.FULL)
	.build()
	.create(CheckinSvcApi.class);

	

	
	
	@Test
	public void testServer() throws Exception {
	
		
		
	}
	
	
  
	
	
}
