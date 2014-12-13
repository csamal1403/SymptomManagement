package com.android.capstone.doctor.alarm;

import com.android.capstone.MainActivity;
import com.android.capstone.R;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

public class CheckinAlarmService extends IntentService {

	
	public static final int NOTIFICATION_ID = 2;
	private NotificationManager mNotificationManager;
	
	
	public CheckinAlarmService() {
		super("CheckinAlarmService");
	}
	
	
	
	@Override
	protected void onHandleIntent(Intent intent) {
		sendNotification("Its time to Checkin...");
		
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
	             .setContentIntent(resultPendingIntent)
	             .setContentTitle("Checkin Now...") 
	             .setStyle(new Notification.BigTextStyle().bigText(msg))
	             .setContentText(msg);
	             
	 
	       Notification notify = mBuilder.build();
	       notify.flags = Notification.FLAG_AUTO_CANCEL;
	       notify.defaults = Notification.DEFAULT_ALL;
	       
	       mNotificationManager.notify(NOTIFICATION_ID, notify);
	        
	        
	    } 
	
	
	
	
	
	
	
}
