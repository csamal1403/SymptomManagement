package com.coursera.capstone.checkin.client;

import retrofit.http.GET;
import retrofit.http.Query;

import com.coursera.capstone.data.Token;



public interface TokenSvcApi {

	@GET("/oauth2/v1/tokeninfo")
	public Token getUserInfo(@Query("access_token") String token);
	
	
}
