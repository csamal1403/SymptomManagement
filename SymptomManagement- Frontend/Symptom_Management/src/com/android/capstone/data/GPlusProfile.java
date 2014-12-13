package com.android.capstone.data;

public class GPlusProfile {

	private String name;
	private String picture;
	private String gender;
	private String given_name;
	private String family_name;
	
	
	public GPlusProfile(String name, String picture, String gender,
			String given_name, String family_name) {
		super();
		this.name = name;
		this.picture = picture;
		this.gender = gender;
		this.given_name = given_name;
		this.family_name = family_name;
	}


	public String getName() {
		return name;
	}


	public String getPicture() {
		return picture;
	}


	public String getGender() {
		return gender;
	}


	public String getGiven_name() {
		return given_name;
	}


	public String getFamily_name() {
		return family_name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}


	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	
	
	
	
}
