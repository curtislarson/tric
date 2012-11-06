package com.quackware.tric.stats.AppStats;

import java.util.List;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.quackware.tric.MyApplication;

public class NumberOfTotalAppsUninstalled extends AppStats {
	
	private static String mName = "NumberOfTotalAppsUninstalled";
	private int mDefaultCollectionInterval = 60*24;

	public NumberOfTotalAppsUninstalled() {
		super(mName);
	}

	@Override
	public void refreshStats() {
		final PackageManager pm = MyApplication.getInstance().getPackageManager();
		mData = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES).size() - pm.getInstalledPackages(0).size();
	}

}
