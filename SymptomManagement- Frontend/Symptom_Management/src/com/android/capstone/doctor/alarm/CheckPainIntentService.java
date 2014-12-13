package com.android.capstone.doctor.alarm;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.capstone.MainActivity;
import com.android.capstone.R;
import com.android.capstone.data.Checkin;
import com.android.capstone.data.VPatient;
import com.android.capstone.db.SymptomManagementContract.UserInfoEntry;
import com.android.capstone.utils.CommonUtils;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;




public class CheckPainIntentService extends IntentService {
    
	
	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	private CommonUtils commonUtils;
	
	
	private final int CANT_EAT_TIME = 720;  //12 hrs
	private final int MODERATE_PAIN_TIME = 960;  //16 hrs
	private final int SEVERE_PAIN_TIME = 720; //12 hrs
	private boolean toNotify = false;
	
	   
    ArrayList<Long> cantEatList, moderatePainList, cantEatAndModeratePainList, severePainList, cantEatAndSeverePainList ;
    
    
	
	public CheckPainIntentService() {
		super("CheckPainIntentService");
		cantEatList = new ArrayList<Long>();
		moderatePainList = new ArrayList<Long>();
		cantEatAndModeratePainList = new ArrayList<Long>();
		severePainList = new ArrayList<Long>();
		cantEatAndSeverePainList = new ArrayList<Long>();
	}

	
	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		 commonUtils = new CommonUtils(this);		 
		 HashMap<Long,ArrayList<Checkin>> allCheckins = commonUtils.getAllCheckins();
		 		 
		 for (Long patientId : allCheckins.keySet()) {
		      
			 ArrayList<Checkin> checkins = allCheckins.get(patientId);
		     CheckPain checkPain= new CheckPain();
		     
		     String finalPainStatus = "", finalCantEatStatus = "";
		     
		     for(Checkin checkin : checkins){
		    	 checkPain.checkPainStatus(checkin);
		    	 checkPain.checkEatingStatus(checkin);
		     }	 
		
		     
             if(checkPain.getCantEatStatus().equals(CommonUtils.ANSWER_CANT_EAT)){
            	 int diff = (int) (checkPain.getTotalCantEatTime()/(60*1000));
                 if(diff > CANT_EAT_TIME){
		    		 finalCantEatStatus = CommonUtils.ANSWER_CANT_EAT;
		    	 }
		     }
		     
             
		     if(checkPain.getPainStatus().equals(CommonUtils.ANSWER_MODERATE)){
		    	 int diff = (int) (checkPain.getTotalPainTime()/(60*1000)); 
		    	 if(diff > MODERATE_PAIN_TIME){
		    		finalPainStatus = CommonUtils.ANSWER_MODERATE;
		    	 }
		    	 
		     }else if(checkPain.getPainStatus().equals(CommonUtils.ANSWER_SEVERE)){
		    	 int diff = (int) (checkPain.getTotalPainTime()/(60*1000));
		    	 if(diff > SEVERE_PAIN_TIME){
		    	    finalPainStatus = CommonUtils.ANSWER_SEVERE;
			     }
		    }
		     
		     
		     if(!(finalPainStatus.equals("") && finalCantEatStatus.equals(""))){
		         toNotify = true;   
		     }
		     
		     
		     
		     VPatient p = commonUtils.getPatientInfo(patientId);
	         ContentValues cv = new ContentValues();
		     cv.put(UserInfoEntry._ID, p.getPatientId());
		     cv.put(UserInfoEntry.COLUMN_EMAIL_ID, p.getPatientEmailId());
		     cv.put(UserInfoEntry.COLUMN_FIRST_NAME, p.getFirstName());
		     cv.put(UserInfoEntry.COLUMN_LAST_NAME, p.getLastName());
		     cv.put(UserInfoEntry.COLUMN_ABOUT, p.getAbout());
		     cv.put(UserInfoEntry.COLUMN_PICTURE_URL, p.getPictureUrl());
		     cv.put(UserInfoEntry.COLUMN_BIRTH_DATE, p.getBirthDate().getTime());
		     cv.put(UserInfoEntry.COLUMN_STATUS_PAIN, checkPain.getPainStatus());
		     cv.put(UserInfoEntry.COLUMN_STATUS_CANT_EAT, checkPain.getCantEatStatus());
		     cv.put(UserInfoEntry.COLUMN_LAST_CHECKED, System.currentTimeMillis());
		     
		     Uri uri = getContentResolver().insert(UserInfoEntry.CONTENT_URI, cv);
		     
		 }
		
	
		 
		 if(toNotify){
		    sendNotification("Check Your Patients Now ");
		 }
		 
		 
	}




	
	
	
	 private void sendNotification(String msg) {
		 
		 Intent resultIntent = new Intent(this, MainActivity.class);
		 TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		 stackBuilder.addParentStack(MainActivity.class); 
		 stackBuilder.addNextIntent(resultIntent);
		 PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		 
	        mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
	 
	        Notification.Builder mBuilder = new Notification.Builder(this)
	             .setSmallIcon(R.drawable.ic_action_cloud_upload)
	             .setContentTitle("Check Patients Now...") 
	             .setContentIntent(resultPendingIntent)
	             .setStyle(new Notification.BigTextStyle().bigText(msg))
	             .setContentText(msg);
	 
	        Notification notify = mBuilder.build();
		    notify.flags = Notification.FLAG_AUTO_CANCEL;
		    notify.defaults = Notification.DEFAULT_ALL;
		      	        
	        mNotificationManager.notify(NOTIFICATION_ID, notify);
	        
	        
	    } 
	
	
	
	
	
}
