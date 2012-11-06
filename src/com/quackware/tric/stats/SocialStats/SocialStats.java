package com.quackware.tric.stats.SocialStats;

import com.quackware.tric.stats.Stats;

public abstract class SocialStats extends Stats {

	private String mType = "SocialStats";
	
	public SocialStats(String pName) {
		super(pName);
	}
	
	public String getType()
	{
		return mType;
	}

}
