package com.android.capstone.doctor;

import com.android.capstone.BaseActivity;
import com.android.capstone.R;
import com.android.capstone.db.SymptomManagementContract.UserInfoEntry;
import com.android.capstone.task.GetUserImageTask;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Doctor_Main_CursorAdapter extends CursorAdapter {
    
	private LruCache<Long, Bitmap> imageCache;
	private BaseActivity activity;
	
	public static class ViewHolder {
        public final TextView tvName;
        public final ImageView imgUser;


        public ViewHolder(View view) {
        	tvName = (TextView) view.findViewById(R.id.tv_patientListItem);
        	imgUser = (ImageView) view.findViewById(R.id.img_user);
        }
    }

	
	
	
	
	public Doctor_Main_CursorAdapter(Activity activity, Cursor c, int flags) {
		super(activity.getApplicationContext(), c, flags);
		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
		final int cacheSize = maxMemory / 8;
		imageCache = new LruCache<>(cacheSize);
		this.activity = (BaseActivity) activity;
		
	}

	
	
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
	    Long userId = cursor.getLong(cursor.getColumnIndex(UserInfoEntry._ID));
	    String firstName = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_FIRST_NAME));
		String lastName = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_LAST_NAME));
		String pictureUrl = cursor.getString(cursor.getColumnIndex(UserInfoEntry.COLUMN_PICTURE_URL));
		
		String name = firstName + "  " + lastName;
		viewHolder.tvName.setText(name);
		viewHolder.tvName.setTextColor(Color.GRAY);
		
		Bitmap bitmap = imageCache.get(userId);
		if(bitmap != null){
			viewHolder.imgUser.setImageBitmap(bitmap);
		
		}else{
			if(activity.isOnline()){
				new GetUserImageTask(userId, imageCache, viewHolder.imgUser).execute(pictureUrl);
			}
		
		}
		
		
		
    }

	
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.patient_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
		return view;
	}


	
}
