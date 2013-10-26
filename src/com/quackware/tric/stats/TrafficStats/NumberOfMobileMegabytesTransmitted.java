package com.quackware.tric.stats.TrafficStats;

public class NumberOfMobileMegabytesTransmitted extends TrafficStats
{
	private static final String mName = "NumberOfMobileMegabytesTransmitted";
	private int mDefaultCollectionInterval = 60;
	
	
	public NumberOfMobileMegabytesTransmitted()
	{
		super(mName);
	}
	
	@Override
	public String getName()
	{
		return mName;
	}

	@Override
	public boolean refreshStats()
	{
		mData = android.net.TrafficStats.getMobileTxBytes() / (1024 * 1024);
		return false;
	}

	@Override
	public int getDefaultCollectionInterval()
	{
		return mDefaultCollectionInterval;
	}

	@Override
	public DataType getDataType()
	{
		return DataType.NUMBER;
	}

}
