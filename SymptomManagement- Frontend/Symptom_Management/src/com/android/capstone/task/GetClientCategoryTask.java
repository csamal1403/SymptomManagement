package com.android.capstone.task;

import java.io.IOException;

import com.android.capstone.MainActivity;
import com.android.capstone.data.CheckinSvcApi;
import com.android.capstone.data.GPlusProfile;
import com.android.capstone.utils.RetrofitUtils;
import com.android.capstone.utils.SharedPrefUtils;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import android.app.ProgressDialog;
import android.os.AsyncTask;


public class GetClientCategoryTask extends AsyncTask<Void, Void , String> {

	
	private MainActivity activity;
	private ProgressDialog dialog;
	boolean tokenSaved;
	
	
	public GetClientCategoryTask(MainActivity activity) {
		super();
		this.activity = activity;
		tokenSaved = false;
	}

	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(activity, "Getting UserInfo ", "Please Wait... ", true);
	}
	
	
	
	
	protected String doInBackground(Void... params) {
		
		SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(activity);
		String email = sharedPrefUtils.getEmail();
		
		try { 
            String token = fetchToken(email);
            if (token != null) {
                new SharedPrefUtils(activity).setAccessToken(token); //Getting Refresh Token is more pratcical..
                tokenSaved = true;
            } 
            
        } catch (IOException e) {
            
        } 
		
		if(tokenSaved){
			
			RetrofitUtils retrofitUtils =  new RetrofitUtils(activity);
			GPlusProfile clientProfile = retrofitUtils.getClientGPlusProfile();
			sharedPrefUtils.setName(clientProfile.getName());
			sharedPrefUtils.setFirstName(clientProfile.getGiven_name());
			sharedPrefUtils.setLastName(clientProfile.getFamily_name());
			sharedPrefUtils.setGender(clientProfile.getGender());
		    sharedPrefUtils.setImageUrl(clientProfile.getPicture());
			
		    return new RetrofitUtils(activity).getClientCategory();
		
		}else{
			return null;
		}
		
	}





	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		dialog.dismiss();
		
		if(activity != null && tokenSaved && (result != null)){
			activity.getClientCategoryTaskResult(result);
		}
		
	}
	
	
	
	
	
	
	
	
	
	private String fetchToken(String email) throws IOException {
        try { 
        	String scopes = "oauth2:" + CheckinSvcApi.EMAIL_SCOPE;
            return GoogleAuthUtil.getToken(activity.getApplicationContext(), email, scopes);
            
        } catch (UserRecoverableAuthException userRecoverableException) {
        	activity.handleException(userRecoverableException);
        	
        	
        } catch (GoogleAuthException fatalException) {
        	
        } 
        return null; 
    }
	
	
	
	

}
