package com.coursera.capstone.checkin.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coursera.capstone.data.Checkin;
import com.coursera.capstone.data.ClientUtils;
import com.coursera.capstone.data.Doctor;
import com.coursera.capstone.data.DoctorUtils;
import com.coursera.capstone.data.Medications;
import com.coursera.capstone.data.Patient;
import com.coursera.capstone.data.PatientUtils;
import com.coursera.capstone.data.VDoctor;
import com.coursera.capstone.data.VPatient;




@Controller
public class CheckinSvc{

	
	
	
	 public static final String API_KEY = "AIzaSyBQrNoa9uiJ2VlXrC5oxuejPZcXgmzhTl8";
	
	
	
	
	//  ****************SERVICES  FOR  DOCTOR   **************************************//

	
	
	//  POST  REQUESTS  ....
	
	@RequestMapping(value = "/register/doctor" , method = RequestMethod.POST)
	@ResponseBody
	public Doctor registerDoctor(@RequestBody Doctor d) {
		return new DoctorUtils().registerDoctor(d);
	}

	
	
	
	@RequestMapping(value = "/{id}/patient" , method = RequestMethod.POST)
	@ResponseBody
    public VPatient addPatient(@PathVariable("id") long doctorId, @RequestParam("emailId") String pEmailId) {
		return new DoctorUtils().addPatient(doctorId , pEmailId);
	}

	

	
	@RequestMapping(value = "/doctor/medications" , method = RequestMethod.POST)
	@ResponseBody
    public Medications addMedications(@RequestBody Medications m) {
		return new DoctorUtils().addMedications(m);
	}





	
	
	//  GET  REQUESTS  .... 
	
	@RequestMapping(value = "/doctor/info" , method = RequestMethod.GET)
	@ResponseBody
	public Doctor getDoctorInfoByEmail(HttpServletRequest request) {
		String email = (String) request.getAttribute("email");
		return new DoctorUtils().getDoctorInfoByEmail(email);
	}

	
	

	@RequestMapping(value = "/{id}/patients" , method = RequestMethod.GET)
	@ResponseBody
    public Collection<VPatient> getPatients(@PathVariable("id") long doctorId) {
		return new DoctorUtils().getPatients(doctorId);
	}



   
	
	
	
	// **********************    SERVICES  FOR  PATIENT    ************************************//
	
	
	
	//  POST  REQUESTS  ...
	
	@RequestMapping(value = "/register/patient" , method = RequestMethod.POST)
	@ResponseBody
    public Patient registerPatient(@RequestBody Patient p) {
		return new PatientUtils().registerPatient(p);
	}

	
	
	
	@RequestMapping(value = "/patient/checkin" , method = RequestMethod.POST)
	@ResponseBody
	public Checkin addCheckin(@RequestBody Checkin ch) {
		return new PatientUtils().addCheckin(ch);
	}

	
	
	
	
	
	//  GET  REQUESTS  ....
	
	@RequestMapping(value = "/patient/info" , method = RequestMethod.GET)
	@ResponseBody
    public Patient getPatientInfoByEmail(HttpServletRequest request) {
		String email = (String) request.getAttribute("email");
		return new PatientUtils().getPatientInfoByEmail(email);
	}
	
	
	
	
	@RequestMapping(value = "/{id}/doctors" , method = RequestMethod.GET)
	@ResponseBody
	public Collection<VDoctor> getDoctors(@PathVariable("id") long patientId) {
		return new PatientUtils().getDoctors(patientId);
	}

	
	
	
	
	
	
	
	// ******************* SERVICES  FOR  BOTH  DOCTOR  &  PATIENT  *******************************//
	
	
    
	//  GET  REQUESTS  ...
	
	
	@RequestMapping(value = "/category" , method = RequestMethod.GET)
	@ResponseBody
	public String getClientCategory(HttpServletRequest request) {
		String email = (String) request.getAttribute("email");
		return new ClientUtils().getClientCategory(email);
		
	}
 
	

	@RequestMapping(value = "/medications" , method = RequestMethod.GET)
	@ResponseBody
    public Medications getMedicationsById(@RequestParam("dId") long doctorId,  @RequestParam("pId") long patientId) {
			return new ClientUtils().getMedicationsById(doctorId, patientId);
    }




	
	@RequestMapping(value = "/checkins" , method = RequestMethod.GET)
	@ResponseBody
	public List<Checkin> getCheckinsById(@RequestParam("dId") long doctorId,  @RequestParam("pId") long patientId) {
		   return new ClientUtils().getCheckinsById(doctorId, patientId);
	}




	



		
	


	
}
