package com.android.capstone.task;

import java.util.ArrayList;

import com.android.capstone.MainActivity;
import com.android.capstone.data.Checkin;
import com.android.capstone.data.Doctor;
import com.android.capstone.data.Medications;
import com.android.capstone.data.Patient;
import com.android.capstone.data.VDoctor;
import com.android.capstone.data.VPatient;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.RetrofitUtils;
import com.android.capstone.utils.SharedPrefUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;



public class GetClientInfoTask extends AsyncTask<Void, Void , Void> {

	private MainActivity activity;
	private CommonUtils commonUtils;
	private SharedPrefUtils sharedPrefUtils;
	private boolean isDoctor;
	private ProgressDialog dialog;
	
	
	
	public GetClientInfoTask(MainActivity activity, boolean isDoctor) {
		super();
		this.activity = activity;
		this.isDoctor = isDoctor;
		sharedPrefUtils = new SharedPrefUtils(activity.getApplicationContext());
		commonUtils = new CommonUtils(activity.getApplicationContext());
	}
	
	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(activity, "Getting UserInfo ", "Please Wait... ", true);
	}
	
	
	
	@Override
	protected Void doInBackground(Void... params) {
		RetrofitUtils retrofitUtils =  new RetrofitUtils(activity);
		
		
		try {
			if(isDoctor){
			     Doctor d = retrofitUtils.getDoctorByEmail();
			     commonUtils.saveDoctor(d);
			     
			     Long doctorId = sharedPrefUtils.getId();
				 ArrayList<VPatient> patients = retrofitUtils.getPatientInfos(doctorId);
				
			     if(!(patients.isEmpty()) || (patients == null)){
					commonUtils.savePatientInfos(patients);
					
					for(VPatient patient : patients){
						Medications med = retrofitUtils.getMedications(doctorId, patient.getPatientId());
						ArrayList<Checkin> checkins = retrofitUtils.getCheckins(doctorId, patient.getPatientId());
						commonUtils.saveMedications(med, true);
						commonUtils.saveCheckins(checkins, true);
					}
				 }
			     
			     
			
			}else{
			     Patient p = retrofitUtils.getPatientByEmail();
			     commonUtils.savePatient(p);
			     
			     Long patientId = sharedPrefUtils.getId();
				 ArrayList<VDoctor> doctors = retrofitUtils.getDoctorInfos(patientId);
					
				 if(!(doctors.isEmpty()) || (doctors == null)){
					commonUtils.saveDoctorInfos(doctors);
						
					for(VDoctor doctor : doctors){
						Log.i(CommonUtils.LOG_TAG, "Id: " +doctor.getDoctorId());
						Medications med = retrofitUtils.getMedications(doctor.getDoctorId(), patientId);
						ArrayList<Checkin> checkins = retrofitUtils.getCheckins(doctor.getDoctorId(), patientId);
						commonUtils.saveMedications(med, false);
						commonUtils.saveCheckins(checkins, false);
					}
				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		return null;
		
	}

	
	
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		dialog.dismiss();
		
		if(activity != null){
			activity.getClientInfoTaskResult();
		}
		
	}
	
	
	
	
	
	

}
