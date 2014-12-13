package com.android.capstone.patient;

import com.android.capstone.R;
import com.android.capstone.db.SymptomManagementContract.UserInfoEntry;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.SharedPrefUtils;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;




public class PatientMain_Fragment extends Fragment implements OnItemClickListener, LoaderCallbacks<Cursor>{

	Context mContext;
	Patient_Main_CursorAdapter mAdapter;
	SharedPrefUtils sharedPrefUtils;
	
	
	ListView doctorsList;
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        
		  mContext = getActivity();
		  mAdapter = new Patient_Main_CursorAdapter(getActivity(), null, 0);
		  sharedPrefUtils = new SharedPrefUtils(mContext);
        	
	      View rootView = inflater.inflate(R.layout.patient_main_fragment, container, false);
		  doctorsList = (ListView) rootView.findViewById(R.id.DoctorsList);
		  doctorsList.setAdapter(mAdapter);
		  doctorsList.setOnItemClickListener(this);
		  
		  getLoaderManager().initLoader(0, null, this);	
		 
		  
        return rootView;
    }


	
	

    @Override
    public void onResume() {
       super.onResume();
       getLoaderManager().restartLoader(0, null, this);
    }
    
    
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	super.onActivityCreated(savedInstanceState);
    	int remindersCount = new CommonUtils(getActivity()).getRemindersCount();
    	if(remindersCount < 4){
    		if(! sharedPrefUtils.isDoctor()){
				Intent intent = new Intent(getActivity(), RemindersActivity.class);
				Bundle translateBundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
				startActivity(intent, translateBundle);	
			}
    	}
    	
    	
    }
    

    
    
    
    // List Item OnClick Handler ...
    
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		
		Long doctorId = null;
		
		Cursor cursor = mAdapter.getCursor();
        if (cursor != null && cursor.moveToPosition(position)) {
        	doctorId = cursor.getLong(cursor.getColumnIndex(UserInfoEntry._ID));
        }
        
        Intent intent = new Intent(getActivity(), CheckinNowActivity.class);
		intent.putExtra("doctorId", doctorId);
		  
		Bundle translateBundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
		startActivity(intent, translateBundle);	
		 
		
	}




	
	
	
	// Loader Callbacks ...
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		return new CursorLoader(
                mContext,   // Parent activity context
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
		
	}




	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		mAdapter.swapCursor(null);
	}

	
	
	
	
}
