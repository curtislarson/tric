package com.quackware.tric.stats.PhoneStats;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TotalPhoneRam extends PhoneStats
{
	private static String mName = "TotalPhoneRam";
	private int mDefaultCollectionInterval = 12 * 60;
	
	public TotalPhoneRam()
	{
		super(mName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean refreshStats()
	{
		int tm = 1000; 
		try 
		{ 
			RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r"); 
			String load = reader.readLine(); 
			String[] totrm = load.split(" kB"); 
			String[] trm = totrm[0].split(" "); 
			tm=Integer.parseInt(trm[trm.length-1]); 
			tm=Math.round(tm/1024);
			reader.close();
		} 
		catch (IOException ex) 
		{ 
			ex.printStackTrace();
			tm = 0;
		}
		mData = tm;
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
