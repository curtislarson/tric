package com.quackware.tric.stats.PhoneStats;

import com.quackware.tric.stats.Stats;

public abstract class PhoneStats extends Stats{
	
	private String mType = "PhoneStats";
	
	private String mPrettyName = "Phone Statistics";
	
	public PhoneStats(String pName)
	{
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
