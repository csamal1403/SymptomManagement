package com.android.capstone.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.android.capstone.data.Checkin;
import com.android.capstone.data.Doctor;
import com.android.capstone.data.Medications;
import com.android.capstone.data.Patient;
import com.android.capstone.data.VDoctor;
import com.android.capstone.data.VPatient;
import com.android.capstone.db.SymptomManagementContract.CheckinsEntry;
import com.android.capstone.db.SymptomManagementContract.MedicationsEntry;
import com.android.capstone.db.SymptomManagementContract.RemindersEntry;
import com.android.capstone.db.SymptomManagementContract.UserInfoEntry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


public class CommonUtils {

	public static final String LOG_TAG = "Symptom";
	public static final String SENDER_ID = "992384145132";
	
	public static final String QUESTION_1 = "How bad is your mouth pain/sore throat ?";
	public static final String ANSWER_WELL_CONTROLLED = "Well Controlled";
	public static final String ANSWER_MODERATE = "Moderate";
	public static final String ANSWER_SEVERE = "Severe";
	
	public static final String QUESTION_2 = "Does your pain stop you from eating/drinking ?";
	public static final String ANSWER_SOME = "Some";
	public static final String ANSWER_CANT_EAT = "I can't eat";
	
	public static final String QUESTION_3 = "Did you take your Pain Medications ?";
	
	public static final String ANSWER_YES = "Yes";
	public static final String ANSWER_NO = "No";
	
	
	
	
	private Context mContext;
    private Gson gson;
    private SharedPrefUtils sharedPrefUtils;
    
   

	public CommonUtils(Context mContext) {
		super();
		this.mContext = mContext;
		gson = new Gson();
		sharedPrefUtils = new SharedPrefUtils(mContext);
	}
	
	
	
	
	public int getRemindersCount(){
		Cursor cursor = mContext.getContentResolver().query(RemindersEntry.CONTENT_URI,null, null, null, null);
		
		int count = 0;
		while(cursor.moveToNext()){
			count++;
		}
		
		cursor.close();
		return count;
	}
	
	
	
