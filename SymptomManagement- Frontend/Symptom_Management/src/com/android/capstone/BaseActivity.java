package com.android.capstone;

import com.android.capstone.patient.RemindersActivity;
import com.android.capstone.utils.SharedPrefUtils;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {

		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		SharedPrefUtils utils = new SharedPrefUtils(getApplicationContext());
		
		if(item.getItemId() == R.id.action_settings){
			if(! utils.isDoctor()){
				Intent intent = new Intent(getApplicationContext(), RemindersActivity.class);
				Bundle translateBundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
				startActivity(intent, translateBundle);	
			}
			
		}else if(item.getItemId() == android.R.id.home){
			 NavUtils.navigateUpFromSameTask(this);
		     return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	public boolean isOnline() { 
	    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}  

	
	
	
	
	
	
	
	
	
	
}
