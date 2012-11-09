package com.quackware.tric.stats.SocialStats;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.quackware.tric.MyApplication;
import com.quackware.tric.database.DatabaseHelper;

public class NumberOfFacebookFriends extends FacebookStats{

	private static final String TAG = "NumberOfFacebookFriends";
	
	private static String mName = "NumberOfFacebookFriends";
	private static int mDefaultCollectionInterval = 60*12;
	
	//May want to change this to AsyncRunner in the future if we want async calls.
	private AsyncFacebookRunner mRunner;
	
	public NumberOfFacebookFriends(AsyncFacebookRunner pRunner) {
		super(mName);
		mRunner = pRunner;
	}

	@Override
	public boolean refreshStats() {
		try
		{
			mRunner.request("me/friends", mRequestListener);
		}
		catch(Exception ex)
		{
			//NetworkOnMainThreadException
			Log.e(TAG,"" + ex.getMessage());
			mData = null;
		}
		return true;
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public int getDefaultCollectionInterval() {
		return mDefaultCollectionInterval;
	}
	
	private RequestListener mRequestListener = new RequestListener()
	{

		@Override
		public void onComplete(String response, Object state) {
			Log.i(TAG,response);
			try
			{
				JSONObject obj = Util.parseJson(response);
				JSONArray arr = obj.getJSONArray("data");
				mData = (Integer)arr.length();
				//Insert into db.
				DatabaseHelper helper = new DatabaseHelper(MyApplication.getInstance());
				helper.insertNewStat(NumberOfFacebookFriends.this);
			}
			catch(Exception ex)
			{
				Log.i(TAG,"" + ex.getMessage());
				mData = null;
			}
		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub
			
		}
		
	};

}
