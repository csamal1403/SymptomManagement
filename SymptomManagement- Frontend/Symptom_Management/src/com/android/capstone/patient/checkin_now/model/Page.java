package com.android.capstone.patient.checkin_now.model;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;


public abstract class Page implements PageTreeNode {
    
    public static final String SIMPLE_DATA_KEY = "_";

    protected ModelCallbacks mCallbacks;

    protected Bundle mData = new Bundle();
    protected String mTitle;
    protected boolean mRequired = false;
    protected String mParentKey;

    protected Page(ModelCallbacks callbacks, String title) {
        mCallbacks = callbacks;
        mTitle = title;
    }

    
    
    public abstract Fragment createFragment();
    public abstract void getReviewItems(ArrayList<ReviewItem> dest);
    
    
    
    
    @Override
    public Page findByKey(String key) {
        return getKey().equals(key) ? this : null;
    }
    
    public String getKey() {
        return (mParentKey != null) ? mParentKey + ":" + mTitle : mTitle;
    }

    

    @Override
    public void flattenCurrentPageSequence(ArrayList<Page> dest) {
        dest.add(this);
    }

   
    
    
    
    

    void setParentKey(String parentKey) {
        mParentKey = parentKey;
    }

    
    public Bundle getData() {
        return mData;
    }

    public String getTitle() {
        return mTitle;
    }

    public boolean isRequired() {
        return mRequired;
    }

    public Page setRequired(boolean required) {
        mRequired = required;
        return this;
    }

    
    
    
    
   
    public boolean isCompleted() {
        return true;
    }

    public void resetData(Bundle data) {
        mData = data;
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        mCallbacks.onPageDataChanged(this);
    }

   
}
