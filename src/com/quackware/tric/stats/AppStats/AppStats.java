package com.quackware.tric.stats.AppStats;

import com.quackware.tric.stats.Stats;

public abstract class AppStats extends Stats {

	private String mType = "AppStats";
	private String mPrettyName = "Application Stats";
	
	public AppStats(String pName) {
		super(pName);
	}

	@Override
	public String getType() {
		return mType;
	}
	
	@Override
	public String getPrettyName() {
		return mPrettyName;
	}


}
