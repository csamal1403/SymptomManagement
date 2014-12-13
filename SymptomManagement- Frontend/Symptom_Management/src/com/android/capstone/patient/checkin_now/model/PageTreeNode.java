package com.android.capstone.patient.checkin_now.model;

import java.util.ArrayList;


public interface PageTreeNode {
    public Page findByKey(String key);
    public void flattenCurrentPageSequence(ArrayList<Page> dest);
}
