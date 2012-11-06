package com.quackware.tric.stats;

import java.util.ArrayList;

import com.quackware.tric.MyApplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public abstract class Stats {
	
	//Data for every Stats object.
	private String mName = "Stats";
	private String mType = "Stats";
	protected Object mData;
	
	//Preferences
	//Ideally we should be reading these in from preferences
	//In minutes (Default to 1 hour)
	protected int mDefaultCollectionInterval = 60;
	protected int mCollectionInterval;
	protected boolean mShare = false;
	protected boolean mCollect = true;
	
	public static ArrayList<String> mStatsNames = new ArrayList<String>();
	
	public Stats(String pName)
	{
		if(!mStatsNames.contains(pName))
		{
			mStatsNames.add(pName);
		}

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
		mCollectionInterval = prefs.getInt("edittext_collectinterval_" + pName, mDefaultCollectionInterval);
		mShare = prefs.getBoolean("checkbox_share_" + pName, false);
		mCollect = prefs.getBoolean("checkbox_collect_" + pName,true);
	}
	
	public abstract void refreshStats();
	
	public abstract String getType();
	
	public Object getStats()
	{
		return mData;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public int getDefaultCollectionInterval()
	{
		return mDefaultCollectionInterval;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Stats)
		{
			//Not sure if this.getName() will refer to the correct name.
			if(((Stats)o).getName().equals(this.getName()))
				return true;
		}
		return false;
	}

}
