package com.quackware.tric.stats.PhoneStats;

import com.quackware.tric.stats.Stats;

public abstract class PhoneStats extends Stats{
	
	private String mType = "PhoneStats";
	
	public PhoneStats(String pName)
	{
		super(pName);
	}
	
	public String getType()
	{
		return mType;
	}

}
