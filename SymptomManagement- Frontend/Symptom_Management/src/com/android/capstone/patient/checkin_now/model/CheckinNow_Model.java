package com.android.capstone.patient.checkin_now.model;


import java.util.ArrayList;
import java.util.List;

import com.android.capstone.utils.CommonUtils;

import android.content.Context;

public class CheckinNow_Model extends AbstractWizardModel {
	
	
	
    public  CheckinNow_Model(Context context, List<String> medications) {
        super(context, medications);
    }

    
    
    
    
    @Override
    protected PageList onNewRootPageList(List<String> medications) {
    	
    	List<Page> checkinPages = new ArrayList<Page>();
    	
    	Page page_1 = new SingleFixedChoicePage(this, CommonUtils.QUESTION_1)
                        .setChoices(CommonUtils.ANSWER_WELL_CONTROLLED, CommonUtils.ANSWER_MODERATE, CommonUtils.ANSWER_SEVERE)
                        .setRequired(true);
    	checkinPages.add(page_1);
    	
    	Page page_2=   new SingleFixedChoicePage(this, CommonUtils.QUESTION_2)
                         .setChoices(CommonUtils.ANSWER_NO, CommonUtils.ANSWER_SOME, CommonUtils.ANSWER_CANT_EAT)
                         .setRequired(true);
    	checkinPages.add(page_2);
    	
    	
    	List<Page> medicationPages = new ArrayList<Page>();
    	for(String medication : medications){
    		   String medicationQuestion = "Did you take your " + medication + "?";
    		   Page medicationPage = new SingleFixedChoicePage(this, medicationQuestion)
                                        .setChoices(CommonUtils.ANSWER_YES, CommonUtils.ANSWER_NO)
                                        .setRequired(true);
    		    medicationPages.add(medicationPage);
    	}
    	
    	if(medicationPages.isEmpty()){ 
    		Page page_3 = new BranchPage(this, CommonUtils.QUESTION_3)
    		                 .addBranch(CommonUtils.ANSWER_YES)
                             .addBranch(CommonUtils.ANSWER_NO)
                             .setRequired(true);
    		checkinPages.add(page_3);
    		
    	}else{
    		Page[] medicationPagesArray = medicationPages.toArray(new Page[medicationPages.size()]);
    		Page page_3 = new BranchPage(this, CommonUtils.QUESTION_3)
                             .addBranch(CommonUtils.ANSWER_YES , medicationPagesArray)
                             .addBranch(CommonUtils.ANSWER_NO)
                             .setRequired(true);
            checkinPages.add(page_3);
        }
    	
    	Page[] checkinPagesArray = checkinPages.toArray(new Page[checkinPages.size()]);
    	
    	return new PageList(checkinPagesArray);
        
    }
    
    
    
    
    
}
