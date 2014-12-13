package com.android.capstone.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Doctor {
	
	private Long doctorId;
	private String doctorEmailId;
	private String GCMregId;
	private String firstName;
	private String lastName;
	private String gender;
	private String pictureUrl;
	private String about;
	private Date birthDate;
	private List<Long> patientIds;

	
	
	
	
	public Doctor() {
		this.patientIds = new ArrayList<Long>();
	}





	public Doctor(String doctorEmailId, String firstName, String lastName,
			String gender, String pictureUrl, Date birthDate) {
		super();
		this.doctorEmailId = doctorEmailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.pictureUrl = pictureUrl;
		this.birthDate = birthDate;
	}





	public Long getDoctorId() {
		return doctorId;
	}





	public String getDoctorEmailId() {
		return doctorEmailId;
	}





	public String getGCMregId() {
		return GCMregId;
	}





	public String getFirstName() {
		return firstName;
	}





	public String getLastName() {
		return lastName;
	}





	public String getGender() {
		return gender;
	}





	public String getPictureUrl() {
		return pictureUrl;
	}





	public String getAbout() {
		return about;
	}





	public Date getBirthDate() {
		return birthDate;
	}





	public List<Long> getPatientIds() {
		return patientIds;
	}





	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}





	public void setDoctorEmailId(String doctorEmailId) {
		this.doctorEmailId = doctorEmailId;
	}





	public void setGCMregId(String gCMregId) {
		GCMregId = gCMregId;
	}





	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}





	public void setLastName(String lastName) {
		this.lastName = lastName;
	}





	public void setGender(String gender) {
		this.gender = gender;
	}





	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}





	public void setAbout(String about) {
		this.about = about;
	}





	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}





	public void setPatientIds(List<Long> patientIds) {
		this.patientIds = patientIds;
	}


	public void addPatientId(Long patientId) {
		this.patientIds.add(patientId);
	}
	
	
	
	
}
