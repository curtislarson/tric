package com.quackware.tric.stats.AppStats;

import java.util.List;

import com.quackware.tric.MyApplication;
import com.quackware.tric.stats.Stats.DataType;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class NumberOfAppsRunning extends AppStats {

	private static final String mName = "NumberOfAppsRunning";
	private int mDefaultCollectionInterval = 60;
	
	public NumberOfAppsRunning() {
		super(mName);
		this.mNeedsContext = true;
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
		if (this.mContext != null) 
		{
			final ActivityManager activityManager = (ActivityManager)this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
			final List<RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
			mData = (Integer)recentTasks.size();	
		}
		return false;
	}
	
	@Override
	public DataType getDataType() {
		return DataType.NUMBER;
	}

}
