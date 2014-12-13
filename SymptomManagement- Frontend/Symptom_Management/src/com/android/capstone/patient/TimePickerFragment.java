package com.android.capstone.patient;

import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;


public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

	OnTimeSelectedListener mCallback;
	
	private String title;
	
	
	public interface OnTimeSelectedListener { 
        public void onTimeSelected(int hourOfDay, int minute);
    } 
	
	
	
	public TimePickerFragment(String title){
		this.title = title;
	}
	
	
	
	
    @Override 
    public Dialog onCreateDialog(Bundle savedInstanceState) {
          final Calendar c = Calendar.getInstance();
          int hour = c.get(Calendar.HOUR_OF_DAY);
          int minute = c.get(Calendar.MINUTE);

          Dialog dialog = new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
          dialog.setTitle(title);
          return dialog;
   
    } 


    
    
    @Override 
    public void onAttach(Activity activity) {
        super.onAttach(activity);
         
        try { 
            mCallback = (OnTimeSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimeSelectedListener"); 
        } 
    } 
    
    
  
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    	if(view.isShown()){
    		mCallback.onTimeSelected(hourOfDay, minute);
    	}
       
    } 
    
    
    

}