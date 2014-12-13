package com.android.capstone.doctor;

import java.util.ArrayList;
import java.util.List;

import com.android.capstone.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CheckinLogsListAdapter extends BaseAdapter {

	
	
	private final Context mContext;
	 private List<String> dates = new ArrayList<String>();
	 
	
	public CheckinLogsListAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}
	
	
	
	public void add(String date){
		dates.add(date);
		notifyDataSetChanged();
	}
	
		
	@Override
	public int getCount() {
		return dates.size();
	}

	
	
	@Override
	public String getItem(int position) {
		return dates.get(position);
	}

	
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

        String date = dates.get(position);
		
		if(convertView == null){
			LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.checkin_logs_list_item, null);
		}
		
		TextView view = (TextView) convertView.findViewById(R.id.tv_checkinLogsListItem);
		view.setText(date);
		
	    return convertView;
		
		
	}



	public void addAll(ArrayList<String> datesList) {
		this.dates = datesList;
		notifyDataSetChanged();
	}

	
	
	
	
	
	
	
}
