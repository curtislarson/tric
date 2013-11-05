package com.quackware.tric.stats;

import java.util.ArrayList;

import com.quackware.tric.MyApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public abstract class Stats {
	
	//Data for every Stats object.
	protected Object mData;

	protected int mDefaultCollectionInterval = 60;
	
	// Optional context object for stats that need it
	protected Context mContext = null;
	protected boolean mNeedsContext = false;
	
	public static ArrayList<String> mStatsNames = new ArrayList<String>();
	
	public Stats(String pName)
	{
		if(!mStatsNames.contains(pName))
		{
			mStatsNames.add(pName);
		}
	}
	
	/**
	 * Refreshes the internal data of the stats object.
	 * @return A boolean indicating whether or not the refresh was asynchronous.
	 */
	public abstract boolean refreshStats();
	
	public abstract String getType();
	
	public abstract String getName();
	
	public abstract String getPrettyName();
	
	public abstract int getDefaultCollectionInterval();
	
	public abstract DataType getDataType();
	
	public Object getStats()
	{
		return mData;
	}
	
	public void setStats(Object o)
	{
		mData = o;
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
	
	public void setContext(Context pContext) {
		mContext = pContext;
	}
	
	public boolean getNeedsContext()
	{
		return mNeedsContext;
	}
	
	
	public enum DataType
	{
		STRING,
		TIME,
		NUMBER,
		KEY_VALUE
	};

}
