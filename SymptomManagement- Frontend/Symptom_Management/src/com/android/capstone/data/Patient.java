package com.android.capstone.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Patient {
	

	private Long patientId;
	private String patientEmailId;
	private String GCMregId;
	private String firstName;
	private String lastName;
	private String gender;
	private String pictureUrl;
	private String about;
	private Date birthDate;
	private List<Long> doctorIds;

	
	
	
	
	public Patient() {
		this.doctorIds = new ArrayList<Long>();
	}





	public Patient(String patientEmailId, String firstName, String lastName,
			String gender, String pictureUrl, Date birthDate) {
		super();
		this.patientEmailId = patientEmailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.pictureUrl = pictureUrl;
		this.birthDate = birthDate;
	}





	public Long getPatientId() {
		return patientId;
	}





	public String getPatientEmailId() {
		return patientEmailId;
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





	public List<Long> getDoctorIds() {
		return doctorIds;
	}





	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}





	public void setPatientEmailId(String patientEmailId) {
		this.patientEmailId = patientEmailId;
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





	public void setDoctorIds(List<Long> doctorIds) {
		this.doctorIds = doctorIds;
	}


	public void addDoctorId(Long doctorId) {
		this.doctorIds.add(doctorId);
	}

	
	
}
