package com.quackware.tric.stats.SocialStats;

public abstract class FacebookStats extends SocialStats{

	private static String mType = "FacebookStats";
	private static String mPrettyName = "Facebook Statistics";
	
	public static final String FACEBOOK_APP_ID = "407653559195";
	public static final String[] FACEBOOK_PERMISSIONS = new String[] {"read_friendlists","read_stream","read_requests","user_photos"};

	
	public FacebookStats(String pName) {
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
