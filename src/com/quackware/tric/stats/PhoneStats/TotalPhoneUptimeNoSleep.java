package com.quackware.tric.stats.PhoneStats;

import android.os.SystemClock;

public class TotalPhoneUptimeNoSleep extends PhoneStats{
	
	private static String mName = "TotalPhoneUptimeNoSleep";
	
	public TotalPhoneUptimeNoSleep()
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
		Long returnL = SystemClock.uptimeMillis();
		mData = returnL;
	}

}
