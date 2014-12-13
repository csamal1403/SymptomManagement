package com.android.capstone.task;

import com.android.capstone.MainActivity;
import com.android.capstone.data.Patient;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.RetrofitUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class RegisterPatientTask extends AsyncTask<Patient, Void , Patient> {
    
	private MainActivity activity;
	private ProgressDialog dialog;
	
	
	
	public RegisterPatientTask(MainActivity activity) {
		super();
		this.activity = activity;
	}


	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(activity, "Registering ", "Please Wait... ", true);
	}
	
	


	protected Patient doInBackground(Patient... params) {
		
		String gcmRegId = "";
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity.getApplicationContext());
		try {
			gcmRegId = gcm.register(CommonUtils.SENDER_ID);
			Patient p = params[0];
			p.setGCMregId(gcmRegId);
			p = new RetrofitUtils(activity).registerPatient(p);
			new CommonUtils(activity.getApplicationContext()).savePatient(p);
		
			return p;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	  return null;
		
	}




	
	protected void onPostExecute(Patient result) {
		super.onPostExecute(result);
			
		dialog.setMessage("Done .. ");
		dialog.dismiss();
		
		if(activity != null && result != null){
			activity.registerPatientTaskResult(result);
		}
		
	}

	
	
	
	

}
