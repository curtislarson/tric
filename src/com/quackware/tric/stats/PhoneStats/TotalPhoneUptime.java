package com.quackware.tric.stats.PhoneStats;

import android.os.SystemClock;

public class TotalPhoneUptime extends PhoneStats{

	private static String mName = "TotalPhoneUptime";
	
	public TotalPhoneUptime()
	{
		super(mName);
	}
	
	@Override
	public boolean insertIntoDatabase() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refreshStats() {
		Long returnL = SystemClock.elapsedRealtime();
		mData = returnL;
	}

}
