package com.quackware.tric.stats.SocialStats;

import com.quackware.tric.stats.Stats;

public abstract class SocialStats extends Stats {

	private String mType = "SocialStats";
	
	private String mPrettyName = "Social Statistics";
	
	public SocialStats(String pName) {
		super(pName);
	}
	
	public String getType()
	{
		return mType;
	}
	
	@Override
	public String getPrettyName() {
		return mPrettyName;
	}

}
