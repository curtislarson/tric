package com.quackware.tric.stats.PhoneStats;

import android.os.SystemClock;

public class TotalPhoneUptime extends PhoneStats{

	private static String mName = "TotalPhoneUptime";
	private int mDefaultCollectionInterval = 10;
	
	public TotalPhoneUptime()
	{
		super(mName);
	}
	
	@Override
	public String getName()
	{
		return mName;
	}
	
	@Override
	public int getDefaultCollectionInterval()
	{
		return mDefaultCollectionInterval;
	}

	@Override
	public void refreshStats() {
		Long returnL = SystemClock.elapsedRealtime();
		mData = returnL;
	}

}
