package com.coursera.capstone.data;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;


public class CheckinUtils {
	
	
	
	public static final String QUESTION_1 = "How bad is your mouth pain/sore throat ?";
	public static final String QUESTION_2 = "Does your pain stop you from eating/drinking ?";
	public static final String QUESTION_3 = "Did you take your Pain Medications ?";
	
	
	
	
	
	private PersistenceManager pm ;
	
	
	public CheckinUtils() {
		pm =  PMF.get().getPersistenceManager();
	}
	
	
	
	
	 @SuppressWarnings("unchecked")
	 public List<Checkin> getCheckinsByClientIds(Long doctorId, Long patientId){
		  
		 List<Checkin> results;
		 
	     Query q =  pm.newQuery(Checkin.class);
	     q.setFilter("doctorId == dId && patientId == pId");
  		 q.declareParameters("Long dId, Long pId");
	 		
	 	try {
	 	     results =  (List<Checkin>) q.execute(doctorId, patientId);
	 	    
	    }finally {
	 	      q.closeAll();
	 	}
	 	
	 	return results;
	 		
	}
	
	
  	
	
	
	 @SuppressWarnings("unchecked")
	 public Long getMedicationIdByClientIds(Long doctorId, Long patientId){
		  
		 Long result;
		 
	     Query q =  pm.newQuery(Medications.class);
	     q.setFilter("doctorId == dId && patientId == pId");
   		 q.declareParameters("Long dId, Long pId");
	 		
	 	try {
	 	    List<Medications> results =   (List<Medications>) q.execute(doctorId, patientId);
	 	    
	 	    if (results.isEmpty()) {
			     result = null;
			  } else {
				 result = results.get(0).getMedicationId();
			  }
	 	    
	    }finally {
	 	      q.closeAll();
	 	}
	 	
	 	return result;
	 		
	}
		
	
	
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public Long getPatientIdByEmail(String pEmailId){
		
		Long result;
		
		Query q =  pm.newQuery(Patient.class);
  		q.setFilter("patientEmailId == email");
  		q.declareParameters("String email");
  		
  		try {
  			  List<Patient> results =   (List<Patient>) q.execute(pEmailId);
  			  Patient p = results.get(0);
  			  result = p.getPatientId();
  			 
  			} finally {
  			  q.closeAll();
  		 }
  		return result;
		
	}
	
	
	
	
	
   @SuppressWarnings("unchecked")
   public Long getDoctorIdByEmail(String dEmailId){
		
	   Long result;
	   
		Query q =  pm.newQuery(Doctor.class);
 		q.setFilter("doctorEmailId == email");
 		q.declareParameters("String email");
 		
 		try {
 			  List<Doctor> results =   (List<Doctor>) q.execute(dEmailId);
 			  Doctor d = results.get(0);
 			  result = d.getDoctorId();
 			  
 			} finally {
 			  q.closeAll();
 		 }
 		return result;
 		
	}
	
	
      
   
    @SuppressWarnings("unchecked")
  	public boolean containsDoctor(String emailId) {
  		boolean present;
  	
  		Query q =  pm.newQuery(Doctor.class);
  		q.setFilter("doctorEmailId == email");
  		q.declareParameters("String email");
  		
  		try {
  			  List<Doctor> results = (List<Doctor>) q.execute(emailId);
  			  if (results.isEmpty()) {
  			     present = false;
  			  } else {
  			     present = true;
  			  }
  			} finally {
  			  q.closeAll();
  		 }
  		return present;
  	}
  	
  	
    
  	
  	
  	@SuppressWarnings("unchecked")
  	public boolean containsPatient(String emailId){
  		boolean present;
  		
  		Query q =  pm.newQuery(Patient.class);
  		q.setFilter("patientEmailId == email");
  		q.declareParameters("String email");
  		
  		try {
  			  List<Patient> results = (List<Patient>) q.execute(emailId);
  			  if (results.isEmpty()) {
  			     present = false;
  			  } else {
  			     present = true;
  			  }
  			} finally {
  			  q.closeAll();
  		 }
  		return present;
  	}



	

  	
  	
}
	
