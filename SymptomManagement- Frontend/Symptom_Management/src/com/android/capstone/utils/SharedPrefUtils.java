package com.android.capstone.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtils {

	
	SharedPreferences sharedPref;
	SharedPreferences.Editor editor;
	
	
	
	public SharedPrefUtils(Context context){
		sharedPref = context.getSharedPreferences("symptom", Context.MODE_PRIVATE);
	}
	
	
	
	

	
	public Long getId(){
		return sharedPref.getLong("Id", 0);
	}
	
	
	public boolean isDoctor(){
		return sharedPref.getBoolean("isDoctor", false);
	}
	

	
	public String getEmail(){
		return sharedPref.getString("email", "");
	}
	
	
	public String getAccessToken(){
		return sharedPref.getString("token", "");
	}
	
	
	public String getGCMRegId(){
		return sharedPref.getString("GcmRegId", "");
	}
	
	
	public String getName(){
		return sharedPref.getString("name", "");
	}
	
	public String getFirstName(){
		return sharedPref.getString("first_name", "");
	}
	
	public String getLastName(){
		return sharedPref.getString("last_name", "");
	}
	
	public String getGender(){
		return sharedPref.getString("gender", "");
	}
	
	public String getImageUrl(){
		return sharedPref.getString("image_url", "");
	}
	
	
	
	
	public void setFirstName(String firstName){
		editor = sharedPref.edit();
		editor.putString("first_name", firstName);
        editor.commit();
	}
	
	public void setLastName(String lastName){
		editor = sharedPref.edit();
		editor.putString("last_name", lastName);
        editor.commit();
	}
	
	
	
	
	public void setName(String name){
		editor = sharedPref.edit();
		editor.putString("name", name);
        editor.commit();
	}
	
	
	public void setGender(String gender){
		editor = sharedPref.edit();
		editor.putString("gender", gender);
        editor.commit();
	}
	
	public void setImageUrl(String imageUrl){
		editor = sharedPref.edit();
		editor.putString("image_url", imageUrl);
        editor.commit();
	}
	
	
	public void setAccessToken(String tokenString){
		editor = sharedPref.edit();
		editor.putString("token", tokenString);
        editor.commit();
	}
	
	
	
	
	public void setGCMRegId(String gcmRegId){
		editor = sharedPref.edit();
		editor.putString("GcmRegId", gcmRegId);
        editor.commit();
	}
	
	
	public void setClientId(Long id){
		editor = sharedPref.edit();
		editor.putLong("Id", id);
        editor.commit();
	}
	
	
	public void setDoctor(boolean isDoctor){
		editor = sharedPref.edit();
		editor.putBoolean("isDoctor", isDoctor);
		editor.commit();
    }
	
	
	public void setEmail(String mEmail){
		editor = sharedPref.edit();
		editor.putString("email", mEmail);
        editor.commit();
	}
	
}
