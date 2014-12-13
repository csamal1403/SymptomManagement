package com.android.capstone.doctor.alarm;

import java.util.Date;

import com.android.capstone.data.Checkin;
import com.android.capstone.utils.CommonUtils;

public class CheckPain {

	
	
	private String painStatus;
	private Long painStartTime;
	private Long painEndTime;
	
	private String cantEatStatus;
	private Long cantEatStartTime;
	private Long cantEatEndTime;
	
		
	
	public CheckPain(){
		painStatus = "";
		painStartTime = 0L;
	    painEndTime = 0L;
	    cantEatStatus = "";
	    cantEatStartTime = 0L;
	    cantEatEndTime = 0L;
	    
	}
	
	
	
	
	public void checkPainStatus(Checkin checkin){
		 if(checkin.getAns1().equals(CommonUtils.ANSWER_WELL_CONTROLLED)){
			if(painStatus.equals("")){
				painStatus = CommonUtils.ANSWER_WELL_CONTROLLED;
				
			}else if(painStatus.equals(CommonUtils.ANSWER_MODERATE) || painStatus.equals(CommonUtils.ANSWER_SEVERE)){
				painStatus = CommonUtils.ANSWER_WELL_CONTROLLED;
				painStartTime = 0L;
			}
		
		
		}else if(checkin.getAns1().equals(CommonUtils.ANSWER_MODERATE)){
			if(painStatus.equals("") || painStatus.equals(CommonUtils.ANSWER_WELL_CONTROLLED)){
				painStatus = CommonUtils.ANSWER_MODERATE;
				painStartTime = checkin.getCheckinDate().getTime();
				
			}
			
			
		}else if(checkin.getAns1().equals(CommonUtils.ANSWER_SEVERE)){
			if(painStatus.equals("") || painStatus.equals(CommonUtils.ANSWER_WELL_CONTROLLED)){
				painStatus = CommonUtils.ANSWER_SEVERE;
				painStartTime = checkin.getCheckinDate().getTime();
				
			}else if(painStatus.equals(CommonUtils.ANSWER_MODERATE)){
				painStatus = CommonUtils.ANSWER_SEVERE;
			}
		
		}
		
	}
	
	
	
	
	
	public void checkEatingStatus(Checkin checkin){
		 if(checkin.getAns2().equals(CommonUtils.ANSWER_NO)){
				if(cantEatStatus.equals("") || cantEatStatus.equals(CommonUtils.ANSWER_SOME)){
					cantEatStatus = CommonUtils.ANSWER_NO;
					
				}else if(cantEatStatus.equals(CommonUtils.ANSWER_CANT_EAT)){
					cantEatStatus = CommonUtils.ANSWER_NO;
					cantEatStartTime = 0L;
				}
			
			
		}else if(checkin.getAns2().equals(CommonUtils.ANSWER_SOME)){
			   if(cantEatStatus.equals("") || cantEatStatus.equals(CommonUtils.ANSWER_NO)){
				    cantEatStatus = CommonUtils.ANSWER_SOME;
				
			  }else if(cantEatStatus.equals(CommonUtils.ANSWER_CANT_EAT)){
				   cantEatStatus = CommonUtils.ANSWER_SOME;
				   cantEatStartTime = 0L;
			  } 
			
			
			
		}else if(checkin.getAns2().equals(CommonUtils.ANSWER_CANT_EAT)){
			 if(cantEatStatus.equals("") || cantEatStatus.equals(CommonUtils.ANSWER_NO) || cantEatStatus.equals(CommonUtils.ANSWER_SOME) ){
				    cantEatStatus = CommonUtils.ANSWER_CANT_EAT;
				    cantEatStartTime = checkin.getCheckinDate().getTime();
			  }
			
			
	   }
		
		
   }
	
	
	
	
	public String getPainStatus() {
		return painStatus;
	}

   
	public String getCantEatStatus() {
		return cantEatStatus;
	}


	

	public Long getTotalPainTime(){
		painEndTime = new Date().getTime();
		Long totalPainTime = painEndTime - painStartTime;
		return totalPainTime;
	}
	
	
    public Long getTotalCantEatTime(){
		cantEatEndTime = new Date().getTime();
		Long totalCantEatTime = cantEatEndTime - cantEatStartTime;
		return totalCantEatTime;
	}
	
	
    
    
    
}
