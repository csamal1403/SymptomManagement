package com.android.capstone.utils;

import java.util.ArrayList;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;

import android.content.Context;
import com.android.capstone.data.Checkin;
import com.android.capstone.data.CheckinSvcApi;
import com.android.capstone.data.Doctor;
import com.android.capstone.data.GPlusProfile;
import com.android.capstone.data.Medications;
import com.android.capstone.data.Patient;
import com.android.capstone.data.VDoctor;
import com.android.capstone.data.VPatient;




public class RetrofitUtils {

	
	public static final String TOKEN_ENDPOINT = "https://www.googleapis.com";
	private final String ENDPOINT = "https://carbide-crowbar-749.appspot.com";
	
	private CheckinSvcApi service;
	private Context mContext;
	
	
	public RetrofitUtils(Context context){
		
		this.mContext = context;
		
		final String tokenString = new SharedPrefUtils(context).getAccessToken();
		
		if(tokenString != null){
			RequestInterceptor requestInterceptor = new RequestInterceptor()
			{
			    @Override
			    public void intercept(RequestFacade request) {
			    	request.addHeader(CheckinSvcApi.AUTH_HEADER , tokenString);
			    }
			};

			
			service = new RestAdapter.Builder().setEndpoint(ENDPOINT)
					          .setRequestInterceptor(requestInterceptor)
	                          .setLogLevel(LogLevel.FULL)
	                          .build().create(CheckinSvcApi.class);
			
		}
		
		
		
	}

	
	
	
	public GPlusProfile getClientGPlusProfile(){
		CheckinSvcApi ch = new RestAdapter.Builder().setEndpoint(TOKEN_ENDPOINT)
                           .setLogLevel(LogLevel.FULL)
                           .build().create(CheckinSvcApi.class);
		
		String token = new SharedPrefUtils(mContext).getAccessToken();
		
		return ch.getClientInfo(token);
		
	}
	
	
	
	public String getClientCategory() {
		if(service != null){
			return service.getClientCategory();
		}else{
			return null;
		}
		
	}

	public Doctor getDoctorByEmail(){
		if(service != null){
			return service.getDoctorInfoByEmail();
		}else{
			return null;
		}
		
	}
	
	public Patient getPatientByEmail(){
		if(service != null){
			return service.getPatientInfoByEmail();
		}else{
			return null;
		}
	   
	}
	
    public Doctor registerDoctor(Doctor d){
    	if(service != null){
    		return service.registerDoctor(d);
		}else{
			return null;
		}
	   
	}
	
    public Patient registerPatient(Patient p){
    	if(service != null){
    		return service.registerPatient(p);
		}else{
			return null;
		}
	   
	}
   
    
    
    
    
    
    public ArrayList<VPatient> getPatientInfos(Long doctorId) {
    	if(service != null){
    		return (ArrayList<VPatient>) service.getPatients(doctorId);
		}else{
			return null;
		}
		
	 }

    public ArrayList<VDoctor> getDoctorInfos(Long patientId) {
    	if(service != null){
    		return (ArrayList<VDoctor>) service.getDoctors(patientId);
		}else{
			return null;
		}
		
	}

	public VPatient addPatientInfo(Long doctorId, String email) {
		if(service != null){
			 return service.addPatient(doctorId, email);
		}else{
			return null;
		}
		
	}

	
	
	

	
	public Medications addMedications(Medications m) {
		if(service != null){
			return service.addMedications(m);
		}else{
			return null;
		}
		
	}

    public Medications getMedications(Long doctorId, Long patientId) {
    	if(service != null){
    		return service.getMedicationsById(doctorId, patientId);
		}else{
			return null;
		}
		 
	}


    
    
	
    
	
	public Checkin addCheckin(Checkin c) {
		if(service != null){
			return service.addCheckin(c);
		}else{
			return null;
		}
		 
	}

    public ArrayList<Checkin> getCheckins(Long doctorId, Long patientId) {
    	if(service != null){
    		return (ArrayList<Checkin>) service.getCheckinsById(doctorId, patientId);
		}else{
			return null;
		}
		
	}



	
}
