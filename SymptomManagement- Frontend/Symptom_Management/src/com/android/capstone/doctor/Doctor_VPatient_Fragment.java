package com.android.capstone.doctor;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.capstone.BaseActivity;
import com.android.capstone.R;
import com.android.capstone.db.SymptomManagementContract.UserInfoEntry;

public class Doctor_VPatient_Fragment extends Fragment implements OnClickListener{

	TextView tvName, tvStatusPatient;
	ImageView userImage;
    Button checkinLogsButton, medicationsButton;
	
    Long patientId, doctorId;
	String vPatientName, pictureUrl;	
	
    
	
	public Doctor_VPatient_Fragment(){
		
	}
	
	
	
    public Doctor_VPatient_Fragment(Long doctorId, Long patientId, String vPatientName, String pictureUrl){
    	super();
    	this.doctorId = doctorId;
    	this.patientId = patientId;
    	this.vPatientName = vPatientName;
    	this.pictureUrl = pictureUrl;
    }
    
    
    

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
		View view = inflater.inflate(R.layout.fragment_doctor_vpatient, container, false);
		tvName = (TextView) view.findViewById(R.id.userName);
		tvStatusPatient = (TextView) view.findViewById(R.id.tv_status_Patient);
		userImage = (ImageView) view.findViewById(R.id.profileImage);
		checkinLogsButton = (Button) view.findViewById(R.id.btVPatientCheckinLogs);
		medicationsButton = (Button) view.findViewById(R.id.btVPatientMedications);
		checkinLogsButton.setOnClickListener(this);
		medicationsButton.setOnClickListener(this);
		
		tvName.setText(vPatientName);
		
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		BaseActivity activity = (BaseActivity) getActivity();
		if(activity.isOnline()){
		   new GetUserProfileImageTask().execute(pictureUrl);
		}
		
		
		return view;
	}


	
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Uri uri = UserInfoEntry.buildUserInfoUri(patientId);
		Cursor cursor = getActivity().getContentResolver().query(uri,null, null, null, null);
	     
	     if(cursor.moveToNext()){
	    	 String statusCantEat = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_STATUS_CANT_EAT));
	    	 String statusPain = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_STATUS_PAIN));
	         Long lastChecked = cursor.getLong(cursor.getColumnIndex(UserInfoEntry.COLUMN_LAST_CHECKED));
	         
	        
	         if(lastChecked == null || statusCantEat == null || statusPain == null){
	        	 tvStatusPatient.setText("\n Not Analysed yet.. ");
		         return; 
	         }
	         
	         String lastCheckedDate = new SimpleDateFormat("h:mm a").format(new Date(lastChecked));
	         String lastCheckedString = "Last Analysed  : " + lastCheckedDate;
	         
	         String statusString = "";
	         statusPain = "Pain Status  :   "  + statusPain;
	         statusString = "\n\n" + statusString + statusPain ;
	         
	         statusCantEat = "Eating Status  :   "  + statusCantEat;
		     statusString = statusString + "\n\n" + statusCantEat;
	         
	         String finalText = lastCheckedString + statusString;
	         
	         tvStatusPatient.setText(finalText);
	
	     }
	     
	     
	     cursor.close();
	     
	     
	     
	     
		
	}
	
	
	
	
	


	@Override
	public void onClick(View v) {
		
		if(v.getId() == R.id.btVPatientCheckinLogs){
		     Fragment fragment = new CheckinLogs_Doctor_Fragment(doctorId, patientId);
			 FragmentTransaction ft = getFragmentManager().beginTransaction();
			 ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right,R.animator.slide_out_right);
			 ft.replace(R.id.vPatientContainer, fragment);
			 ft.addToBackStack(null);
			 ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			 ft.commit();
			
		}else if(v.getId() == R.id.btVPatientMedications){
			 Fragment fragment = new MedicationsListFragment(doctorId, patientId);
			 FragmentTransaction ft = getFragmentManager().beginTransaction();
			 ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_left, R.animator.slide_in_right,R.animator.slide_out_right);
			 ft.replace(R.id.vPatientContainer, fragment);
			 ft.addToBackStack(null);
			 ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			 ft.commit();
			 
			
		}
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
  
	private class GetUserProfileImageTask extends AsyncTask<String, Void, Bitmap> {

	
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				String imageUrl = params[0];
				InputStream in = (InputStream) new URL(imageUrl).getContent();
				Bitmap bitmap = BitmapFactory.decodeStream(in);
				in.close();
				
				return bitmap;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		    return null;
		}



		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			
			if(result != null){
				userImage.setImageBitmap(result);
			}
			
		}
		

		
	}




	
	
	
	
}
