package com.quackware.tric;

import java.util.ArrayList;

import com.quackware.tric.stats.Stats;

import android.app.Application;

public class MyApplication extends Application
{
	private static MyApplication instance;
	//Holds a list of Stats that have ever been instantiated
	private static ArrayList<Stats> mGlobalStatsList = new ArrayList<Stats>();
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		instance = this;
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

}
