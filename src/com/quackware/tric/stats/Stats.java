package com.quackware.tric.stats;

import java.util.ArrayList;

public abstract class Stats {
	
	//Data for every Stats object.
	private String mName = "Stats";
	protected Object mData;
	
	//Preferences
	//Ideally we should be reading these in from preferences
	//In minutes (Default to 1 hour)
	protected int mCollectionInterval = 60;
	protected boolean mShare = false;
	protected boolean mCollect = true;
	
	public static ArrayList<String> mStatsNames = new ArrayList<String>();
	
	public Stats(String pName)
	{
		if(!mStatsNames.contains(pName))
		{
			mStatsNames.add(pName);
		}
		//Read from preferences and set collectioninterval,share,collect, etc.
	}
	
	public abstract void refreshStats();
	
	public Object getStats()
	{
		return mData;
	}
	
	public String getName()
	{
		return mName;
	}
	
	public int getCollectionInterval()
	{
		return mCollectionInterval;
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
