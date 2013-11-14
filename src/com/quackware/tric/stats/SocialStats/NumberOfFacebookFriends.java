package com.quackware.tric.stats.SocialStats;

public class NumberOfFacebookFriends extends FacebookStats{

	private static final String TAG = "NumberOfFacebookFriends";
	
	private static String mName = "NumberOfFacebookFriends";
	private static final String mPrettyName = "Number of Facebook Friends";
	private static int mDefaultCollectionInterval = 60*12;

	public NumberOfFacebookFriends() {
		super(mName);
	}

	@Override
	public boolean refreshStats() {
		
		new QueryFacebookTask(this).execute("SELECT friend_count FROM user WHERE uid = me()","friend_count");
		return true;
	}

	@Override
	public String getName() {
		return mName;
	}

	@Override
	public int getDefaultCollectionInterval() {
		return mDefaultCollectionInterval;
	}

	@Override
	public DataType getDataType() {
		return DataType.NUMBER;
	}
	
	@Override
	public String getPrettyName() {
		return mPrettyName;
	}

}
