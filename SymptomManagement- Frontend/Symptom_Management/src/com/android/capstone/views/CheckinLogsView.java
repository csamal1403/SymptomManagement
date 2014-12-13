package com.android.capstone.views;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.capstone.data.Checkin;
import com.android.capstone.data.MedicationCheckinQA;
import com.android.capstone.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;



public class CheckinLogsView extends View{

	    

	private Paint  barPaint, labelPaint;
	
    private ArrayList<Checkin> checkins;
    ArrayList<String> questions;
    HashMap<String, String> currMeds;
    HashMap<String, String> currMedTimes;
    HashMap<String, Integer> ansColorMap;
    HashMap<String, Integer> medAnsColorMap;
    SimpleDateFormat sdf;
    Type type;
    Gson gson;
    
    public CheckinLogsView(Context context , AttributeSet attrs) {
        super(context , attrs);
        init();
   }
    
    public CheckinLogsView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

    public CheckinLogsView(Context context) {
		super(context);
		init();
	}



	private void init() {
		
        checkins = new ArrayList<Checkin>();
        currMeds = new HashMap<String, String>();
        currMedTimes = new HashMap<String, String>();
        questions = new ArrayList<String>();
               
        ansColorMap = new HashMap<String, Integer>();
        ansColorMap.put("Well Controlled", getResources().getColor(android.R.color.holo_green_light));
	    ansColorMap.put("Moderate", getResources().getColor(android.R.color.holo_orange_light));
	    ansColorMap.put("Severe", getResources().getColor(android.R.color.holo_red_light));
	    ansColorMap.put("No", getResources().getColor(android.R.color.holo_green_light));
	    ansColorMap.put("Some", getResources().getColor(android.R.color.holo_orange_light));
	    ansColorMap.put("I can't eat", getResources().getColor(android.R.color.holo_red_light));
	    
	    medAnsColorMap = new HashMap<String, Integer>();
	    medAnsColorMap.put("No", getResources().getColor(android.R.color.holo_red_light));
	    medAnsColorMap.put("Yes", getResources().getColor(android.R.color.holo_green_light));
        
        type = new TypeToken<List<MedicationCheckinQA>>() {}.getType();
        gson = new Gson();
        
        sdf = new SimpleDateFormat("h:mm a");
        
        barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        labelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        
        setFocusable(true);
        setFocusableInTouchMode(true);
   }

	
	

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int desiredWidth = 500;
        int desiredHeight = 200;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

    
        setMeasuredDimension(width, height);
    }
    
    
    
    
    
    

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    	
   	    if(!checkins.isEmpty()){
   	    	
   	       int left = getLeft();
   	       int right = getRight();
   	       int top = getTop();
   	       int height = getHeight();
            
        
           //Drawing Text
           int size = questions.size();
           int space_each = height/(size+2);
           int start = top;
           labelPaint.setColor(Color.GRAY);
           labelPaint.setTextSize(20);
           barPaint.setTextSize(15);
           
           for(int i=0; i< size; i++){
        	  start+= space_each;
              canvas.drawText(questions.get(i), left+10 , start, labelPaint);
          }
        
       
            int centerY = start + space_each;
            int centerX = left + 150;
            int axisWidth = (right-20)- centerX;
            int axisHeight = centerY - (top+10);
        
           //Drawing Axis
           barPaint.setColor(Color.LTGRAY);
           barPaint.setStrokeWidth(3);
           canvas.drawLine(centerX, centerY, centerX+axisWidth , centerY, barPaint); //X-axis
           canvas.drawLine(centerX, centerY ,centerX ,centerY-axisHeight , barPaint);  //Y-axis
       
       
        
        
           // Marking Axis and Draw Bars for Each Mark...
           labelPaint.setTextSize(15);
        
           int barWidth = space_each/2;
        
           int checkinSize = checkins.size();
           int checkinSpaceEach = (axisWidth)/(checkinSize);
           int markStart = centerX;
        
           int end = centerX;
           
           for(int i=0; i< checkinSize ; i++){
        	  
        	   barPaint.setColor(Color.LTGRAY);
               barPaint.setStrokeWidth(3);
            
               markStart+= checkinSpaceEach;
               
               if(!currMeds.isEmpty()){
            	   currMeds.clear();
               }
               
               List<MedicationCheckinQA>  medCheckinQAs = gson.fromJson(checkins.get(i).getMedicationsJSON(), type);
               if(medCheckinQAs != null ){
            	   for(MedicationCheckinQA qa : medCheckinQAs){
            	        String med = qa.getMedicationQuestion();
				        med = med.substring(med.lastIndexOf(" ")+1);
				        currMeds.put(med,qa.getMedicationAnswer());
				        String time = sdf.format(qa.getMedicationDate());
				        currMedTimes.put(med,time);
				   }
               }
               
               
               //Draw all Bars for this Checkin...
               start = top;
             
               for(int j=0; j< size; j++){
            	   int color = Color.LTGRAY;
            	   
            	   if(j==0){
            		  color = ansColorMap.get(checkins.get(i).getAns1());
            	   }else if(j==1){
            		  color = ansColorMap.get(checkins.get(i).getAns2());
            	   }else if(j==2){
            		  color = medAnsColorMap.get(checkins.get(i).getAns3());
            	   
            	   }else{
            		   if(!currMeds.isEmpty()){
            		       if(currMeds.keySet().contains(questions.get(j))){
            			       String ans =  currMeds.get(questions.get(j));
            			       color = medAnsColorMap.get(ans);
            		       }
            		   }
            	  }
            	   
            	   barPaint.setColor(color);
            	   start += space_each;
            	   canvas.drawRect(end, start-20,markStart ,start-20+(barWidth) , barPaint);
            	   
            	  
            	      
             }
            	   
               end = markStart; 
               
               //Draw Time Mark..
               String time = sdf.format(checkins.get(i).getCheckinDate());
               barPaint.setColor(Color.LTGRAY);
               canvas.drawLine(markStart, top+20, markStart, centerY+10, barPaint);
               canvas.drawText(time, markStart-40 , centerY+30, labelPaint);
                 
           }
               
               
   	    }        
       
        
    }

    
   
    public void setCheckins(ArrayList<Checkin> checkins){
    	this.checkins = checkins;
    	
    	questions.clear();
    	
    	questions.add("Pain");
	    questions.add("Eating");
	    questions.add("Medications");
	       
    	for(Checkin checkin : checkins){
	    	  List<MedicationCheckinQA>  medCheckinQAs = gson.fromJson(checkin.getMedicationsJSON(), type);
	    	  if(medCheckinQAs!= null){
	    	     for(MedicationCheckinQA qa : medCheckinQAs){
	    	    	String ques = qa.getMedicationQuestion();
				    ques = ques.substring(ques.lastIndexOf(" ")+1);
				    if(!questions.contains(ques)){
				    	questions.add(ques);
				    }
				    
			    }
	    	 }
	      }
	       
    	
        invalidate();
        
        
    }
    
   
  
 
}
	        
	        

	
	
	
