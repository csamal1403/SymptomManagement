package com.coursera.capstone.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;

public class DoctorUtils {

	
	private static final String API_KEY = "AIzaSyBQrNoa9uiJ2VlXrC5oxuejPZcXgmzhTl8";
	
	
    private PersistenceManager pm ;
    private CheckinUtils utils;
	
	
	public DoctorUtils() {
		pm =  PMF.get().getPersistenceManager();
		utils = new CheckinUtils();
	}
	
	
	
	
	
	
	public Doctor registerDoctor(Doctor d){
		pm.makePersistent(d);
		return d;
	}
	
	
	
	
	public Doctor getDoctorInfoByEmail(String dEmailId){
		Long doctorId = utils.getDoctorIdByEmail(dEmailId);
		Doctor doctor = pm.getObjectById(Doctor.class, doctorId);
		return doctor;
	}






	public VPatient addPatient(Long doctorId, String pEmailId) {
		  
		  Doctor doctor = pm.getObjectById(Doctor.class, doctorId);
		        
		  if(utils.containsPatient(pEmailId)){
			  Long patientId = utils.getPatientIdByEmail(pEmailId);
			  Patient patient = pm.getObjectById(Patient.class, patientId);
				      
		      if( ! (doctor.getPatientIds().contains(patientId) &&  patient.getDoctorIds().contains(doctorId))){
				    patient.addDoctorId(doctorId);
				    doctor.addPatientId(patientId);
			        
				    VPatient vPatient = new VPatient(patient.getPatientId(), patient.getPatientEmailId(),
				    		                         patient.getFirstName(),patient.getLastName(), patient.getPictureUrl(),
				    		                         patient.getAbout(), patient.getBirthDate());
				    
				    
				    Message message = new Message.Builder().addData("patient", doctorId.toString()).build();
				   
				    Result result = null;
				    try {
					    Sender sender = new Sender(API_KEY);
					    result = sender.send(message, patient.getGCMregId(), 5);
				      } catch (IOException e) {
					     e.printStackTrace();
				      }
				    
				    
				    if(result == null){
				    	return null;
				    }
				    
				   
				    if (result.getMessageId() != null) {
				        String canonicalRegId = result.getCanonicalRegistrationId();
				        if (canonicalRegId != null) {
				          Patient patient1 = pm.getObjectById(Patient.class, patientId);
				          patient1.setGCMregId(canonicalRegId);
				        }
				   
				    }else{
				       String error = result.getErrorCodeName();  //Should Unregister the device..
				        
				    }
				    

				    
				   return vPatient;
				    
		      }else{
		    	  return null; 
		      }
		       
		 }else{
			  return null;
		 }
		      	
     }

	







	public Collection<VPatient> getPatients(long doctorId) {
		
		List<VPatient> vPatients = new ArrayList<VPatient>();
		
		Doctor doctor = pm.getObjectById(Doctor.class, doctorId);
		
		for (Long patientId : doctor.getPatientIds()) {
			Patient patient = pm.getObjectById(Patient.class, patientId);
			VPatient vPatient = new VPatient(patient.getPatientId(), patient.getPatientEmailId(),
					                         patient.getFirstName(), patient.getLastName(),patient.getPictureUrl(),
					                         patient.getAbout(), patient.getBirthDate());
			
			vPatients.add(vPatient);
		}
		
		return vPatients;
	}







	public Medications addMedications(Medications m) {
	    
		Medications result;
		
    	Long doctorId = m.getDoctorId();
    	Long patientId = m.getPatientId();
    	
        Doctor d = pm.getObjectById(Doctor.class ,doctorId);
        Patient p = pm.getObjectById(Patient.class ,patientId);
    		 
    	if(d.getPatientIds().contains(m.getPatientId()) &&  p.getDoctorIds().contains(m.getDoctorId())){
    		Long medicationId = utils.getMedicationIdByClientIds(doctorId, patientId);
   		 
   		    if(medicationId != null){
   			    Medications med = pm.getObjectById(Medications.class ,medicationId);
   			    med.setMedications(m.getMedications());
   			    result = med;
   		  
   		   }else{
   			   pm.makePersistent(m);
   			   result = m; 
   		    }
   		    
 
	     Message message = new Message.Builder().addData("medications", doctorId.toString()).build();
	    
	     
	     
	        Result res = null;
		    try {
			    Sender sender = new Sender(API_KEY);
			    res = sender.send(message, p.getGCMregId(), 5);
		      } catch (IOException e) {
			     e.printStackTrace();
		      }
		    
		    
		    if(res == null){
		    	return null;
		    }
		    
		   
		    if (res.getMessageId() != null) {
		        String canonicalRegId = res.getCanonicalRegistrationId();
		        if (canonicalRegId != null) {
		          Patient patient1 = pm.getObjectById(Patient.class, patientId);
		          patient1.setGCMregId(canonicalRegId);
		        }
		   
		    }else{
		       String error = res.getErrorCodeName();  //Should Unregister the device..
		        
		    }
		    
	    
   	
   		     
       }else{
    	   result = null;
        }
		
    	
    	    
    	
    	return result;
       
   }


		
		

	
	
	
	
	
	
	

	
	
}
