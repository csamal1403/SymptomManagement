package com.android.capstone.gcm;

import java.util.ArrayList;

import com.android.capstone.data.Checkin;
import com.android.capstone.data.Medications;
import com.android.capstone.data.VDoctor;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.RetrofitUtils;
import com.android.capstone.utils.SharedPrefUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;




public class GcmIntentService extends IntentService {
	
	    public GcmIntentService() { 
	        super("GcmIntentService"); 
	    } 
	 
	    
	    
	    @Override 
	    protected void onHandleIntent(Intent intent) {
	        
	    	SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(getApplicationContext());
	    	CommonUtils commonUtils = new CommonUtils(getApplicationContext());
	    	RetrofitUtils retrofitUtils = new RetrofitUtils(this);
	    	
	    	Bundle extras = intent.getExtras();
	        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
	        String messageType = gcm.getMessageType(intent);
	 
	        if (!extras.isEmpty()) { 
	            
	            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
	                
	            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
	                
	            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
	                
	            	for(String updateKey : extras.keySet()){
	                	
	                	if(updateKey.equals("medications")){
		                	
		                	//Patient received this update ..
		                	Long doctorId = Long.parseLong(extras.getString("medications"));
		                	Medications med = retrofitUtils.getMedications(doctorId, sharedPrefUtils.getId());
		                	commonUtils.saveMedications(med, false);
		                	
		                }else if(updateKey.equals("checkins")){
		                	
		                	//Doctor received this update ..
		                	Long patientId = Long.parseLong(extras.getString("checkins"));
		                	ArrayList<Checkin> checkins = retrofitUtils.getCheckins(sharedPrefUtils.getId(), patientId);
		                	commonUtils.saveCheckins(checkins, true);
		                	
		                }else if(updateKey.equals("patient")){
		                	
		                	//Patient received this update ..
		                	ArrayList<VDoctor> doctorInfos = retrofitUtils.getDoctorInfos(sharedPrefUtils.getId());
		                	commonUtils.saveDoctorInfos(doctorInfos);
		                	
		               }
	                }
	            } 
	        } 
	        
	        
	        GcmBroadcastReceiver.completeWakefulIntent(intent);
	        
	    } 
	
}
