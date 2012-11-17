package com.quackware.tric.stats.SocialStats;

public class NumberOfFacebookWallPosts extends FacebookStats{

	private static final String TAG = "NumberOfFacebookWallPosts";
	
	private static String mName = "NumberOfFacebookWallPosts";
	private static int mDefaultCollectionInterval = 60*12;
	
	public NumberOfFacebookWallPosts() {
		super(mName);
	}

	@Override
	public boolean refreshStats() {
		
		new QueryFacebookTask(this).execute("SELECT wall_count FROM user WHERE uid = me()","wall_count");
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

}
