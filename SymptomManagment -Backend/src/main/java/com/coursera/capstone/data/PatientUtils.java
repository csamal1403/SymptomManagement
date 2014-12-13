package com.coursera.capstone.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;





public class PatientUtils {

	private static final String API_KEY = "AIzaSyBQrNoa9uiJ2VlXrC5oxuejPZcXgmzhTl8";
	
    private PersistenceManager pm ;
    private CheckinUtils utils;
    
	
	public PatientUtils() {
		pm =  PMF.get().getPersistenceManager();
		utils = new CheckinUtils();
	}
	
	
	
	
	
	
	public Patient registerPatient(Patient p){
		pm.makePersistent(p);
		return p;
	}
	
	
	
	public Patient getPatientInfoByEmail(String pEmailId){
		Long patientId = utils.getPatientIdByEmail(pEmailId);
		Patient patient = pm.getObjectById(Patient.class, patientId);
		return patient;
	}






	public Collection<VDoctor> getDoctors(long patientId) {
		
        List<VDoctor> vDoctors = new ArrayList<VDoctor>();
		
		Patient patient = pm.getObjectById(Patient.class, patientId);
		
		for (Long doctorId : patient.getDoctorIds()) {
			Doctor doctor = pm.getObjectById(Doctor.class, doctorId);
			VDoctor vDoctor = new VDoctor(doctor.getDoctorId(), doctor.getDoctorEmailId(),
					                      doctor.getFirstName(), doctor.getLastName(), doctor.getPictureUrl(),
					                      doctor.getAbout(), doctor.getBirthDate()) ;
			
			vDoctors.add(vDoctor);
		}
		
		return vDoctors;
	}






	public Checkin addCheckin(Checkin ch) {
		pm.makePersistent(ch);
		Long checkinId = ch.getCheckinId();
		
		// Can change this..
		Checkin checkin = pm.getObjectById(Checkin.class, checkinId);
		
		
		//Testing GCM...
		Long doctorId = checkin.getDoctorId();
		Doctor doctor = pm.getObjectById(Doctor.class, doctorId);
		Message message = new Message.Builder().addData("checkins", checkin.getPatientId().toString()).build();
		
		
		Result result = null;
		try {
			Sender sender = new Sender(API_KEY);
			result = sender.send(message, doctor.getGCMregId(), 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if(result == null){
	    	return null;
	    }
	    
	   
	    if (result.getMessageId() != null) {
	        String canonicalRegId = result.getCanonicalRegistrationId();
	        if (canonicalRegId != null) {
	          Doctor doctor1 = pm.getObjectById(Doctor.class, doctorId);
	          doctor1.setGCMregId(canonicalRegId);
	        }
	   
	    }else{
	       String error = result.getErrorCodeName();  //Should Unregister the device..
	        
	    }
		
		
		
		
		return checkin;
		
		
	}
	
	
	
	
	
	
	
	
	
}
