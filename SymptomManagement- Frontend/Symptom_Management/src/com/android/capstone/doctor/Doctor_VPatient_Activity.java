package com.android.capstone.doctor;

import com.android.capstone.BaseActivity;
import com.android.capstone.R;
import com.android.capstone.utils.SharedPrefUtils;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Doctor_VPatient_Activity extends BaseActivity {

	
    Long doctorId, patientId;
    String vPatientName, pictureUrl;	
    
   
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.activity_doctor_vpatient);
		
		 doctorId = new SharedPrefUtils(this).getId();
		 patientId = getIntent().getLongExtra("patientId", 0);
		 vPatientName = getIntent().getStringExtra("pName");
		 pictureUrl =   getIntent().getStringExtra("pictureUrl");

		 Fragment fragment = new Doctor_VPatient_Fragment(doctorId, patientId, vPatientName, pictureUrl);
	   	 FragmentTransaction ft = getFragmentManager().beginTransaction();
		 ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right,R.animator.slide_out_right);
		 ft.replace(R.id.vPatientContainer, fragment);
		 ft.commit();
			
		 
	
		
	}


	

     @Override
     public void finish() {
      	super.finish();
  	    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
  	
     }
  


}