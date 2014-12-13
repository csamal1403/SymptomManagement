package com.android.capstone.patient;

import java.util.Calendar;
import java.util.Date;

import com.android.capstone.BaseActivity;
import com.android.capstone.R;
import com.android.capstone.db.SymptomManagementContract.RemindersEntry;
import com.android.capstone.doctor.alarm.CheckinAlarmService;
import com.android.capstone.patient.TimePickerFragment.OnTimeSelectedListener;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.views.FloatingActionButton;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;




public class RemindersActivity extends BaseActivity implements OnTimeSelectedListener, LoaderCallbacks<Cursor>{
    FloatingActionButton plusFabButton;
	ReminderCursorAdapter mAdapter;
	ListView remindersList;
	AlarmManager alarmMgr;
	CommonUtils utils;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_reminders);
		alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		utils = new CommonUtils(this);
		mAdapter = new ReminderCursorAdapter(this, null, 0);
		remindersList = (ListView)findViewById(R.id.remindersList);
		remindersList.setAdapter(mAdapter);
		
		showReminderToast();
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getLoaderManager().initLoader(0, null, this);	
		
		createPlusFabButton();
		
	}
	
	
	 @Override
	 protected void onResume() {
	    super.onResume();
	     getLoaderManager().restartLoader(0, null, this);
    }
	 
	
	
	
	
	private void createPlusFabButton() {
	     plusFabButton = new FloatingActionButton.Builder(this)
		           .withDrawable(getResources().getDrawable(R.drawable.ic_content_add))
		           .withButtonColor(getResources().getColor(R.color.theme_primary))
		           .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
		           .withMargins(0, 0, 25, 25)
		           .create();
		
	    plusFabButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				 DialogFragment newFragment = new TimePickerFragment(" Reminder Time "); 
				 newFragment.show(getFragmentManager(), "timePicker"); 
				
			}
		});
	     
	     
	 }
	
	
	
	
	
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(
                this,   // context
                RemindersEntry.CONTENT_URI,        // Table to query
                null,     // Projection to return
                null,            // No selection clause
                null,            // No selection arguments
                null             // Default sort order
          );
	}




	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		mAdapter.swapCursor(cursor);
		
	}





	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}


	@Override
	public void onTimeSelected(int hourOfDay, int minute) {
		 Calendar cal = Calendar.getInstance();
		 cal.setTimeInMillis(System.currentTimeMillis());
		 cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
	     cal.set(Calendar.MINUTE, minute);
	     Date date = cal.getTime();
	   	 
	     utils.saveReminder(date);
	    
	     showReminderToast();
	    
	   	 int reqCode = (int) date.getTime();
		 Intent intent = new Intent(this, CheckinAlarmService.class);
		 PendingIntent alarmIntent = PendingIntent.getService(this, reqCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		 alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, date.getTime(), AlarmManager.INTERVAL_DAY, alarmIntent);
		
		
	}


	private void showReminderToast() {
		int remindersCount = new CommonUtils(this).getRemindersCount();
	    	int need = 4 - remindersCount;
	    	
	    	if(remindersCount < 4){
	    		Toast.makeText(this, "Add " + need + " more Reminders ", Toast.LENGTH_SHORT).show();
	    	}
	} 
	
	
	
	
	@Override
	public void onBackPressed() {
		int remindersCount = new CommonUtils(this).getRemindersCount();
    	int need = 4 - remindersCount;
    	
    	if(remindersCount < 4){
    		Toast.makeText(this, "Add " + need + " more Reminders ", Toast.LENGTH_LONG).show();
    	}else{
    		finish();
    	}
	}
	
	
	
	
	 
	
	
	@Override
     public void finish() {
    	super.finish();
    	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    	
   }
   
	
	
	

	
	
}
