package com.quackware.tric.stats.SocialStats;


public class FacebookStats extends SocialStats{

	private static String mName = "FacebookStats";
	private static int mDefaultCollectionInterval = 60;
	

	
	public FacebookStats(String pName) {
		super(mName);

	}
	@Override
	public void refreshStats() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getName() {
		return mName;
	}
	@Override
	public int getDefaultCollectionInterval() {
		return mDefaultCollectionInterval;
	}
	
	

}
