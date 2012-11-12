package com.quackware.tric.stats.PhoneStats;

import com.quackware.tric.stats.Stats.DataType;

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
	public boolean refreshStats() {
		Long returnL = SystemClock.elapsedRealtime();
		mData = returnL;
		return false;
	}

	@Override
	public DataType getDataType() {
		return DataType.TIME;
	}
}
