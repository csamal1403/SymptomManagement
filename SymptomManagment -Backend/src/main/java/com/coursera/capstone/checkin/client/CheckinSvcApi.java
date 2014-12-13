package com.coursera.capstone.checkin.client;


import java.util.Collection;
import java.util.List;

import com.coursera.capstone.data.Checkin;
import com.coursera.capstone.data.Doctor;
import com.coursera.capstone.data.Medications;
import com.coursera.capstone.data.Patient;
import com.coursera.capstone.data.VDoctor;
import com.coursera.capstone.data.VPatient;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;


public interface CheckinSvcApi {
     
	
	
	
	public static final String CATEGORY_DOCTOR = "doctor";
	public static final String CATEGORY_PATIENT = "patient";
	public static final String CATEGORY_EMPTY = "empty";
	
	
	
	
	
	
	
   //  ****************SERVICES  FOR  DOCTOR   **************************************//

	//  POST  REQUESTS  ....
	
	
	@POST("/register/doctor")
	public Doctor registerDoctor(@Body Doctor d);
	
	
	@POST("/{id}/patient")
	public VPatient addPatient(@Path("id") long id, @Query("emailId") String pEmailId);     

	
	
	@POST("/doctor/medications")
	public Medications addMedications(@Body Medications m);
	
	
	
	
	
	
    //  GET  REQUESTS  .... 
	
	
	@GET("/doctor/info")
	public Doctor getDoctorInfoByEmail();
	
	
	
	@GET("/{id}/patients")
	public Collection<VPatient> getPatients(@Path("id") long id);


	
	
	
	
	
	
	 // **********************    SERVICES  FOR  PATIENT    ************************************//
	
	 //  POST  REQUESTS  ...
		
	
	@POST("/register/patient")
	public Patient registerPatient(@Body Patient p);

	 	
	@POST("/patient/checkin")
    public Checkin addCheckin(@Body Checkin ch);
	
	
      
	//  GET  REQUESTS  ....
	
	 
	@GET("/patient/info")
	public Patient getPatientInfoByEmail();
	
	
	 
	@GET("/{id}/doctors")
    public Collection<VDoctor> getDoctors(@Path("id") long id);

		
		
	 

	
	
	
	// ******************* SERVICES  FOR  BOTH  DOCTOR  &  PATIENT  *******************************//
	
	
    
	//  GET  REQUESTS  ...
	
	
	@GET("/category")
	public String getClientCategory();
	
	
	
	@GET("/medications")
	public Medications getMedicationsById(@Query("dId") long doctorId , @Query("pId") long patientId );
	
	
	
	@GET("/checkins")
	public List<Checkin> getCheckinsById(@Query("dId") long doctorId , @Query("pId") long patientId );
	
	
	
	
	
	
	
}
