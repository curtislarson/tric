package com.quackware.tric.database;

import com.quackware.tric.stats.Stats.DataType;

public class StatData {

	
	public String mData;
	public String mTimestamp;
	public DataType mDataType;
	
	@Override
	public String toString()
	{
		return mData;
	}

}
