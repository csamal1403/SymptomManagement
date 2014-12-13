package com.coursera.capstone.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

import com.coursera.capstone.checkin.client.TokenSvcApi;
import com.coursera.capstone.data.Token;



@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	private final String TOKEN_ENDPOINT = "https://www.googleapis.com";
	public static final String CLIENT_ID_ANDROID = "992384145132-mbvd13690rr56vu15iugq1npbd4kkkmi.apps.googleusercontent.com";
	public static final String AUTH_HEADER = "Authorization";
	
	
	
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String tokenString = request.getHeader(AUTH_HEADER);
		
		TokenSvcApi tokenService = new RestAdapter.Builder().setEndpoint(TOKEN_ENDPOINT)
                                   .setLogLevel(LogLevel.FULL)
                                    .build().create(TokenSvcApi.class);
		
		Token userInfo = tokenService.getUserInfo(tokenString);
		
		if(userInfo.getAudience().equals(CLIENT_ID_ANDROID)){
			request.setAttribute("email", userInfo.getEmail());
			return true;
		
		}else{
			return false;
		}
		
		
		
		
		
	}
	
	
	
}
