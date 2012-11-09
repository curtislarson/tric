package com.quackware.tric.service;

import java.util.ArrayList;
import java.util.Iterator;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.quackware.tric.MyApplication;
import com.quackware.tric.database.DatabaseHelper;
import com.quackware.tric.stats.Stats;
import com.quackware.tric.stats.AppStats.NumberOfDownloadedAppsInstalled;
import com.quackware.tric.stats.AppStats.NumberOfTotalAppsInstalled;
import com.quackware.tric.stats.AppStats.NumberOfTotalAppsUninstalled;
import com.quackware.tric.stats.PhoneStats.*;
import com.quackware.tric.stats.SocialStats.FacebookStats;
import com.quackware.tric.stats.SocialStats.NumberOfFacebookFriends;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class CollectionService extends Service {

	private Handler mHandler;
	private DatabaseHelper mDatabaseHelper;
	
	private ArrayList<ArgRunnable> mRunnableList;
	
	private final IBinder mBinder = new CollectionBinder();
	
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		mHandler = new Handler();
		mDatabaseHelper = new DatabaseHelper(this);
		mRunnableList = new ArrayList<ArgRunnable>();
		
		mFacebook = new Facebook(FacebookStats.FACEBOOK_APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	@Override
	public boolean onUnbind(Intent intent)
	{
		return false;
	}
	
	public class CollectionBinder extends Binder
	{
		public CollectionService getService()
		{
			return CollectionService.this;
		}
	}
	
	public void cancelStatsCollection(Stats...pStats)
	{
		//Iterator for safe deletion while iterating through list.
		Iterator<ArgRunnable> it = mRunnableList.iterator();
		
		for(Stats s : pStats)
		{
			while(it.hasNext())
			{
				ArgRunnable a = it.next();
				if(a.mStats.equals(s))
				{
					mHandler.removeCallbacks(a);
					it.remove();
				}
			}
		}
	}
	
	public void refreshStatsInfo(Stats...pStats)
	{
		cancelStatsCollection(pStats);
		launch(pStats);
	}
	
	public void beginCollection()
	{
		//BEGIN PHONE STATS
		TotalPhoneUptime tpu = new TotalPhoneUptime();
		TotalPhoneUptimeNoSleep tpuns = new TotalPhoneUptimeNoSleep();
		//END PHONE STATS
		
		//BEGIN APP STATS
		NumberOfDownloadedAppsInstalled d = new NumberOfDownloadedAppsInstalled();
		NumberOfTotalAppsInstalled totalApps = new NumberOfTotalAppsInstalled();
		NumberOfTotalAppsUninstalled u = new NumberOfTotalAppsUninstalled();
		//END APP STATS
		
		//BEGIN SOCIAL STATS
		NumberOfFacebookFriends fbFriends = new NumberOfFacebookFriends(mAsyncRunner);
		//END SOCIAL STATS
		
		launch(tpu,tpuns,d,totalApps,u,fbFriends);
		MyApplication.addStats(tpu,tpuns,d,totalApps,u,fbFriends);
	}
	
	private void launch(Stats...pStats)
	{
		//Add check to see if we should actually launch based on preferences for collecting.
		for(Stats s : pStats)
		{
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
			if(prefs.getBoolean("checkbox_collect_" + s.getName(), true))
			{
				ArgRunnable a = new ArgRunnable(s);
				mHandler.post(a);
				mRunnableList.add(a);
			}
		}
	}
	
	public void authorizeFacebook(Activity pCallingActivity) {
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
						}

						@Override
						public void onFacebookError(FacebookError e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onError(DialogError e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onCancel() {
							// TODO Auto-generated method stub

						}
					});
		}
		
		//Refresh collection after login.
		for(int i = 0;i<MyApplication.getStats().size();i++)
		{
			if(MyApplication.getStats().get(i).getType().equals("FacebookStats"))
			{
				launch(MyApplication.getStats().get(i));
			}
		}
	}

	public Facebook getFacebook()
	{
		return mFacebook;
	}
	
	class ArgRunnable implements Runnable
	{
		private Stats mStats;
		public ArgRunnable(Stats pStats)
		{
			mStats = pStats;
		}
		public void run() 
		{
			if(mStats.getType().equals("FacebookStats") && mFacebook != null)
			{
				mFacebook.extendAccessTokenIfNeeded(MyApplication.getInstance(), null);
				if(!mFacebook.isSessionValid())
				{
					Toast.makeText(getApplicationContext(), "Not logged into Facebook, not collecting info",Toast.LENGTH_LONG).show();
					mHandler.postDelayed(this,mStats.getDefaultCollectionInterval()*1000*60);
					return;
				}
			}
			if(!mStats.refreshStats())
			{
				//Not asynchronous, we can go ahead and insert now.
				mDatabaseHelper.insertNewStat(mStats);
			}
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
			int collectionInterval = Integer.parseInt(prefs.getString("edittext_collectinterval_" + mStats.getName(), "" + mStats.getDefaultCollectionInterval()));
			mHandler.postDelayed(this,collectionInterval*1000*60);
		}
		
	}
}
