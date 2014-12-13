package com.android.capstone.db;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class SymptomManagementProvider extends ContentProvider {

	
	private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SymptomManagementDbHelper mOpenHelper;

    private static final int USER_INFO = 100;
    private static final int MEDICATIONS = 200;
    private static final int CHECKINS = 300;
    private static final int REMINDERS = 400;
    private static final int USER_INFO_ID = 101;
    private static final int MEDICATIONS_ID = 201;
    private static final int CHECKINS_ID = 301;

	
	
	
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SymptomManagementContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, SymptomManagementContract.PATH_USER_INFO, USER_INFO);
        matcher.addURI(authority, SymptomManagementContract.PATH_MEDICATIONS, MEDICATIONS);
        matcher.addURI(authority, SymptomManagementContract.PATH_CHECKINS, CHECKINS);
        matcher.addURI(authority, SymptomManagementContract.PATH_REMINDERS, REMINDERS);
        
        matcher.addURI(authority, SymptomManagementContract.PATH_USER_INFO + "/#", USER_INFO_ID);
        matcher.addURI(authority, SymptomManagementContract.PATH_MEDICATIONS + "/#", MEDICATIONS_ID);
        matcher.addURI(authority, SymptomManagementContract.PATH_CHECKINS + "/#", CHECKINS_ID);
        
       return matcher;
       
    }
    

    
    
	
	
	@Override
	public boolean onCreate() {
		mOpenHelper = new SymptomManagementDbHelper(getContext());
		return true;
	}

	
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        
        switch (match) {
            case USER_INFO:
                rowsDeleted = db.delete(SymptomManagementContract.UserInfoEntry.TABLE_NAME, selection, selectionArgs);
                break;
                
            case MEDICATIONS:
                rowsDeleted = db.delete(SymptomManagementContract.MedicationsEntry.TABLE_NAME, selection, selectionArgs);
                break;
                
            case CHECKINS:
                rowsDeleted = db.delete(SymptomManagementContract.CheckinsEntry.TABLE_NAME, selection, selectionArgs);
                break;  
            
            case REMINDERS:
                rowsDeleted = db.delete(SymptomManagementContract.RemindersEntry.TABLE_NAME, selection, selectionArgs);    
                break;
                
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        
        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
        
	}

	
	
	
	@Override
	public String getType(Uri uri) {
		
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case USER_INFO:
                return SymptomManagementContract.UserInfoEntry.CONTENT_TYPE;
            case MEDICATIONS:
                return SymptomManagementContract.MedicationsEntry.CONTENT_TYPE;
            case CHECKINS:
                return SymptomManagementContract.CheckinsEntry.CONTENT_TYPE;
            case REMINDERS:
                return SymptomManagementContract.RemindersEntry.CONTENT_TYPE;
            case MEDICATIONS_ID:
                return SymptomManagementContract.MedicationsEntry.CONTENT_ITEM_TYPE;
            case CHECKINS_ID:
                return SymptomManagementContract.CheckinsEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
		
	}

	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
        
            case USER_INFO: {
                long _id = db.replace(SymptomManagementContract.UserInfoEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = SymptomManagementContract.UserInfoEntry.buildUserInfoUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            
            case MEDICATIONS: {
                long _id = db.replace(SymptomManagementContract.MedicationsEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = SymptomManagementContract.MedicationsEntry.buildMedicationsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            
            case CHECKINS: {
                long _id = db.replace(SymptomManagementContract.CheckinsEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = SymptomManagementContract.CheckinsEntry.buildCheckinsUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            
            case REMINDERS: {
                long _id = db.replace(SymptomManagementContract.RemindersEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = SymptomManagementContract.RemindersEntry.buildRemindersUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
         }
        
        
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
        
    }
	

	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            
            case USER_INFO:
            {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SymptomManagementContract.UserInfoEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                 );
                break;
            }
            
            case MEDICATIONS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SymptomManagementContract.MedicationsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                 );
                break;
            }
          
            case CHECKINS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SymptomManagementContract.CheckinsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            
            
            case REMINDERS: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SymptomManagementContract.RemindersEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            
           
           
            case USER_INFO_ID: {
           	 retCursor = mOpenHelper.getReadableDatabase().query(
                       SymptomManagementContract.UserInfoEntry.TABLE_NAME,
                       projection,
                       SymptomManagementContract.UserInfoEntry._ID + " = '" + ContentUris.parseId(uri) + "'",
                       null,
                       null,
                       null,
                       sortOrder
               );
               break;
           }
            
            case MEDICATIONS_ID: {
            	 retCursor = mOpenHelper.getReadableDatabase().query(
                        SymptomManagementContract.MedicationsEntry.TABLE_NAME,
                        projection,
                        SymptomManagementContract.MedicationsEntry.COLUMN_USER_ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
           
            case CHECKINS_ID: {
            	retCursor = mOpenHelper.getReadableDatabase().query(
                        SymptomManagementContract.CheckinsEntry.TABLE_NAME,
                        projection,
                        SymptomManagementContract.CheckinsEntry.COLUMN_USER_ID + " = '" + ContentUris.parseId(uri) + "'",
                        null,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        
        
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
		
	}
	
	
	

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		
	        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
	        final int match = sUriMatcher.match(uri);
	        int rowsUpdated;

	        switch (match) {
	        
	            case USER_INFO:
	                rowsUpdated = db.update(SymptomManagementContract.UserInfoEntry.TABLE_NAME, values, selection, selectionArgs);
	                break;
	                
	            case MEDICATIONS:
	                rowsUpdated = db.update(SymptomManagementContract.MedicationsEntry.TABLE_NAME, values, selection, selectionArgs);
	                break;
	                
	            case CHECKINS:
	                rowsUpdated = db.update(SymptomManagementContract.CheckinsEntry.TABLE_NAME, values, selection, selectionArgs);
	                break;
	                
	            case REMINDERS:
	                rowsUpdated = db.update(SymptomManagementContract.RemindersEntry.TABLE_NAME, values, selection, selectionArgs);
	                break;
	                
	            default:
	                throw new UnsupportedOperationException("Unknown uri: " + uri);
	        }
	        
	        if (rowsUpdated != 0) {
	            getContext().getContentResolver().notifyChange(uri, null);
	        }
	   
	     return rowsUpdated;
		
	 }






	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		
		final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        
        switch (match) {
        
            case USER_INFO:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.replace(SymptomManagementContract.UserInfoEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
           
                
           case CHECKINS:
                db.beginTransaction();
                int returnCount1 = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.replace(SymptomManagementContract.CheckinsEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount1++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount1;
                
                
           case REMINDERS:
               db.beginTransaction();
               int returnCount2 = 0;
               try {
                   for (ContentValues value : values) {
                       long _id = db.replace(SymptomManagementContract.RemindersEntry.TABLE_NAME, null, value);
                       if (_id != -1) {
                           returnCount2++;
                       }
                   }
                   db.setTransactionSuccessful();
               } finally {
                   db.endTransaction();
               }
               getContext().getContentResolver().notifyChange(uri, null);
               return returnCount2;
            
            default:
                return super.bulkInsert(uri, values);
        }
        
	}
	
	
	
	
	
	
	
	
	
	
}
	

