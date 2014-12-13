package com.android.capstone.data;


import java.util.Collection;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;


public interface CheckinSvcApi {
     
	
	public static final String AUTH_HEADER = "Authorization";
	
	public static final String CATEGORY_DOCTOR = "doctor";
	public static final String CATEGORY_PATIENT = "patient";
	public static final String CATEGORY_EMPTY = "empty";
	
	public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/plus.profile.emails.read";
	
	
	
	
	@GET("/oauth2/v1/userinfo")
	public GPlusProfile getClientInfo(@Query("access_token") String token);
	
	
	
	
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
	public Medications getMedicationsById(@Query("dId") Long doctorId , @Query("pId") Long patientId );
	
	
	@GET("/checkins")
	public List<Checkin> getCheckinsById(@Query("dId") long doctorId , @Query("pId") long patientId );
	
	
	
	
}
