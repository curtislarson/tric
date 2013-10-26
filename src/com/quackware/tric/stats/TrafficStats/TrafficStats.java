package com.quackware.tric.stats.TrafficStats;

import com.quackware.tric.stats.Stats;

public abstract class TrafficStats extends Stats
{

	private String mType = "TrafficStats";
	public TrafficStats(String pName)
	{
		super(pName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType()
	{
		return mType;
	}

}
