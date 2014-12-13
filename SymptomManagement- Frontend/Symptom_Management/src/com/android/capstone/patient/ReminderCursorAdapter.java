package com.android.capstone.patient;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.android.capstone.R;
import com.android.capstone.db.SymptomManagementContract.RemindersEntry;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ReminderCursorAdapter extends CursorAdapter {

    
    private SimpleDateFormat sdf;
	
	public static class ViewHolder {
        public final TextView tvReminderTime;
        

        public ViewHolder(View view) {
        	tvReminderTime = (TextView) view.findViewById(R.id.tv_reminderListItem);
        }
    }

	
	
	
	
	public ReminderCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		sdf = new SimpleDateFormat("h:mm a");
	}

	
	
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		Long time = cursor.getLong(cursor.getColumnIndex(RemindersEntry.COLUMN_REMINDER_TIME));
		Date date = new Date(time);
		String reminder = sdf.format(date);
		viewHolder.tvReminderTime.setText(reminder);
    }

	
	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.reminders_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
		return view;
	}
	
	

}
