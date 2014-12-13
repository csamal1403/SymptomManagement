package com.coursera.capstone.data;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class Medications{

	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long medicationId;
	
	
	@Persistent
	private Long doctorId;
	
	@Persistent
	private Long patientId;
	
	@Persistent
	private List<String> medications;
	
	
	
	
	public Medications() {
		medications = new ArrayList<String>();
	}



	public Medications(Long doctorId, Long patientId, List<String> medications) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.medications = medications;
	}


	
	

	public Long getMedicationId() {
		return medicationId;
	}



	public void setMedicationId(Long medicationId) {
		this.medicationId = medicationId;
	}



	public Long getDoctorId() {
		return doctorId;
	}



	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}



	public Long getPatientId() {
		return patientId;
	}



	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}



	public List<String> getMedications() {
		return medications;
	}



	public void setMedications(List<String> medications) {
		this.medications = medications;
	}




	
}
