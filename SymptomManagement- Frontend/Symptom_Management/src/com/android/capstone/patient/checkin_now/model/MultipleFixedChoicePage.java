package com.android.capstone.patient.checkin_now.model;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import com.android.capstone.patient.checkin_now.ui.MultipleChoiceFragment;


public class MultipleFixedChoicePage extends SingleFixedChoicePage {
    public MultipleFixedChoicePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return MultipleChoiceFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        StringBuilder sb = new StringBuilder();

        ArrayList<String> selections = mData.getStringArrayList(Page.SIMPLE_DATA_KEY);
        if (selections != null && selections.size() > 0) {
            for (String selection : selections) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(selection);
            }
        }

        dest.add(new ReviewItem(getTitle(), sb.toString(), getKey()));
    }

    @Override
    public boolean isCompleted() {
        ArrayList<String> selections = mData.getStringArrayList(Page.SIMPLE_DATA_KEY);
        return selections != null && selections.size() > 0;
    }
}
