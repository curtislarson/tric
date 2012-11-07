package com.quackware.tric.stats.AppStats;

import java.util.List;

import com.quackware.tric.MyApplication;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

public class NumberOfTotalAppsInstalled extends AppStats {

	private static final String mName = "NumberOfTotalAppsInstalled";
	private int mDefaultCollectionInterval = 60*12;
	
	public NumberOfTotalAppsInstalled() {
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
		final PackageManager pm = MyApplication.getInstance().getPackageManager();
		List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
		mData = (Integer)packages.size();
	}

}
