package com.android.capstone.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class SymptomManagementContract {

	
    public static final String CONTENT_AUTHORITY = "com.android.capstone";
    
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_USER_INFO = UserInfoEntry.TABLE_NAME;
    public static final String PATH_MEDICATIONS = MedicationsEntry.TABLE_NAME ;
    public static final String PATH_CHECKINS= CheckinsEntry.TABLE_NAME;
    public static final String PATH_REMINDERS= RemindersEntry.TABLE_NAME;
   
    
   
    
    
    public static final class UserInfoEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_INFO).build();
        public static final String TABLE_NAME = "User_Info";
        
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_USER_INFO;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_USER_INFO;

        public static final String COLUMN_EMAIL_ID = "email_id";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_ABOUT = "about";
        public static final String COLUMN_PICTURE_URL = "picture_url";
        public static final String COLUMN_BIRTH_DATE = "birth_date";
        public static final String COLUMN_STATUS_PAIN = "pain_status";
        public static final String COLUMN_STATUS_CANT_EAT = "cant_eat";
        public static final String COLUMN_LAST_CHECKED = "last_checked";

        
        public static Uri buildUserInfoUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        
        
    }

    
    
 
    
    public static final class MedicationsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEDICATIONS).build();
        public static final String TABLE_NAME = "Medications";
        
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MEDICATIONS;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MEDICATIONS;
        

        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_MEDICATIONS_JSON = "medications_json";
        
        
        
        public static Uri buildMedicationsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        
        
        
    }
	
	
    
    
    
    public static final class CheckinsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CHECKINS).build();
        public static final String TABLE_NAME = "Checkins";
        
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CHECKINS;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CHECKINS;
        
        
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_ANS_1 = "ans1";
        public static final String COLUMN_ANS_2 = "ans2";
        public static final String COLUMN_ANS_3 = "ans3";
        public static final String COLUMN_MEDICATIONS_CHECKIN_JSON = "medications_checkin_json";
        public static final String COLUMN_CHECKIN_DATE = "checkin_date";
       
        
        
        public static Uri buildCheckinsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    
    
    }    
    
    
    public static final class RemindersEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REMINDERS).build();
        public static final String TABLE_NAME = "Reminders";
        
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_REMINDERS;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_REMINDERS;
        
        
        public static final String COLUMN_REMINDER_TIME = "reminder_time";
       
        public static Uri buildRemindersUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        
        
    
    }    
    
    
    
    
	
	
	
	
}
