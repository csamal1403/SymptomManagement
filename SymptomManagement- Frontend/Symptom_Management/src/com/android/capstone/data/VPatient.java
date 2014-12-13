package com.android.capstone.data;

import java.util.Date;

public class VPatient {
	
	
	private Long patientId;
	private String patientEmailId;
	private String firstName;
	private String lastName;
	private String pictureUrl;
	private String about;
	private Date birthDate;
	
	
	public VPatient(Long patientId, String patientEmailId, String firstName,
			String lastName, String pictureUrl, String about, Date birthDate) {
		super();
		this.patientId = patientId;
		this.patientEmailId = patientEmailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pictureUrl = pictureUrl;
		this.about = about;
		this.birthDate = birthDate;
	}


	public Long getPatientId() {
		return patientId;
	}


	public String getPatientEmailId() {
		return patientEmailId;
	}


	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
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


	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}


	public void setPatientEmailId(String patientEmailId) {
		this.patientEmailId = patientEmailId;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	
	
	
	
	
	

}
