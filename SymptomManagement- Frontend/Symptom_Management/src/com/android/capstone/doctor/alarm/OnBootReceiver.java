package com.android.capstone.doctor.alarm;

import java.util.ArrayList;
import java.util.Date;

import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.SharedPrefUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class OnBootReceiver extends BroadcastReceiver {

	
	
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	
	
	
	@Override
	public void onReceive(Context context, Intent receivedIntent) {
		boolean isDoctor = new SharedPrefUtils(context).isDoctor();
		     
		if(isDoctor){
		   //Start Alarms at Regular Intervals to Start the CheckPainIntentService ....
		   alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		   Intent intent = new Intent(context, CheckPainIntentService.class);
		   alarmIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		   alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60*1000, alarmIntent);
       
		}else{
			ArrayList<Date> reminders = new CommonUtils(context).getReminders();
			
			for(Date reminder : reminders){
				 int reqCode = (int) reminder.getTime();
				 Intent intent = new Intent(context, CheckinAlarmService.class);
				 PendingIntent alarmIntent = PendingIntent.getService(context, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				 alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, reminder.getTime(), AlarmManager.INTERVAL_DAY, alarmIntent);
				
			}
			
		}
		
		
	}

	
	
	
	
	
	
}
