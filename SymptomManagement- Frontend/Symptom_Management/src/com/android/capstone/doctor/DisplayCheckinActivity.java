package com.android.capstone.doctor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.android.capstone.BaseActivity;
import com.android.capstone.R;
import com.android.capstone.data.Checkin;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.SharedPrefUtils;
import com.android.capstone.views.CheckinLogsView;

import android.os.Bundle;
import android.util.Log;

public class DisplayCheckinActivity extends BaseActivity {


	private Long doctorId;
	private Long patientId;
	private String date;
	private ArrayList<Checkin> checkins;
	
	CheckinLogsView view ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doctorId = getIntent().getLongExtra("doctorId", 0);
		patientId = getIntent().getLongExtra("patientId", 0);
		
		date = getIntent().getStringExtra("date");
        checkins = new ArrayList<Checkin>();
		view = new CheckinLogsView(getApplicationContext(), null);
		
		setContentView(view);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		loadCheckinsByDate();
	
	}
	
	
	
	
   private void loadCheckinsByDate(){
		
	   ArrayList<Checkin> result;
	   if(new SharedPrefUtils(this).isDoctor()){
		  
		  result = new CommonUtils(this).getCheckinsById(doctorId, patientId, "ASC");
	   }else{
		   result = new CommonUtils(this).getCheckinsById(patientId, doctorId, "ASC"); 
	   }
	   
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");

		
		try {
			Date checkinDate = sdf.parse(date);

			Calendar cal = Calendar.getInstance();
			cal.setTime(checkinDate);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			
			for(Checkin checkin : result){
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(checkin.getCheckinDate());
				int day1 = cal.get(Calendar.DAY_OF_MONTH);
				int month1 = cal.get(Calendar.MONTH);
				int year1 = cal.get(Calendar.YEAR);
				
				if(day == day1 && month == month1 && year == year1){
				   checkins.add(checkin);
				}
				
			}
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		view.setCheckins(checkins);
	
		
	}
	
	
	
	
	
   
    @Override
    public void finish() {
    	super.finish();
    	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    	
    }
   
   
   
   
   
	
}
