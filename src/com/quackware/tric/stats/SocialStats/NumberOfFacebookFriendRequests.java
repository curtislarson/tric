package com.quackware.tric.stats.SocialStats;

public class NumberOfFacebookFriendRequests extends FacebookStats{

	private static final String TAG = "NumberOfFacebookFriendRequests";
	
	private static String mName = "NumberOfFacebookFriendRequests";
	private static final String mPrettyName = "Number of Facebook Friend Requests";
	private static int mDefaultCollectionInterval = 60*12;

	public NumberOfFacebookFriendRequests() {
		super(mName);
	}

	@Override
	public boolean refreshStats() {
		new QueryFacebookTask(this).execute("Select friend_request_count from user where uid = me()","friend_request_count");
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
