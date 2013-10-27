package com.quackware.tric.stats.TrafficStats;

public class NumberOfMobileMegabytesReceived extends TrafficStats
{
	private static final String mName = "NumberOfMobileMegabytesReceived";
	private int mDefaultCollectionInterval = 60;
	
	
	public NumberOfMobileMegabytesReceived()
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
		mData = android.net.TrafficStats.getMobileRxBytes() / (1024 * 1024);
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
