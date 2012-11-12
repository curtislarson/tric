package com.quackware.tric;

import java.util.ArrayList;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.ServiceListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.quackware.tric.service.CollectionService;
import com.quackware.tric.service.CollectionService.CollectionBinder;
import com.quackware.tric.stats.Stats;
import com.quackware.tric.stats.SocialStats.FacebookStats;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class MyApplication extends Application
{
	
	private static final String TAG = "MyApplication";
	
	private static MyApplication instance;
	//Holds a list of Stats that have ever been instantiated
	private static ArrayList<Stats> mGlobalStatsList = new ArrayList<Stats>();
	
	private static CollectionService mService;
	private static boolean mBound = false;
	
	private static Facebook mFacebook;
	private static AsyncFacebookRunner mAsyncRunner;
	
	
	//Going to need to extend facebook auth token here potentially (or in the tric category detail activity)
	@Override
	public void onCreate()
	{
		super.onCreate();
		instance = this;
		
		mFacebook = new Facebook(FacebookStats.FACEBOOK_APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		
		startService();
	}
	
	/**
	 * Attempts to authorize with Facebook using the provided Activity.
	 * @param pCallingActivity The activity to return to upon authorization.
	 * @return a boolean indicating whether or not we needed to authorize.
	 */
	public static boolean authorizeFacebook(Activity pCallingActivity) {
		final SharedPreferences mPrefs = PreferenceManager
				.getDefaultSharedPreferences(MyApplication.getInstance());
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		if (access_token != null) {
			mFacebook.setAccessToken(access_token);
		}
		if (expires != 0) {
			mFacebook.setAccessExpires(expires);
		}
		if (!mFacebook.isSessionValid()) 
		{
			mFacebook.authorize(pCallingActivity,
					FacebookStats.FACEBOOK_PERMISSIONS, new DialogListener() {

						@Override
						public void onComplete(Bundle values) {
							SharedPreferences.Editor editor = mPrefs.edit();
							editor.putString("access_token",
									mFacebook.getAccessToken());
							editor.putLong("access_expires",
									mFacebook.getAccessExpires());
							editor.commit();
							
							
							//Refresh collection after login.
							for(int i = 0;i<mGlobalStatsList.size();i++)
							{
								if(mGlobalStatsList.get(i).getType().equals("FacebookStats"))
								{
									getService().refreshStatsInfo(mGlobalStatsList.get(i));
								}
							}
						}

						@Override
						public void onFacebookError(FacebookError e) {
							Log.e(TAG,e.getMessage());
						}

						@Override
						public void onError(DialogError e) {
							Log.e(TAG,e.getMessage());

						}

						@Override
						public void onCancel() {
							// TODO Auto-generated method stub

						}
					});
			return true;
		}
		else
		{
			return false;
		}

	}
	
	public static void refreshFacebookToken()
	{
		if(mFacebook != null)
		{
			mFacebook.extendAccessToken(getInstance(), new ServiceListener(){

				@Override
				public void onComplete(Bundle values) {
					final SharedPreferences mPrefs = PreferenceManager
							.getDefaultSharedPreferences(MyApplication.getInstance());
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token",
							mFacebook.getAccessToken());
					editor.putLong("access_expires",
							mFacebook.getAccessExpires());
					editor.commit();
				}

				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError(Error e) {
					// TODO Auto-generated method stub
					
				}});
		}
	}
	
	private void startService()
	{
		Intent intent = new Intent(this,CollectionService.class);
		bindService(intent,mConnection,Context.BIND_AUTO_CREATE);
	}
	
	public void stopService()
	{
		if(mBound)
		{
			unbindService(mConnection);
			mBound = false;
		}
	}
	
	private ServiceConnection mConnection = new ServiceConnection()
	{

		public void onServiceConnected(ComponentName className, IBinder service) {
			CollectionBinder binder = (CollectionBinder)service;
			mService = binder.getService();
			mBound = true;
			
			mService.beginCollection();
		}

		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
			mService = null;
		}
		
	};
	
	public static Facebook getFacebook()
	{
		return mFacebook;
	}
	
	public static AsyncFacebookRunner getFacebookRunner()
	{
		return mAsyncRunner;
	}
	
	public static CollectionService getService()
	{
		return mService;
	}
	
	public static MyApplication getInstance()
	{
		return instance;
	}
	
	public static void addStats(Stats...pStats)
	{
		for(Stats s : pStats)
		{
			if(!mGlobalStatsList.contains(s))
				mGlobalStatsList.add(s);
		}
	}
	
	public static ArrayList<Stats> getStats()
	{
		return mGlobalStatsList;
	}
	
	public static Stats getStatsByName(String name)
	{
		for(int i = 0;i<mGlobalStatsList.size();i++)
		{
			if(mGlobalStatsList.get(i).getName().equals(name))
				return mGlobalStatsList.get(i);
		}
		return null;
	}

}
