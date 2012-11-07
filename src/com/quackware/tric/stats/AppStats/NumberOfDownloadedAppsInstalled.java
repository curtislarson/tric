package com.quackware.tric.stats.AppStats;

import java.util.List;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.quackware.tric.MyApplication;

public class NumberOfDownloadedAppsInstalled extends AppStats {

	private static final String mName = "NumberOfDownloadedAppsInstalled";
	private int mDefaultCollectionInterval = 60*12;
	
	
	public NumberOfDownloadedAppsInstalled() {
		super(mName);
		// TODO Auto-generated constructor stub
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
		int count = 0;
		List<PackageInfo> packages = pm.getInstalledPackages(0);
		for(int i = 0;i<packages.size();i++)
		{
			if(isSystemPackage(packages.get(i)))
			{
				count++;
			}
		}
		mData = (Integer)count;
	}

	private boolean isSystemPackage(PackageInfo pkgInfo) {
	    return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true
	            : false;
	}
}
