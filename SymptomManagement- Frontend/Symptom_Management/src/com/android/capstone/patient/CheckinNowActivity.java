package com.android.capstone.patient;


import android.app.DialogFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.android.capstone.R;
import com.android.capstone.data.Checkin;
import com.android.capstone.data.MedicationCheckinQA;
import com.android.capstone.patient.TimePickerFragment.OnTimeSelectedListener;
import com.android.capstone.patient.checkin_now.model.AbstractWizardModel;
import com.android.capstone.patient.checkin_now.model.ModelCallbacks;
import com.android.capstone.patient.checkin_now.model.Page;
import com.android.capstone.patient.checkin_now.model.ReviewItem;
import com.android.capstone.patient.checkin_now.ui.PageFragmentCallbacks;
import com.android.capstone.patient.checkin_now.ui.ReviewFragment;
import com.android.capstone.patient.checkin_now.ui.StepPagerStrip;
import com.android.capstone.patient.checkin_now.model.CheckinNow_Model;
import com.android.capstone.task.AddCheckinTask;
import com.android.capstone.utils.CommonUtils;
import com.android.capstone.utils.SharedPrefUtils;
import com.google.gson.Gson;






public class CheckinNowActivity extends FragmentActivity implements PageFragmentCallbacks, 
                                ReviewFragment.Callbacks,ModelCallbacks, OnTimeSelectedListener {

	
	
	 private ViewPager mPager;
	 private MyPagerAdapter mPagerAdapter;
	 private List<Page> mCurrentPageSequence;
	 private StepPagerStrip mStepPagerStrip;
	 private boolean mEditingAfterReview;
	 private boolean mConsumePageSelectedEvent;
	 private AbstractWizardModel mCheckinModel;
	 private ReviewFragment reviewFragment;
	    
	 private SharedPrefUtils sharedPrefUtils;
	 private Button mNextButton;
	 private Button mPrevButton;
	 private Long doctorId;
	 private Gson gson;
     private HashMap<Integer,Date> medTimes = new HashMap<Integer,Date>();
	 
	
	 
	 
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		gson = new Gson();
		setContentView(R.layout.fragment_checkin_now);
		getActionBar().show();
	
		doctorId = getIntent().getLongExtra("doctorId", 0);
        sharedPrefUtils = new SharedPrefUtils(this);
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //Getting Medications from Database ...
        mCheckinModel = new CheckinNow_Model(this, new CommonUtils(this).getMedicationsById(doctorId));
     
        if (savedInstanceState != null) {
              mCheckinModel.load(savedInstanceState.getBundle("model"));
         }

        mCheckinModel.registerListener(this);
        
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {

                @Override
                public void onPageStripSelected(int position) {
                      position = Math.min(mPagerAdapter.getCount() - 1, position);
                      if (mPager.getCurrentItem() != position) {
                                mPager.setCurrentItem(position);
                         }
                 }
             });
        

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    
        	@Override
            public void onPageSelected(int position) {
                   mStepPagerStrip.setCurrentPage(position);

                   if (mConsumePageSelectedEvent) {
                        mConsumePageSelectedEvent = false;
                        return;
                    }

                   mEditingAfterReview = false;
                   updateBottomBar();
            }
        });

        
        mNextButton.setOnClickListener(new View.OnClickListener() {
   
        	@Override
            public void onClick(View view) {
                  if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                        
                	    // EXECUTE  ADD CHECKIN ASYNCTASK TO SUBMIT  CHECKIN TO DOCTOR ...
                	    
                	    List<ReviewItem> reviewItems = reviewFragment.getReviewItems();
                	    Checkin checkin = new Checkin(doctorId, sharedPrefUtils.getId() ,new Date());
                	    
                	    List<MedicationCheckinQA> medCheckinQAs = new ArrayList<MedicationCheckinQA>();
                	    
                	    for(int i=0; i < reviewItems.size(); i++){
                	    	ReviewItem item = reviewItems.get(i);
                	    	
                	    	if(i==0){
                	    		checkin.setAns1(item.getDisplayValue());
                	       }else if(i==1){
                	    		checkin.setAns2(item.getDisplayValue());
                	       }else if(i==2){
                	    	    checkin.setAns3(item.getDisplayValue());
                	      
                	       }else if(i>2){
                	    	    MedicationCheckinQA medCheckinQA = new MedicationCheckinQA();
                	    	    medCheckinQA.setMedicationQuestion(item.getTitle());
                	    	    medCheckinQA.setMedicationAnswer(item.getDisplayValue());
                	    	  
                	    	    medCheckinQA.setMedicationDate(medTimes.get(i));
                	    	    medCheckinQAs.add(medCheckinQA);
                	    	    
                	    	}
                	   }
                	     
                	     checkin.setMedicationsJSON(gson.toJson(medCheckinQAs));
                	    
                	     
                	    //EXECUTE ASYNCTASK TO ADD CHECKIN...
                	     if(isOnline()){
                	    	  new AddCheckinTask(CheckinNowActivity.this).execute(checkin);
              		    }else{
              			  Toast.makeText(CheckinNowActivity.this, "Internet Connection Not Available.. Could Not Add Checkin ", Toast.LENGTH_LONG).show();
              			  finish();
              		   } 

                	  
                	 
              //Write code to set medication time... before submitting and video..
            }else if ( mPager.getCurrentItem() > 2 && mPager.getCurrentItem() < mCurrentPageSequence.size()) {
            	DialogFragment newFragment = new TimePickerFragment(" Medication Time "); 
				newFragment.show(getFragmentManager(), "timePicker"); 
            }
                  
                  
             else {
            	if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                 }else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                  }
            	}
            	        
                    
               
            }
       });

        
        

        mPrevButton.setOnClickListener(new View.OnClickListener() {
    
        	@Override
            public void onClick(View view) {
                  mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
         });

        
        
        onPageTreeChanged();
        updateBottomBar();
        
        
  	}
	
	
	 
	 
    @Override
	public void onDestroy() {
		super.onDestroy();
		mCheckinModel.unregisterListener(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBundle("model", mCheckinModel.save());
	}
	
	
	
    


	@Override
	public void onPageTreeChanged() {
		mCurrentPageSequence = mCheckinModel.getCurrentPageSequence();
		recalculateCutOffPage();
		mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1);
		
		mPagerAdapter.notifyDataSetChanged();
		updateBottomBar();
	}

	
	private void updateBottomBar() {
		int position = mPager.getCurrentItem();
		if (position == mCurrentPageSequence.size()) {
			mNextButton.setText(R.string.finish);
			mNextButton.setBackgroundResource(R.drawable.finish_background);
			mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
		} else {
			mNextButton.setText(mEditingAfterReview ? R.string.review
					: R.string.next);
			mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
			TypedValue v = new TypedValue();
			getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
			mNextButton.setTextAppearance(this, v.resourceId);
			mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
		}

		mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
	}

	

	@Override
	public AbstractWizardModel onGetModel() {
		return mCheckinModel;
	}

	@Override
	public void onEditScreenAfterReview(String key) {
		for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
			if (mCurrentPageSequence.get(i).getKey().equals(key)) {
				mConsumePageSelectedEvent = true;
				mEditingAfterReview = true;
				mPager.setCurrentItem(i);
				updateBottomBar();
				break;
			}
		}
	}

	@Override
	public void onPageDataChanged(Page page) {
		if (page.isRequired()) {
			if (recalculateCutOffPage()) {
				mPagerAdapter.notifyDataSetChanged();
				updateBottomBar();
			}
		}
	}

	@Override
	public Page onGetPage(String key) {
		return mCheckinModel.findByKey(key);
	}

	private boolean recalculateCutOffPage() {
		int cutOffPage = mCurrentPageSequence.size() + 1;
		for (int i = 0; i < mCurrentPageSequence.size(); i++) {
			Page page = mCurrentPageSequence.get(i);
			if (page.isRequired() && !page.isCompleted()) {
				cutOffPage = i;
				break;
			}
		}

		if (mPagerAdapter.getCutOffPage() != cutOffPage) {
			mPagerAdapter.setCutOffPage(cutOffPage);
			return true;
		}

		return false;
	}

	
	
	
	
	
	
	
	// My Adapter for View Pager ...
	public class MyPagerAdapter extends FragmentStatePagerAdapter {
		private int mCutOffPage;
		private Fragment mPrimaryItem;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			if (i >= mCurrentPageSequence.size()) {
				reviewFragment = new ReviewFragment();
				return reviewFragment;
			}

			return mCurrentPageSequence.get(i).createFragment();
		}

		@Override
		public int getItemPosition(Object object) {
			
			if (object == mPrimaryItem) {
				return POSITION_UNCHANGED;
			}

			return POSITION_NONE;
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			super.setPrimaryItem(container, position, object);
			mPrimaryItem = (Fragment) object;
		}

		@Override
		public int getCount() {
			if (mCurrentPageSequence == null) {
				return 0;
			}
			return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
		}

		public void setCutOffPage(int cutOffPage) {
			if (cutOffPage < 0) {
				cutOffPage = Integer.MAX_VALUE;
			}
			mCutOffPage = cutOffPage;
		}

		public int getCutOffPage() {
			return mCutOffPage;
		}
	}






	public void AddCheckinTaskResult(Checkin result) {
		 finish();
	}



	public boolean isOnline() { 
	    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
	    return (networkInfo != null && networkInfo.isConnected());
	}  

	
	
	
	@Override
	public void onTimeSelected(int hourOfDay, int minute) {
		 Calendar cal = Calendar.getInstance();
		 cal.setTimeInMillis(System.currentTimeMillis());
		 cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
	     cal.set(Calendar.MINUTE, minute);
	     Date date = cal.getTime();
	     
	     medTimes.put(mPager.getCurrentItem(), date);
		 
		 if (mEditingAfterReview) {
             mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
        }else {
             mPager.setCurrentItem(mPager.getCurrentItem() + 1);
         }
 	}
 	   
	     
	
	
	
	
	
	@Override
    public void finish() {
    	super.finish();
    	overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
    	
    }





	
   
	
	
	
	
	
}
