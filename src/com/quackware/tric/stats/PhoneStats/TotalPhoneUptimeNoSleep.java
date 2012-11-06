package com.quackware.tric.stats.PhoneStats;

import android.os.SystemClock;

public class TotalPhoneUptimeNoSleep extends PhoneStats{
	
	private static String mName = "TotalPhoneUptimeNoSleep";
	private int mDefaultCollectionInterval = 10;
	
	public TotalPhoneUptimeNoSleep()
	{
		super(mName);
	}

	@Override
	public void refreshStats() {
		Long returnL = SystemClock.uptimeMillis();
		mData = returnL;
	}

}
