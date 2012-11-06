package com.quackware.tric.stats.AppStats;

import com.quackware.tric.stats.Stats;

public abstract class AppStats extends Stats {

	private String mType = "AppStats";
	
	public AppStats(String pName) {
		super(pName);
	}

	@Override
	public String getType() {
		return mType;
	}


}
