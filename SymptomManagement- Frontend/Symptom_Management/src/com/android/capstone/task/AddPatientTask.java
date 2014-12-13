package com.android.capstone.task;

import com.android.capstone.data.VPatient;
import com.android.capstone.doctor.DoctorMain_Fragment;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.RetrofitUtils;
import com.android.capstone.utils.SharedPrefUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AddPatientTask extends AsyncTask<String, Void, VPatient> {

		
	private DoctorMain_Fragment doctorMainFragment;
	private SharedPrefUtils sharedPrefUtils;
	private ProgressDialog dialog;
	

	public AddPatientTask(DoctorMain_Fragment doctorMainFragment) {
		super();
		this.doctorMainFragment = doctorMainFragment;
		sharedPrefUtils = new SharedPrefUtils(doctorMainFragment.getActivity());
	}

	
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(doctorMainFragment.getActivity(), "Adding Patient ", "Please Wait... ", true);
	}
	
	
	
	
	protected VPatient doInBackground(String... params) {	
		VPatient p;
		try {
			p = new RetrofitUtils(doctorMainFragment.getActivity()).addPatientInfo(sharedPrefUtils.getId(), params[0]);
			new CommonUtils(doctorMainFragment.getActivity()).savePatientInfo(p);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}



	
	
	@Override
	protected void onPostExecute(VPatient result) {
		super.onPostExecute(result);
		
		dialog.setMessage("Done .. ");
		dialog.dismiss();
		
		if(doctorMainFragment != null && result != null){
			doctorMainFragment.AddPatientTaskResult(result);
		}
		
	}

	
	
	
	
}
