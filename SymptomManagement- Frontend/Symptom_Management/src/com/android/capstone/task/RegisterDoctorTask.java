package com.android.capstone.task;

import com.android.capstone.MainActivity;
import com.android.capstone.data.Doctor;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.RetrofitUtils;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class RegisterDoctorTask extends AsyncTask<Doctor, Void , Doctor>{


	private MainActivity activity;
	private ProgressDialog dialog;
	
	
	public RegisterDoctorTask(MainActivity activity) {
		super();
		this.activity = activity;
	}


	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(activity, "Registering ", "Please Wait... ", true);
	}
	
	
	

	protected Doctor doInBackground(Doctor... params) {
		
		String gcmRegId = "";
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity.getApplicationContext());
		try {
			gcmRegId = gcm.register(CommonUtils.SENDER_ID);
			Doctor d = params[0];
			d.setGCMregId(gcmRegId);
			d = new RetrofitUtils(activity).registerDoctor(d);
			new CommonUtils(activity.getApplicationContext()).saveDoctor(d);
			
			return d;
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
        
		return null;
		
	}





	protected void onPostExecute(Doctor result) {
		super.onPostExecute(result);
		
		dialog.setMessage("Done .. ");
		dialog.dismiss();
		
		if(activity != null && result != null){
			activity.registerDoctorTaskResult(result);
		}
		
	}
	
	
	

}
