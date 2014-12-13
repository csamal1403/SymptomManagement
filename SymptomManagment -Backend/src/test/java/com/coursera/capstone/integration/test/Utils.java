package com.coursera.capstone.integration.test;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	
	
	public static final String DOCTOR_EMAIL_1 = "stringcondensate@gmail.com";
	public static final String DOCTOR_EMAIL_2 = "transformers.samal@gmail.com";
	public static final String DOCTOR_EMAIL_3 = "cyberton.xyz@gmail.com";
	public static final String DOCTOR_EMAIL_4 = "devcode.xyz@gmail.com";
	
	public static final String PATIENT_EMAIL_1 = "abc@gmail.com";
	public static final String PATIENT_EMAIL_2 = "def@gmail.com";
	public static final String PATIENT_EMAIL_3 = "ghi@gmail.com";
	public static final String PATIENT_EMAIL_4= "jkl@gmail.com";
	public static final String PATIENT_EMAIL_5 = "mno@gmail.com";
	public static final String PATIENT_EMAIL_6 = "pqrs@gmail.com";
	public static final String PATIENT_EMAIL_7 = "tuv@gmail.com";
	public static final String PATIENT_EMAIL_8 = "wxyz@gmail.com";
	public static final String PATIENT_EMAIL_9 = "abc12345.xyz@gmail.com";
	public static final String PATIENT_EMAIL_10 = "def12345.xyz@gmail.com";
	public static final String PATIENT_EMAIL_11 = "ghi12345@gmail.com";
	public static final String PATIENT_EMAIL_12= "jkl12345@gmail.com";
	public static final String PATIENT_EMAIL_13 = "mno12345@gmail.com";
	public static final String PATIENT_EMAIL_14 = "pqrs12345@gmail.com";
	public static final String PATIENT_EMAIL_15 = "tuv12345@gmail.com";
	public static final String PATIENT_EMAIL_16 = "wxyz12345@gmail.com";

	
	
	
	
	
	
	
	public static final String QUES_1 = "How bad is your Mouth Pain / Sore Throat ? ";
	public static final String QUES_2 = "Did you take your Pain Medications ?";
	public static final String QUES_3 = "Does your Pain stop you from Eating / Drinking ?";
	
	public static final String QUES_1_ANS_1 = "Well Controlled";
	public static final String QUES_1_ANS_2 = "Moderate";
	public static final String QUES_1_ANS_3 = "Severe";
	
	public static final String QUES_2_ANS_1 = "Yes";
	public static final String QUES_2_ANS_2 = "No";
	
	public static final String QUES_3_ANS_1 = "No";
	public static final String QUES_3_ANS_2 = "Some";
	public static final String QUES_3_ANS_3 = "I can't Eat";
	
	
	
	
	public static final String MED_1 = "Lortab";
	public static final String MED_2 = "Hydrocodone";
	public static final String MED_3 = "Percocet";
	public static final String MED_4 = "Oxycontin";
	
	
	
	
	
	
	
	
	public Utils(){
		
	}
	
	
	
	public List<String> createFirstDoctorPatients(){
		
		List<String> patients = new ArrayList<String>();
		patients.add(PATIENT_EMAIL_1);
		patients.add(PATIENT_EMAIL_2);
		patients.add(PATIENT_EMAIL_3);
		patients.add(PATIENT_EMAIL_4);
		return patients;
	}
	
    public List<String> createSecondDoctorPatients(){
		
    	List<String> patients = new ArrayList<String>();
		patients.add(PATIENT_EMAIL_5);
		patients.add(PATIENT_EMAIL_6);
		patients.add(PATIENT_EMAIL_7);
		patients.add(PATIENT_EMAIL_8);
		return patients;
	}
    
    public List<String> createThirdDoctorPatients(){
		
    	List<String> patients = new ArrayList<String>();
		patients.add(PATIENT_EMAIL_9);
		patients.add(PATIENT_EMAIL_10);
		patients.add(PATIENT_EMAIL_11);
		patients.add(PATIENT_EMAIL_12);
		return patients;
	}

    public List<String> createFourthDoctorPatients(){
	    
    	List<String> patients = new ArrayList<String>();
		patients.add(PATIENT_EMAIL_13);
		patients.add(PATIENT_EMAIL_14);
		patients.add(PATIENT_EMAIL_15);
		patients.add(PATIENT_EMAIL_16);
		return patients;
    }
	
	
	
	
	
	
	
	
	
	

}
