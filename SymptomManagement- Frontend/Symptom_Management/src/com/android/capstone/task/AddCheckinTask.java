package com.android.capstone.task;

import com.android.capstone.data.Checkin;
import com.android.capstone.patient.CheckinNowActivity;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.RetrofitUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AddCheckinTask extends AsyncTask<Checkin, Void, Checkin> {

	
    private CheckinNowActivity mActivity;
    private ProgressDialog dialog;
    
	
	public AddCheckinTask(CheckinNowActivity activity) {
		this.mActivity = activity;
	}


	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(mActivity, "Adding Checkin ", "Please Wait... ", true);
	}
	
	

	protected Checkin doInBackground(Checkin... params) {
		
        try {
			Checkin checkin = new RetrofitUtils(mActivity).addCheckin(params[0]);
			new CommonUtils(mActivity).saveCheckin(checkin, false);
			return checkin;
		
        } catch (Exception e) {
			e.printStackTrace();
		}
        
        return null;
        
 	}



	
	
	protected void onPostExecute(Checkin result) {
		super.onPostExecute(result);
		
		dialog.setMessage("Done .. ");
		dialog.dismiss();
		
		if(mActivity != null && result != null){
			mActivity.AddCheckinTaskResult(result);
		}
		
		
	}

	
	
	
	
	
	
}
