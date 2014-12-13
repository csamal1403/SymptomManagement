package com.android.capstone;

import java.util.Calendar;
import java.util.Date;

import com.android.capstone.DatePickerFragment.OnDateSelectedListener;
import com.android.capstone.data.CheckinSvcApi;
import com.android.capstone.data.Doctor;
import com.android.capstone.data.Patient;
import com.android.capstone.doctor.DoctorMain_Fragment;
import com.android.capstone.doctor.alarm.CheckPainIntentService;
import com.android.capstone.patient.PatientMain_Fragment;
import com.android.capstone.task.GetClientCategoryTask;
import com.android.capstone.task.GetClientInfoTask;
import com.android.capstone.task.RegisterDoctorTask;
import com.android.capstone.task.RegisterPatientTask;
import com.android.capstone.utils.SharedPrefUtils;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends BaseActivity implements OnDateSelectedListener{

 	
	private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
	private static final int REQUEST_CODE_RECOVER_FROM_AUTH_ERROR = 1001;
	private static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;
	
	
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	private SharedPrefUtils sharedPrefUtils;
	private FragmentManager fm;
	private Date dateOfBirth ;
	private boolean checkDoctor = false;
	private boolean checkPatient = false;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		sharedPrefUtils = new SharedPrefUtils(this);
		fm = getFragmentManager();
		selectFragment();
		
	}

		
	
	
	public void onRegisterDoctor(View v){ 
		checkDoctor = true;
		DatePickerFragment frag = new DatePickerFragment();
		frag.show(getFragmentManager(), "datePicker"); 	   
   }
	    	
	
	 
	
	public void onRegisterPatient(View v){
		 checkPatient = true;
		 DatePickerFragment frag = new DatePickerFragment();
		 frag.show(getFragmentManager(), "datePicker");       	     	     		   
	}
	
	
	

	
	
	@Override
	public void onDateSelected(int year, int month, int day) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, 0, 0);  
		dateOfBirth = c.getTime();
		
		String email = sharedPrefUtils.getEmail();
		String firstName = sharedPrefUtils.getFirstName();
        String lastName = sharedPrefUtils.getLastName();
        String gender = sharedPrefUtils.getGender();
	    String pictureUrl = sharedPrefUtils.getImageUrl();
		
	    
	    if(checkDoctor){
	    	Doctor d = new Doctor(email, firstName, lastName, gender, pictureUrl , dateOfBirth);
			
	    	if(isOnline()){
			  new RegisterDoctorTask(this).execute(d);
		   }else{
			  Toast.makeText(this, "Internet Connection Not Available ", Toast.LENGTH_LONG).show();
			  finish();
		    }  
	    
	    }else if(checkPatient){
	    	Patient p = new Patient(email, firstName , lastName,gender, pictureUrl, dateOfBirth);
		     
		     if(isOnline()){
		    	  new RegisterPatientTask(this).execute(p);
		  	 }else{
				  Toast.makeText(this, "Internet Connection Not Available ", Toast.LENGTH_LONG).show();
				  finish();
			   } 
	   }
	    
	}


	
	
	public void selectFragment(){
	    if(sharedPrefUtils.getId() == 0){
        	pickUserAccount();
        	 
		}else{
			Fragment fragment;
			
			
			if(sharedPrefUtils.isDoctor()){
				
				//Start Alarms at Regular Intervals to Start the CheckPainIntentService ....
				alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
				Intent intent = new Intent(getApplicationContext(), CheckPainIntentService.class);
				alarmIntent = PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60*1000, alarmIntent);
				
			
				fragment = new DoctorMain_Fragment();
				FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right,R.animator.slide_out_right);
				ft.replace(R.id.mainContainer, fragment);
			    ft.commit();
				
				
				
			}else{
                
                fragment = new PatientMain_Fragment();
               	FragmentTransaction ft = fm.beginTransaction();
				ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right,R.animator.slide_out_right);
               	ft.replace(R.id.mainContainer, fragment);
        	    ft.commit();
				
				
			}
		
		    
	   }
  	
	    
	}
	
	
	
    private void pickUserAccount() {
	    String[] accountTypes = new String[]{"com.google"};
	    Intent intent = AccountPicker.newChooseAccountIntent(null, null,accountTypes, false, null, null, null, null);
	    startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
	    
	}
	
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
	       
			if (resultCode == Activity.RESULT_OK) {
	        	sharedPrefUtils.setEmail(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
	        	if(isOnline()){
	        		new GetClientCategoryTask(this).execute();
	 		   }else{
	 			  Toast.makeText(this, "Internet Connection Not Available ", Toast.LENGTH_LONG).show();
	 			  finish();
	 		   } 
	        	 
	         	
	       }else if (resultCode == Activity.RESULT_CANCELED) {
				 Toast.makeText(this, "Pick Account ", Toast.LENGTH_SHORT).show();
			}
	    
			
			
	  }else if ((requestCode == REQUEST_CODE_RECOVER_FROM_AUTH_ERROR ||
		              requestCode == REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR)
		              && resultCode == RESULT_OK) {
				  
		  if(isOnline()){
			  new GetClientCategoryTask(this).execute();
		   }else{
			  Toast.makeText(this, "Internet Connection Not Available ", Toast.LENGTH_LONG).show();
			  finish();
		   } 
				 
				  
	   }
		
        super.onActivityResult(requestCode, resultCode, data);
			
	}


	
	
	public void getClientCategoryTaskResult(String category){
		
		if(category == null){
			return;
		}
		
		if(category.equals(CheckinSvcApi.CATEGORY_DOCTOR)){
			
			if(isOnline()){
				new GetClientInfoTask(this, true).execute();
			}else{
	        	Toast.makeText(this, "Internet Connection Not Available ", Toast.LENGTH_LONG).show();
				finish();
			} 
			
		
		}else if(category.equals(CheckinSvcApi.CATEGORY_PATIENT)){
			
			if(isOnline()){
				new GetClientInfoTask(this, false).execute();
			}else{
	        	 Toast.makeText(this, "Internet Connection Not Available ", Toast.LENGTH_LONG).show();
				 finish();
			} 
			
			
		}else if(category.equals(CheckinSvcApi.CATEGORY_EMPTY)){
			Fragment frag = new RegisterFragment();
		    FragmentTransaction ft = fm.beginTransaction();
		    ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right,R.animator.slide_out_right);
			ft.replace(R.id.mainContainer, frag);
			ft.commit();
		
		}
		
		
	}

	
	
	
	public void registerDoctorTaskResult(Doctor d){
		selectFragment();
	}
	
	
	public void registerPatientTaskResult(Patient p){
		selectFragment();
	}




	public void getClientInfoTaskResult() {
		selectFragment();
	}
	
	
	
	
	
	
	


	public void handleException(final Exception e) {
	    
	    runOnUiThread(new Runnable() {
	        @Override 
	        public void run() { 
	            if (e instanceof GooglePlayServicesAvailabilityException) {
	              
	                int statusCode = ((GooglePlayServicesAvailabilityException)e)
	                        .getConnectionStatusCode();
	                Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode,
	                        MainActivity.this, 
	                        REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
	                dialog.show();
	            } else if (e instanceof UserRecoverableAuthException) {
	               
	                Intent intent = ((UserRecoverableAuthException)e).getIntent();
	                startActivityForResult(intent,
	                        REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
	            } 
	        } 
	    }); 
	}





	
	
	
	
	
	
}
