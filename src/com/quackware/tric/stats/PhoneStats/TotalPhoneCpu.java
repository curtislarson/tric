package com.quackware.tric.stats.PhoneStats;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TotalPhoneCpu extends PhoneStats
{
	private static String mName = "TotalPhoneCpu";
	private int mDefaultCollectionInterval = 12 * 60;
	
	public TotalPhoneCpu()
	{
		super(mName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean refreshStats()
	{
		int cpuSpeed = 0;
		try 
		{ 
			RandomAccessFile reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r"); 
			String speed = reader.readLine();
			cpuSpeed = Integer.parseInt(speed);
			reader.close();
		} 
		catch (IOException ex) 
		{ 
			ex.printStackTrace();
			cpuSpeed = 0;
		}
		mData = (cpuSpeed / 1000000.0);
		return false;
	}

	@Override
	public String getName()
	{
		return mName;
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
