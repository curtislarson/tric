package com.quackware.tric.stats.IndividualAppStats;

public class IndividualAppRuntime extends IndividualAppStats
{

	private static final String mName = "IndividualAppRuntime";
	private static final String mPrettyName = "Indivual App Runtime";
	
	private int mDefaultCollectionInterval = 60;
	
	public IndividualAppRuntime(String pName)
	{
		super(mName);
	}

	@Override
	public boolean refreshStats()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getName()
	{
		return mName;
	}

	@Override
	public String getPrettyName()
	{
		return mPrettyName;
	}

	@Override
	public int getDefaultCollectionInterval()
	{
		return mDefaultCollectionInterval;
	}

	@Override
	public DataType getDataType()
	{
		return DataType.KEY_VALUE;
	}

}
