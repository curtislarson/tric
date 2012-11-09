package com.quackware.tric.stats.SocialStats;


public class FacebookStats extends SocialStats{

	private static String mName = "FacebookStats";
	private static int mDefaultCollectionInterval = 60;
	
	
	public static final String FACEBOOK_APP_ID = "407653559195";
	public static final String[] FACEBOOK_PERMISSIONS = new String[] {"read_friendlists","read_stream","read_requests"};

	
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
