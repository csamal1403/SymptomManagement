package com.coursera.capstone.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import com.coursera.capstone.checkin.client.CheckinSvcApi;

public class ClientUtils {

	
	
    private PersistenceManager pm ;
    private CheckinUtils utils;
	
	
	public ClientUtils() {
		pm =  PMF.get().getPersistenceManager();
		utils = new CheckinUtils();
	}
	
	
	
	
	
	
	public String getClientCategory(String emailId){
		
		if(utils.containsDoctor(emailId)){
			return CheckinSvcApi.CATEGORY_DOCTOR;
			
		}else if(utils.containsPatient(emailId)){
			return CheckinSvcApi.CATEGORY_PATIENT;
			
		}else{
			return CheckinSvcApi.CATEGORY_EMPTY;
		}
		
	}





	public Medications getMedicationsById(long doctorId, long patientId) {
	
  		
  		Doctor d = pm.getObjectById(Doctor.class, doctorId);
    	Patient p = pm.getObjectById(Patient.class ,patientId);
    		
    	if(d.getPatientIds().contains(patientId) &&  p.getDoctorIds().contains(doctorId)){
    		 Long medicationId = utils.getMedicationIdByClientIds(doctorId, patientId);
    		 
    		 if(medicationId != null){
    			 Medications med = pm.getObjectById(Medications.class ,medicationId);
    			 return med;
    		 
    		 }else{
    			 return null; 
    		 }
    		 	 
       }else{
    	   return null; 
       }
  	  
  	 
		
	}
	
	
	
	
	
	
	public List<Checkin> getCheckinsById(Long doctorId, Long patientId) {
  		
        List<Checkin> results;
  			
  		Doctor d = pm.getObjectById(Doctor.class ,doctorId);
    	Patient p = pm.getObjectById(Patient.class ,patientId);
    		
    	if(d.getPatientIds().contains(patientId) &&  p.getDoctorIds().contains(doctorId)){
    		results = utils.getCheckinsByClientIds(doctorId, patientId);
    		
  	   }else{
  		   results = new ArrayList<Checkin>();
  	    }
    	
  		
  	  return results;
  		
    }

	
	
	
	
	
	
	
	
}
