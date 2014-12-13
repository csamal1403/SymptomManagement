package com.android.capstone.data;

import java.util.Date;

public class VDoctor {

	
	private Long doctorId;
	private String doctorEmailId;
	private String firstName;
	private String lastName;
	private String pictureUrl;
	private String about;
	private Date birthDate;
	
	
	public VDoctor(Long doctorId, String doctorEmailId, String firstName,
			String lastName, String pictureUrl, String about, Date birthDate) {
		super();
		this.doctorId = doctorId;
		this.doctorEmailId = doctorEmailId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pictureUrl = pictureUrl;
		this.about = about;
		this.birthDate = birthDate;
	}


	public Long getDoctorId() {
		return doctorId;
	}


	public String getDoctorEmailId() {
		return doctorEmailId;
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


	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}


	public void setDoctorEmailId(String doctorEmailId) {
		this.doctorEmailId = doctorEmailId;
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
