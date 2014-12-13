package com.android.capstone.patient.checkin_now.model;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;

import com.android.capstone.patient.checkin_now.ui.SingleChoiceFragment;


public class SingleFixedChoicePage extends Page {
    protected ArrayList<String> mChoices = new ArrayList<String>();

    public SingleFixedChoicePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return SingleChoiceFragment.create(getKey());
    }
    
    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY), getKey()));
    }

    @Override
    public boolean isCompleted() {
        return !TextUtils.isEmpty(mData.getString(SIMPLE_DATA_KEY));
    }
    
    
    
    

    public String getOptionAt(int position) {
        return mChoices.get(position);
    }

    public int getOptionCount() {
        return mChoices.size();
    }

    

    public SingleFixedChoicePage setChoices(String... choices) {
        mChoices.addAll(Arrays.asList(choices));
        return this;
    }

    public SingleFixedChoicePage setValue(String value) {
        mData.putString(SIMPLE_DATA_KEY, value);
        return this;
    }
}
