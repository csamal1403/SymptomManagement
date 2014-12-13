package com.android.capstone.patient.checkin_now.model;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;


public abstract class AbstractWizardModel implements ModelCallbacks {
    protected Context mContext;

    private List<ModelCallbacks> mListeners = new ArrayList<ModelCallbacks>();
    private PageList mRootPageList;

    public AbstractWizardModel(Context context, List<String> medications){
        mContext = context;
        mRootPageList = onNewRootPageList(medications);
    }

    
    
    
    protected abstract PageList onNewRootPageList(List<String> medications);
    
    

    @Override
    public void onPageDataChanged(Page page) {
        for (int i = 0; i < mListeners.size(); i++) {
            mListeners.get(i).onPageDataChanged(page);
        }
    }

    
    @Override
    public void onPageTreeChanged() {
        for (int i = 0; i < mListeners.size(); i++) {
            mListeners.get(i).onPageTreeChanged();
        }
    }
    
    

    public Page findByKey(String key) {
        return mRootPageList.findByKey(key);
    }

    public void load(Bundle savedValues) {
        for (String key : savedValues.keySet()) {
            mRootPageList.findByKey(key).resetData(savedValues.getBundle(key));
        }
    }

   
    public Bundle save() {
        Bundle bundle = new Bundle();
        for (Page page : getCurrentPageSequence()) {
            bundle.putBundle(page.getKey(), page.getData());
        }
        return bundle;
    }

    
    
    public List<Page> getCurrentPageSequence() {
        ArrayList<Page> flattened = new ArrayList<Page>();
        mRootPageList.flattenCurrentPageSequence(flattened);
        return flattened;
    }
    
    
    
    
    public void registerListener(ModelCallbacks listener) {
        mListeners.add(listener);
    }


    public void unregisterListener(ModelCallbacks listener) {
        mListeners.remove(listener);
    }
}
