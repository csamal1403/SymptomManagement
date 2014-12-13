package com.android.capstone.db;


import com.android.capstone.db.SymptomManagementContract.CheckinsEntry;
import com.android.capstone.db.SymptomManagementContract.MedicationsEntry;
import com.android.capstone.db.SymptomManagementContract.RemindersEntry;
import com.android.capstone.db.SymptomManagementContract.UserInfoEntry;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class SymptomManagementDbHelper extends SQLiteOpenHelper {

	
	private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "symptom_management.db";
	
	

	public SymptomManagementDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	    
    
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		 
        final String SQL_CREATE_USER_INFO_TABLE = "CREATE TABLE " + UserInfoEntry.TABLE_NAME + " (" +
                                                   UserInfoEntry._ID + " INTEGER PRIMARY KEY," +
                                                   UserInfoEntry.COLUMN_EMAIL_ID + " TEXT NOT NULL, " +
                                                   UserInfoEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                                                   UserInfoEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                                                   UserInfoEntry.COLUMN_ABOUT + " TEXT, " +
                                                   UserInfoEntry.COLUMN_PICTURE_URL + " TEXT, " +
                                                   UserInfoEntry.COLUMN_BIRTH_DATE + " INTEGER NOT NULL, " +
                                                   UserInfoEntry.COLUMN_STATUS_PAIN + " TEXT, " +
                                                   UserInfoEntry.COLUMN_STATUS_CANT_EAT + " TEXT, " +
                                                   UserInfoEntry.COLUMN_LAST_CHECKED + " INTEGER, " +
                                                   "UNIQUE (" + UserInfoEntry._ID +") ON CONFLICT REPLACE);" ;

        
        
        final String SQL_CREATE_MEDICATIONS_TABLE = "CREATE TABLE " + MedicationsEntry.TABLE_NAME + " (" +
                                                    MedicationsEntry._ID + " INTEGER PRIMARY KEY," +
                                                    MedicationsEntry.COLUMN_USER_ID+ " INTEGER NOT NULL, " +
                                                    MedicationsEntry.COLUMN_MEDICATIONS_JSON + " TEXT NOT NULL, " +
                                                    " FOREIGN KEY (" + MedicationsEntry.COLUMN_USER_ID + ") REFERENCES " + 
                                                    UserInfoEntry.TABLE_NAME + " (" + UserInfoEntry._ID + "), " +
                                                    " UNIQUE (" + MedicationsEntry._ID + ") ON CONFLICT REPLACE);" ;
        
        
        
        final String SQL_CREATE_CHECKINS_TABLE = "CREATE TABLE " + CheckinsEntry.TABLE_NAME + " (" +
        		                                 CheckinsEntry._ID + " INTEGER PRIMARY KEY," +
        		                                 CheckinsEntry.COLUMN_USER_ID+ " INTEGER NOT NULL, " +
        		                                 CheckinsEntry.COLUMN_ANS_1+ " TEXT NOT NULL, " +
        		                                 CheckinsEntry.COLUMN_ANS_2+ " TEXT NOT NULL, " +
        		                                 CheckinsEntry.COLUMN_ANS_3+ " TEXT NOT NULL, " +
        		                                 CheckinsEntry.COLUMN_MEDICATIONS_CHECKIN_JSON + " TEXT NOT NULL, " +
        		                                 CheckinsEntry.COLUMN_CHECKIN_DATE + " INTEGER NOT NULL, " +
                                                 " FOREIGN KEY (" + CheckinsEntry.COLUMN_USER_ID + ") REFERENCES " +
                                                 UserInfoEntry.TABLE_NAME + " (" + UserInfoEntry._ID + "), " +
                                                 " UNIQUE (" + CheckinsEntry._ID + ") ON CONFLICT REPLACE);" ;
        
        
        
        final String SQL_CREATE_REMINDERS_TABLE = "CREATE TABLE " + RemindersEntry.TABLE_NAME + " (" +
        		                                   RemindersEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        		                                   RemindersEntry.COLUMN_REMINDER_TIME + " INTEGER NOT NULL, " +
                                                  " UNIQUE (" + RemindersEntry.COLUMN_REMINDER_TIME + ") ON CONFLICT REPLACE);" ;

        
        
        
        db.execSQL(SQL_CREATE_USER_INFO_TABLE);
        db.execSQL(SQL_CREATE_MEDICATIONS_TABLE);
        db.execSQL(SQL_CREATE_CHECKINS_TABLE);
        db.execSQL(SQL_CREATE_REMINDERS_TABLE);

	}
	
	
	
	

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS " + UserInfoEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MedicationsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CheckinsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RemindersEntry.TABLE_NAME);
        onCreate(db);
		
	}

	
	
	
	
}
