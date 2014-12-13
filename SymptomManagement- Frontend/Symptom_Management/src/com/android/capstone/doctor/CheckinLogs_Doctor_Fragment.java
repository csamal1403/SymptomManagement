package com.android.capstone.doctor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.android.capstone.R;
import com.android.capstone.data.Checkin;
import com.android.capstone.utils.CommonUtils;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;



public class CheckinLogs_Doctor_Fragment extends Fragment implements OnItemClickListener{

	

	Long doctorId;
	Long patientId;
	
	ListView checkinList;
	CheckinLogsListAdapter mAdapter;
	
	
	
	public CheckinLogs_Doctor_Fragment(Long doctorId, Long patientId){
		this.doctorId = doctorId;
		this.patientId = patientId;
	}
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_checkin_logs_doctor, container, false);
		checkinList = (ListView) view.findViewById(R.id.list_checkinLogs_Doctor);
		mAdapter = new CheckinLogsListAdapter(getActivity());
		checkinList.setAdapter(mAdapter);
		checkinList.setOnItemClickListener(this);
		
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		loadCheckinDates();
        return view;
		
   }
	
	
	
	private void loadCheckinDates(){
		ArrayList<Checkin> result = new CommonUtils(getActivity()).getCheckinsById(doctorId, patientId, "DESC");
		ArrayList<String> dates = new ArrayList<String>();
		
		for(Checkin checkin : result){
			String date = new SimpleDateFormat("EEE, d MMM yyyy").format(checkin.getCheckinDate());
			if(!dates.contains(date)){
				dates.add(date);
			}
            
       }
		
		mAdapter.addAll(dates);
		
		
	 }




	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		 String date = mAdapter.getItem(position);
		 Intent intent = new Intent(getActivity(), DisplayCheckinActivity.class);
		 intent.putExtra("doctorId", doctorId);
		 intent.putExtra("patientId", patientId);
		 intent.putExtra("date", date);
		 
		 Bundle translateBundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
		 startActivity(intent, translateBundle);	
		 
	}
	
	
	
	
	
	
	
	
	
}
