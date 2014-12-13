package com.android.capstone.task;

import com.android.capstone.data.Medications;
import com.android.capstone.doctor.MedicationsListFragment;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.RetrofitUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.ActionMode;

public class AddMedicationsTask extends AsyncTask<Medications, Void, Medications> {

	
	private MedicationsListFragment mFragment;
    private ActionMode mode;
    private ProgressDialog dialog;
	
	
	
	public AddMedicationsTask(MedicationsListFragment mFragment, ActionMode mode) {
		super();
		this.mFragment = mFragment;
		this.mode = mode;
	}


	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog = ProgressDialog.show(mFragment.getActivity(), "Updating Medications List .. ", "Please Wait... ", true);
	}
	
	
	
	protected Medications doInBackground(Medications... params) {
		
		try {
			Medications med = new RetrofitUtils(mFragment.getActivity()).addMedications(params[0]);
			new CommonUtils(mFragment.getActivity()).saveMedications(med, true);
			return med;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}



	
	
	protected void onPostExecute(Medications result) {
		super.onPostExecute(result);
		
		dialog.setMessage("Done .. ");
		dialog.dismiss();
		
		if(mFragment != null && result != null){
			mFragment.AddMedicationsTaskResult(result);
			mode.finish();
		}
		
		
	}
	
	
	
	

}
