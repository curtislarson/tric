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
import com.quackware.tric.database.StatData;
import com.quackware.tric.stats.Stats;
import com.quackware.tric.stats.AppStats.NumberOfDownloadedAppsInstalled;
import com.quackware.tric.stats.AppStats.NumberOfTotalAppsInstalled;
import com.quackware.tric.stats.AppStats.NumberOfTotalAppsUninstalled;
import com.quackware.tric.stats.PhoneStats.*;
import com.quackware.tric.stats.SocialStats.FacebookStats;
import com.quackware.tric.stats.SocialStats.NumberOfFacebookFriends;
import com.quackware.tric.stats.SocialStats.NumberOfFacebookWallPosts;
import com.quackware.tric.stats.TrafficStats.NumberOfMobileMegabytesReceived;
import com.quackware.tric.stats.TrafficStats.NumberOfMobileMegabytesTransmitted;

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
	private ArrayList<ArgRunnable> mRunnableList;
	
	private final IBinder mBinder = new CollectionBinder();

	@Override
	public void onCreate()
	{
		super.onCreate();
		mHandler = new Handler();
		mRunnableList = new ArrayList<ArgRunnable>();
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
		// This should probably be improved by searching through each of our class structures,
		// starting with AppStats, PhoneStats, etc and looking for all the inherited classes.
		// From there all we need to do to add a new tric is to add the file to the folder
		// and implement the correct methods.
		//BEGIN PHONE STATS
		TotalPhoneUptime tpu = new TotalPhoneUptime();
		TotalPhoneUptimeNoSleep tpuns = new TotalPhoneUptimeNoSleep();
		TotalPhoneRam tpr = new TotalPhoneRam();
		//END PHONE STATS
		
		//BEGIN APP STATS
		NumberOfDownloadedAppsInstalled d = new NumberOfDownloadedAppsInstalled();
		NumberOfTotalAppsInstalled totalApps = new NumberOfTotalAppsInstalled();
		NumberOfTotalAppsUninstalled u = new NumberOfTotalAppsUninstalled();
		//END APP STATS
		
		//BEGIN TRAFFIC STATS
		NumberOfMobileMegabytesTransmitted mobileMbT = new NumberOfMobileMegabytesTransmitted();
		NumberOfMobileMegabytesReceived mobileMbR = new NumberOfMobileMegabytesReceived();
		//END TRAFFIC STATS
		
		//BEGIN SOCIAL STATS
		NumberOfFacebookFriends fbFriends = new NumberOfFacebookFriends();
		NumberOfFacebookWallPosts fbWallPosts = new NumberOfFacebookWallPosts();
		//END SOCIAL STATS
		
		launch(tpu,tpuns,tpr,d,totalApps,u,mobileMbT,mobileMbR,fbFriends,fbWallPosts);
		MyApplication.addStats(tpu,tpuns,tpr,d,totalApps,u,mobileMbT,mobileMbR,fbFriends,fbWallPosts,fbWallPosts);
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
	
	class ArgRunnable implements Runnable
	{
		private Stats mStats;
		public ArgRunnable(Stats pStats)
		{
			mStats = pStats;
		}
		public void run() 
		{
			if(mStats.getType().equals("FacebookStats") && MyApplication.getFacebook() != null)
			{
				MyApplication.getFacebook().extendAccessTokenIfNeeded(MyApplication.getInstance(), null);
				if(!MyApplication.getFacebook().isSessionValid())
				{
					Toast.makeText(getApplicationContext(), "Not logged into Facebook, not collecting info",Toast.LENGTH_LONG).show();
					mHandler.postDelayed(this,mStats.getDefaultCollectionInterval()*1000*60);
					return;
				}
			}
			if(!mStats.refreshStats())
			{
				//Not asynchronous, we can go ahead and insert now.
				MyApplication.getDatabaseHelper().insertNewStat(mStats);
			}
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
			int collectionInterval = Integer.parseInt(prefs.getString("edittext_collectinterval_" + mStats.getName(), "" + mStats.getDefaultCollectionInterval()));
			mHandler.postDelayed(this,collectionInterval*1000*60);
		}
		
	}
}
