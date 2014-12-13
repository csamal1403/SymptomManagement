package com.android.capstone.data;

import java.io.Serializable;
import java.util.Date;


public class MedicationCheckinQA implements Serializable{
	
	
	private String medicationQuestion;
	private String medicationAnswer;
	private Date medicationDate;
	
	
	public String getMedicationQuestion() {
		return medicationQuestion;
	}
	
	public void setMedicationQuestion(String medicationQuestion) {
		this.medicationQuestion = medicationQuestion;
	}
	
	public String getMedicationAnswer() {
		return medicationAnswer;
	}
	
	public void setMedicationAnswer(String medicationAnswer) {
		this.medicationAnswer = medicationAnswer;
	}
	
	public Date getMedicationDate() {
		return medicationDate;
	}
	
	public void setMedicationDate(Date medicationDate) {
		this.medicationDate = medicationDate;
	}
	
	
	
	
	

}
