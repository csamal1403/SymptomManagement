package com.coursera.capstone.data;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;



@PersistenceCapable
public class Checkin {
	
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long checkinId;

	@Persistent
	private Long doctorId;
	
	@Persistent
	private Long patientId;
	
	
	@Persistent
	private String ans1;
	
	@Persistent
	private String ans2;
	
	@Persistent
	private String ans3;
	
	
	@Persistent
	private String medicationsJSON;
	
	
	@Persistent
	private Date checkinDate;

	
	
	
	
	
	public Checkin() {
		
	}






	public Checkin(Long doctorId, Long patientId, String ans1, String ans2,
			String ans3, String medicationsJSON, Date checkinDate) {
		super();
		this.doctorId = doctorId;
		this.patientId = patientId;
		this.ans1 = ans1;
		this.ans2 = ans2;
		this.ans3 = ans3;
		this.medicationsJSON = medicationsJSON;
		this.checkinDate = checkinDate;
	}






	public Long getCheckinId() {
		return checkinId;
	}

	
	public void setCheckinId(Long checkinId) {
		this.checkinId = checkinId;
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


	public String getAns1() {
		return ans1;
	}


	public void setAns1(String ans1) {
		this.ans1 = ans1;
	}


	public String getAns2() {
		return ans2;
	}


	public void setAns2(String ans2) {
		this.ans2 = ans2;
	}


	public String getAns3() {
		return ans3;
	}


	public void setAns3(String ans3) {
		this.ans3 = ans3;
	}


	public String getMedicationsJSON() {
		return medicationsJSON;
	}



	public void setMedicationsJSON(String medicationsJSON) {
		this.medicationsJSON = medicationsJSON;
	}



	public Date getCheckinDate() {
		return checkinDate;
	}


	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}


   



    
}
