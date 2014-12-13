package com.android.capstone;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;



public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	
    OnDateSelectedListener mCallback;
	
	
	public interface OnDateSelectedListener { 
        public void onDateSelected(int year, int month, int day);
    } 
	
	
	
	
     @Override 
     public Dialog onCreateDialog(Bundle savedInstanceState) {

         final Calendar c = Calendar.getInstance();
         int year = c.get(Calendar.YEAR);
         int month = c.get(Calendar.MONTH);
         int day = c.get(Calendar.DAY_OF_MONTH);
 
         Dialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
         dialog.setTitle(" Date of Birth ");
        
         return dialog;
         
    } 

     
     
     @Override 
     public void onAttach(Activity activity) {
         super.onAttach(activity);
          
         try { 
             mCallback = (OnDateSelectedListener) activity;
         } catch (ClassCastException e) {
             throw new ClassCastException(activity.toString()
                     + " must implement OnDateSelectedListener"); 
         } 
     } 
     
     
     
     public void onDateSet(DatePicker view, int year, int month, int day) {
    	 if(view.isShown()){
     		mCallback.onDateSelected(year, month, day);
     	}
     } 
     
     
     
     
} 