package com.quackware.tric.stats.IndividualAppStats;

import com.quackware.tric.stats.Stats;

public abstract class IndividualAppStats extends Stats {

	private String mType = "IndividualAppStats";
	
	public IndividualAppStats(String pName) {
		super(pName);
	}

	@Override
	public String getType() {
		return mType;
	}


}
