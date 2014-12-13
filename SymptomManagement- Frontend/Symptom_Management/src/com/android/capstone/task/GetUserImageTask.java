package com.android.capstone.task;

import java.io.InputStream;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

public class GetUserImageTask extends AsyncTask<String, Void, Void> {

		
	private LruCache<Long, Bitmap> imageCache;
	private ImageView imgView;
	private Long userId;
	
	
	public GetUserImageTask(Long userId, LruCache<Long, Bitmap> imageCache, ImageView imgView){
		this.userId = userId;
		this.imageCache = imageCache;
		this.imgView = imgView;
	}
	
	

	
	@Override
	protected Void doInBackground(String... params) {
		 
		try {
			String imageUrl = params[0];
			InputStream in = (InputStream) new URL(imageUrl).getContent();
			Bitmap bitmap = BitmapFactory.decodeStream(in);
			imageCache.put(userId, bitmap);
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	    return null;
	}




	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Bitmap bitmap = imageCache.get(userId);
		if(bitmap != null){
			imgView.setImageBitmap(bitmap);
		}
		
	}

	
	
	
	
	

}

