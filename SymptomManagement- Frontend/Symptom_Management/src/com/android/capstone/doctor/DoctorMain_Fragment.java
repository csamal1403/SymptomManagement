package com.android.capstone.doctor;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.capstone.BaseActivity;
import com.android.capstone.R;
import com.android.capstone.data.VPatient;
import com.android.capstone.db.SymptomManagementContract.UserInfoEntry;
import com.android.capstone.task.AddPatientTask;
import com.android.capstone.utils.SharedPrefUtils;
import com.android.capstone.views.FloatingActionButton;





public class DoctorMain_Fragment extends Fragment implements OnItemClickListener, LoaderCallbacks<Cursor>{

	
	FloatingActionButton plusFabButton;
	Doctor_Main_CursorAdapter mAdapter;
	SharedPrefUtils sharedPrefUtils;
	
	ListView patientsList;
	EditText addPatient;
	
	
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		  mAdapter = new Doctor_Main_CursorAdapter(getActivity(), null, 0);
		  sharedPrefUtils = new SharedPrefUtils(getActivity());
			
	      View rootView = inflater.inflate(R.layout.doctor_main_fragment, container, false);
		  patientsList = (ListView) rootView.findViewById(R.id.PatientsList);
		  patientsList.setAdapter(mAdapter);
		  patientsList.setOnItemClickListener(this);
	       
		  createPlusFabButton();
	
		  getLoaderManager().initLoader(0, null, this);	
		  
	      return rootView;
	   
	 }
	 
	
	 
	 
	    
	 // List Item OnClick Handler ...
		
	 public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		 String firstName = "", lastName="", pictureUrl = "";
		 Long patientId = null;
			
		 Cursor cursor = mAdapter.getCursor();
         if (cursor != null && cursor.moveToPosition(position)) {
         	patientId = cursor.getLong(cursor.getColumnIndex(UserInfoEntry._ID));
            firstName = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_FIRST_NAME));
         	lastName = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_LAST_NAME));
         	pictureUrl = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_PICTURE_URL));
         }
         
		String vPatientName = firstName + "   " + lastName;
			
		Intent intent = new Intent(getActivity(), Doctor_VPatient_Activity.class);
		intent.putExtra("patientId", patientId);
		intent.putExtra("pName",vPatientName);
		intent.putExtra("pictureUrl", pictureUrl);
			  
		Bundle translateBundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
		startActivity(intent, translateBundle);
			  
	 }

	 
	 
	 
	    @Override
	  public void onResume() {
	       super.onResume();
	       getLoaderManager().restartLoader(0, null, this);
	    }
	 
	 
	
	 private void createPlusFabButton() {
		
	     plusFabButton = new FloatingActionButton.Builder(getActivity())
		           .withDrawable(getResources().getDrawable(R.drawable.ic_content_add))
		           .withButtonColor(getResources().getColor(R.color.theme_primary))
		           .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
		           .withMargins(0, 0, 25, 25)
		           .create();
		
	    plusFabButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				showDialog();
				
			}
		});
	     
	     
	 }
	 
	 
		
		private void showDialog(){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			View addPatientView = View.inflate(getActivity(), R.layout.add_patient_dialog_layout, null);
			addPatient = (EditText) addPatientView.findViewById(R.id.et_addPatient);
			builder.setTitle("Add a Patient");
			builder.setView(addPatientView);
			builder.setPositiveButton(" Add ", new DialogInterface.OnClickListener() {
			             
			        	   @Override
			               public void onClick(DialogInterface dialog, int id) {
			                  final String pEmail = addPatient.getEditableText().toString();
			                  
			                  //AddPatient Task ...
			                  BaseActivity activity = (BaseActivity) getActivity();
			                  if(activity.isOnline()){
			                	  new AddPatientTask(DoctorMain_Fragment.this).execute(pEmail);
			       		     }else{
			       			   Toast.makeText(getActivity(), "Internet Connection Not Available.. Could not Add Patient.. ", Toast.LENGTH_LONG).show();
			       		    } 

			             }
			           })
			           .setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
			               public void onClick(DialogInterface dialog, int id) {
			                  
			            	  dialog.cancel(); 
			            	   
			               }
			           }).show(); 
			
		}

		
		
 
		public void AddPatientTaskResult(VPatient result) {
			
			
		}



		
		
      
        


		
		// Loader Callbacks ...

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			
			return new CursorLoader(
                    getActivity(),   // Parent activity context
                    UserInfoEntry.CONTENT_URI,        // Table to query
                    null,     // Projection to return
                    null,            // No selection clause
                    null,            // No selection arguments
                    null             // Default sort order
              );
			
		}




		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
			mAdapter.swapCursor(cursor);
			
			if(mAdapter.getCount() == 0){
				Toast.makeText(getActivity() , " Add Patient ", Toast.LENGTH_LONG).show();
			}
			
			
	   }




		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			mAdapter.swapCursor(null);
		}



		
		
		
	
}
