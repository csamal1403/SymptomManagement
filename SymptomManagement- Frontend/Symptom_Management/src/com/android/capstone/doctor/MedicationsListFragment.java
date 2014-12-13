package com.android.capstone.doctor;

import com.android.capstone.BaseActivity;
import com.android.capstone.R;
import com.android.capstone.data.Medications;
import com.android.capstone.task.AddMedicationsTask;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.views.FloatingActionButton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;



public class MedicationsListFragment extends Fragment implements ListView.MultiChoiceModeListener{

	ListView medicationsList;
	MedicationsListAdapter mAdapter;
	FloatingActionButton plusFabButton;
	EditText addMedication;
	
    
	Long patientId, doctorId;
	
	
	
	public MedicationsListFragment(Long doctorId, Long patientId){
		this.doctorId = doctorId;
		this.patientId = patientId;
	}
	
	
	
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_medications_list, container, false);
		
		medicationsList = (ListView) view.findViewById(R.id.MedicationsList);
		mAdapter = new MedicationsListAdapter(getActivity());
		medicationsList.setAdapter(mAdapter);
		medicationsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		medicationsList.setMultiChoiceModeListener(this);
	   
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		loadMedications();
		
		return view;
		
	}
	
	
	
	
	
	@Override
	public void onPause() {
		super.onPause();
		((ViewManager)plusFabButton.getParent()).removeView(plusFabButton);
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		createPlusFabButton();
	}
	
	
	
	

	private void loadMedications() {
		mAdapter.setMedications(new CommonUtils(getActivity()).getMedicationsById(patientId));
		
	}

	
	



	private void createPlusFabButton() {
	     plusFabButton = new FloatingActionButton.Builder(getActivity())
		           .withDrawable(getResources().getDrawable(R.drawable.ic_content_add))
		           .withButtonColor(getResources().getColor(R.color.theme_primary))
		           .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
		           .withMargins(0, 0, 25, 25)
		           .create();
		
		plusFabButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				medicationsList.startActionMode(new ActionMode.Callback() {
					
					@Override
					public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
						return false;
					}
					
					@Override
					public void onDestroyActionMode(ActionMode mode) {
					}
					
					
					@Override
					public boolean onCreateActionMode(ActionMode mode, Menu menu) {
						mode.getMenuInflater().inflate(R.menu.medication_list_add, menu);
						return true;
					}
					
					@Override
					public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
						switch (item.getItemId()) {
			             
			              case R.id.action_medications_save_cloud:
			            	   Medications m = new Medications(doctorId, patientId, mAdapter.getMedications());
			            	   BaseActivity activity = (BaseActivity) getActivity();
			            	   if(activity.isOnline()){
			            		   new AddMedicationsTask(MedicationsListFragment.this, mode).execute(m);
			        		   }else{
			        			  Toast.makeText(getActivity(), "Internet Connection Not Available.. Could not Update Medications.. ", Toast.LENGTH_LONG).show();
			        			  loadMedications();
			        			  mode.finish();
			        		   } 

			            	   return true; 
			                  
			             default: 
			                  return false; 
			      } 
					
					      
					}
			});
				
			new AddMedicationsDialogFragment().show(getFragmentManager(),"Medication Dialog ");
		}
	});
		
		
  }
	
	
	
	
	

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		
		switch (item.getItemId()) {
             case R.id.action_medications_discard:
            	// Calls getSelectedIds method from ListViewAdapter Class
                 SparseBooleanArray selected = mAdapter.getSelectedIds();
                 // Captures all selected ids with a loop
                 for (int i = (selected.size() - 1); i >= 0; i--) {
                     if (selected.valueAt(i)) {
                         String selectedMed = mAdapter.getItem(selected.keyAt(i));
                         // Remove selected items following the ids
                         mAdapter.remove(selectedMed);
                     }
                 }
    
                  return true; 
                  
             
             case R.id.action_medications_save_cloud:
            	   Medications m = new Medications(doctorId, patientId, mAdapter.getMedications());
            	   BaseActivity activity = (BaseActivity) getActivity();
            	   if(activity.isOnline()){
            		   new AddMedicationsTask(MedicationsListFragment.this, mode).execute(m);
        		   }else{
        			  Toast.makeText(getActivity(), "Internet Connection Not Available.. Could not Add Medications.. ", Toast.LENGTH_LONG).show();
        			  loadMedications();
        			  mode.finish();
        		   } 
     
                   return true; 
                  
              
             default: 
                  return false; 
    } 
		
	}




	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		plusFabButton.hideFloatingActionButton();
		mode.getMenuInflater().inflate(R.menu.medication_list_context_menu, menu);
		return true;
	}



	@Override
	public void onDestroyActionMode(ActionMode mode) {
		plusFabButton.showFloatingActionButton();
		mAdapter.removeSelection();
	}



	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}





	@Override
	public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
		final int checkedCount = medicationsList.getCheckedItemCount();
        mode.setTitle(checkedCount + " Selected");
        mAdapter.toggleSelection(position);
	}
		
		
	

	
	
	
	
	private class AddMedicationsDialogFragment extends DialogFragment{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			
			 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			 LayoutInflater mInflater = getActivity().getLayoutInflater();
			 View addMedicationView = mInflater.inflate(R.layout.add_medication_dialog_layout, null);
				builder.setTitle("Add a Medication ");
				builder.setView(addMedicationView);
				builder.setPositiveButton(" Add ", new DialogInterface.OnClickListener() {
				             
				        	   @Override
				               public void onClick(DialogInterface dialog, int id) {
				        		  addMedication = (EditText) AddMedicationsDialogFragment.this.getDialog().findViewById(R.id.et_addMedication);
				                  final String med = addMedication.getEditableText().toString();
				                  mAdapter.add(med);
				                
				        	   }
				           });
				 
				
				builder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener() {
				               public void onClick(DialogInterface dialog, int id) {
				                  dialog.cancel(); 
				            	}
				          
				       });
			return builder.create();
		}
	 }





	public void AddMedicationsTaskResult(Medications result) {
		loadMedications();
	}

	
	
	
	
	
	
}