	public VPatient getPatientInfo(Long userId){
		Uri uri  = UserInfoEntry.buildUserInfoUri(userId);
		Cursor cursor = mContext.getContentResolver().query(uri,null, null, null, null);
		
		if(cursor.moveToFirst()){
			Long patientId = cursor.getLong(cursor.getColumnIndex(UserInfoEntry._ID));
			String patientEmailId = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_EMAIL_ID));
            String firstName = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_FIRST_NAME));
        	String lastName = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_LAST_NAME));
        	String pictureUrl = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_PICTURE_URL));
        	String about = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_ABOUT));
        	Long birthDate = cursor.getLong(cursor.getColumnIndex(UserInfoEntry.COLUMN_BIRTH_DATE));
        	
        	cursor.close();
			return new VPatient(patientId, patientEmailId, firstName, lastName,pictureUrl, about, new Date(birthDate));
		}
		
		cursor.close();
		return null;
	}
	
	
	
	public String getUserNameById(Long userId){
		String[] columns = {UserInfoEntry.COLUMN_FIRST_NAME};
		Uri uri  = UserInfoEntry.buildUserInfoUri(userId);
		Cursor cursor = mContext.getContentResolver().query(uri,columns, null, null, null);
		
		if(cursor.moveToFirst()){
			String name = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_FIRST_NAME));
			cursor.close();
			return name;
		}
		
		cursor.close();
		return null;
	}
	
	
	
	
	
	
	public HashMap<Long,ArrayList<Checkin>> getAllCheckins(){
		HashMap<Long,ArrayList<Checkin>> allCheckins = new HashMap<Long,ArrayList<Checkin>>();
		
		ArrayList<Long> userIds = getAllUserIds();
		for(Long userId : userIds){
			ArrayList<Checkin> checkins = getCheckinsById(sharedPrefUtils.getId(), userId, "ASC");
			allCheckins.put(userId, checkins);
		}
		
		return allCheckins;
		
	}
	
	
	
	public ArrayList<Long> getAllUserIds(){
		ArrayList<Long> userIds = new ArrayList<Long>();
	
		String[] columns = {UserInfoEntry._ID};
		Cursor cursor = mContext.getContentResolver().query(UserInfoEntry.CONTENT_URI,columns, null, null, null);
		
		while(cursor.moveToNext()){
			Long userId = cursor.getLong(cursor.getColumnIndex(UserInfoEntry._ID));
			userIds.add(userId);
		}
		
		cursor.close();
		return userIds;
		
	}
	
	
	
		
	public ArrayList<Checkin> getCheckinsById(Long clientId, Long userId, String sort){
		ArrayList<Checkin> checkins = new ArrayList<Checkin>();
		Uri uri = CheckinsEntry.buildCheckinsUri(userId);
		String sortOrder = CheckinsEntry.COLUMN_CHECKIN_DATE + " " + sort;
		Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, sortOrder);
		
		
		
		while(cursor.moveToNext()){
			Long checkinId = cursor.getLong(cursor.getColumnIndex(CheckinsEntry._ID));
			Long checkinUserId = cursor.getLong(cursor.getColumnIndex(CheckinsEntry.COLUMN_USER_ID));
			Long checkinDate = cursor.getLong(cursor.getColumnIndex(CheckinsEntry.COLUMN_CHECKIN_DATE));
			String ans1 = cursor.getString(cursor.getColumnIndex(CheckinsEntry.COLUMN_ANS_1));
			String ans2 = cursor.getString(cursor.getColumnIndex(CheckinsEntry.COLUMN_ANS_2));
			String ans3 = cursor.getString(cursor.getColumnIndex(CheckinsEntry.COLUMN_ANS_3));
			String medCheckinsJson = cursor.getString(cursor.getColumnIndex(CheckinsEntry.COLUMN_MEDICATIONS_CHECKIN_JSON));
			
	        Checkin ch = new Checkin(clientId, checkinUserId, new Date(checkinDate));
	        ch.setAns1(ans1);
	        ch.setAns2(ans2);
	        ch.setAns3(ans3);
	        ch.setMedicationsJSON(medCheckinsJson);
	        ch.setCheckinId(checkinId);
	        checkins.add(ch);
	    }
		
		cursor.close();
		return checkins;
	}
	
	
	
	
	public ArrayList<String> getMedicationsById(Long id){
		ArrayList<String> meds = new ArrayList<String>();
		Uri uri = MedicationsEntry.buildMedicationsUri(id);
		Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
		
		if(cursor.moveToFirst()){
			String medicationsJson = cursor.getString(cursor.getColumnIndex(MedicationsEntry.COLUMN_MEDICATIONS_JSON));
			Type type = new TypeToken<List<String>>() {}.getType();
	        meds = gson.fromJson(medicationsJson, type);
		}
		cursor.close();
		return meds;
	}
	
	
	
	public ArrayList<Date> getReminders(){
		ArrayList<Date> reminders = new ArrayList<Date>();
		String sortOrder = RemindersEntry.COLUMN_REMINDER_TIME + " ASC";
		Cursor cursor = mContext.getContentResolver().query(RemindersEntry.CONTENT_URI, null, null, null, sortOrder);
		
		while(cursor.moveToNext()){
			Long time = cursor.getLong(cursor.getColumnIndex(RemindersEntry.COLUMN_REMINDER_TIME));
			Date date = new Date(time);
			reminders.add(date);
		}
		
		cursor.close();
		return reminders;
	}
	
	
	
	
	public void saveReminder(Date date){
		if(date == null){
			return;
		}
		
		Long time = date.getTime();
		ContentValues values = new ContentValues();
		values.put(RemindersEntry.COLUMN_REMINDER_TIME, time);
		Uri uri = mContext.getContentResolver().insert(RemindersEntry.CONTENT_URI, values);
		
	}
	
	
	

	public void saveMedications(Medications med, boolean isDoctor){
		
		if(med == null){
			return;
		}
		
        ContentValues values = new ContentValues();
		values.put(MedicationsEntry._ID, med.getMedicationId());
		
		if(isDoctor){
		     values.put(MedicationsEntry.COLUMN_USER_ID, med.getPatientId());
		}else{
			 values.put(MedicationsEntry.COLUMN_USER_ID, med.getDoctorId());
		}
		
		values.put(MedicationsEntry.COLUMN_MEDICATIONS_JSON, gson.toJson(med.getMedications()));
		Uri uri = mContext.getContentResolver().insert(MedicationsEntry.CONTENT_URI, values);
	    
	}
	
	
	
	
   public void saveCheckins(ArrayList<Checkin> checkins, boolean isDoctor) {
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();
      
		if(checkins.isEmpty() || checkins == null){
			return;
		}
		
		for(Checkin c : checkins){
			ContentValues cv = new ContentValues();
			cv.put(CheckinsEntry._ID, c.getCheckinId());
			
			if(isDoctor){
				cv.put(CheckinsEntry.COLUMN_USER_ID, c.getPatientId());
		
			}else{
				cv.put(CheckinsEntry.COLUMN_USER_ID, c.getDoctorId());
			}
			
			cv.put(CheckinsEntry.COLUMN_ANS_1, c.getAns1());
			cv.put(CheckinsEntry.COLUMN_ANS_2, c.getAns2());
			cv.put(CheckinsEntry.COLUMN_ANS_3, c.getAns3());
			cv.put(CheckinsEntry.COLUMN_MEDICATIONS_CHECKIN_JSON, c.getMedicationsJSON());
			cv.put(CheckinsEntry.COLUMN_CHECKIN_DATE, c.getCheckinDate().getTime());
			
          values.add(cv);
		}
		
		int count = mContext.getContentResolver().bulkInsert(CheckinsEntry.CONTENT_URI, values.toArray(new ContentValues[values.size()]));
 
	}

   

   public void saveCheckin(Checkin checkin, boolean isDoctor) {
	    
	    if(checkin == null){
			return;
		}
	   
		ContentValues values = new ContentValues();
		values.put(CheckinsEntry._ID, checkin.getCheckinId());
		
		if(isDoctor){
			values.put(CheckinsEntry.COLUMN_USER_ID, checkin.getPatientId());
		}else{
			values.put(CheckinsEntry.COLUMN_USER_ID, checkin.getDoctorId());
		}
		
		values.put(CheckinsEntry.COLUMN_ANS_1, checkin.getAns1());
		values.put(CheckinsEntry.COLUMN_ANS_2, checkin.getAns2());
		values.put(CheckinsEntry.COLUMN_ANS_3, checkin.getAns3());
		values.put(CheckinsEntry.COLUMN_MEDICATIONS_CHECKIN_JSON, checkin.getMedicationsJSON());
		values.put(CheckinsEntry.COLUMN_CHECKIN_DATE, checkin.getCheckinDate().getTime());
		
		Uri uri = mContext.getContentResolver().insert(CheckinsEntry.CONTENT_URI, values);
		
	
	}
   
   
   

	public void savePatientInfo(VPatient p) {
		
		if(p == null){
			return;
		}
		
		ContentValues values = new ContentValues();
		values.put(UserInfoEntry._ID, p.getPatientId());
		values.put(UserInfoEntry.COLUMN_EMAIL_ID, p.getPatientEmailId());
		values.put(UserInfoEntry.COLUMN_FIRST_NAME, p.getFirstName());
		values.put(UserInfoEntry.COLUMN_LAST_NAME, p.getLastName());
		values.put(UserInfoEntry.COLUMN_PICTURE_URL, p.getPictureUrl());
		values.put(UserInfoEntry.COLUMN_ABOUT, p.getAbout());
		values.put(UserInfoEntry.COLUMN_BIRTH_DATE, p.getBirthDate().getTime());
		
		Uri uri = mContext.getContentResolver().insert(UserInfoEntry.CONTENT_URI, values);

	}
   
	
	public void saveDoctorInfos(ArrayList<VDoctor> doctors) {
		
		if(doctors.isEmpty() || doctors == null){
			return;
		}
		
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();

		for(VDoctor d : doctors){
			ContentValues cv = new ContentValues();
			cv.put(UserInfoEntry._ID, d.getDoctorId());
			cv.put(UserInfoEntry.COLUMN_EMAIL_ID, d.getDoctorEmailId());
			cv.put(UserInfoEntry.COLUMN_FIRST_NAME, d.getFirstName());
			cv.put(UserInfoEntry.COLUMN_LAST_NAME, d.getLastName());
			cv.put(UserInfoEntry.COLUMN_PICTURE_URL, d.getPictureUrl());
			cv.put(UserInfoEntry.COLUMN_ABOUT, d.getAbout());
			cv.put(UserInfoEntry.COLUMN_BIRTH_DATE, d.getBirthDate().getTime());
			values.add(cv);
		}
		
		int count = mContext.getContentResolver()
  		               .bulkInsert(UserInfoEntry.CONTENT_URI, values.toArray(new ContentValues[values.size()]));
		
	}
	
	

	public void savePatientInfos(ArrayList<VPatient> patients) {
		
		if(patients.isEmpty() || patients == null){
			return;
		}
		
		ArrayList<ContentValues> values = new ArrayList<ContentValues>();
		
		for(VPatient p : patients){
			ContentValues cv = new ContentValues();
			cv.put(UserInfoEntry._ID, p.getPatientId());
			cv.put(UserInfoEntry.COLUMN_EMAIL_ID, p.getPatientEmailId());
			cv.put(UserInfoEntry.COLUMN_FIRST_NAME, p.getFirstName());
			cv.put(UserInfoEntry.COLUMN_LAST_NAME, p.getLastName());
			cv.put(UserInfoEntry.COLUMN_PICTURE_URL, p.getPictureUrl());
			cv.put(UserInfoEntry.COLUMN_ABOUT, p.getAbout());
			cv.put(UserInfoEntry.COLUMN_BIRTH_DATE, p.getBirthDate().getTime());
		    values.add(cv);
		}
		
        int count = mContext.getContentResolver()
	    		      .bulkInsert(UserInfoEntry.CONTENT_URI, values.toArray(new ContentValues[values.size()]));

	}

	
	
	
	public void saveDoctor(Doctor d){
		
		if(d == null){
			return;
		}
		
		sharedPrefUtils.setDoctor(true);
		sharedPrefUtils.setClientId(d.getDoctorId());
		sharedPrefUtils.setGCMRegId(d.getGCMregId());
	}
	
    public void savePatient(Patient p){
    	
    	if(p == null){
			return;
		}
    	
	   sharedPrefUtils.setDoctor(false);
	   sharedPrefUtils.setClientId(p.getPatientId());
	   sharedPrefUtils.setGCMRegId(p.getGCMregId());
	}
	
	
	
	
   
	
	
}
