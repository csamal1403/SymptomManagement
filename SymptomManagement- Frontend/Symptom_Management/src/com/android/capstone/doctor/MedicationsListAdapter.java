package com.android.capstone.doctor;

import java.util.ArrayList;
import java.util.List;

import com.android.capstone.R;
import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MedicationsListAdapter extends BaseAdapter {

	
	 private final Context mContext;
	 private List<String> medications = new ArrayList<String>();
	 private SparseBooleanArray mSelectedItemsIds;
	 
	
	
	public MedicationsListAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		mSelectedItemsIds = new SparseBooleanArray();
	}

	
	
	
	public void add(String medication){
		medications.add(medication);
		notifyDataSetChanged();
	}
	
	
	public void remove(String medication){
		medications.remove(medication);
		notifyDataSetChanged();
	}
	
	
	
	public List<String> getMedications() {
		return medications;
	}


	public void setMedications(List<String> medications) {
		this.medications = medications;
		notifyDataSetChanged();
	}



	public int getCount() {
		return medications.size();
	}


	public String getItem(int position) {
		return medications.get(position);
	}


	public long getItemId(int position) {
		return position;
	}

	
	public View getView(int position, View convertView, ViewGroup parent) {
	
        
		String medication = medications.get(position);
		
		if(convertView == null){
			LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.medications_list_item, null);
		}
		
		TextView view = (TextView) convertView.findViewById(R.id.tv_medicationsListItem);
		LinearLayout container = (LinearLayout) convertView.findViewById(R.id.container_medicationsListItem);
		view.setText(medication);
		
		if(mSelectedItemsIds.get(position)){
			container.setBackgroundColor(Color.rgb(102, 204, 255));
			view.setTextColor(Color.WHITE);
		}else{
			container.setBackgroundColor(Color.WHITE);
			view.setTextColor(Color.rgb(102, 204, 255));
		}
	  
	  return convertView;
		
	}
	
	
	
	 public void toggleSelection(int position) {
	        selectView(position, !mSelectedItemsIds.get(position));
	    }
	 
	    public void removeSelection() {
	        mSelectedItemsIds = new SparseBooleanArray();
	        notifyDataSetChanged();
	    }
	 
	    public void selectView(int position, boolean value) {
	        if (value)
	            mSelectedItemsIds.put(position, value);
	        else
	            mSelectedItemsIds.delete(position);
	        notifyDataSetChanged();
	    }
	 
	    public int getSelectedCount() {
	        return mSelectedItemsIds.size();
	    }
	 
	    public SparseBooleanArray getSelectedIds() {
	        return mSelectedItemsIds;
	    }
	

}
