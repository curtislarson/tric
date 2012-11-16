package com.quackware.tric.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.quackware.tric.stats.Stats.DataType;

public class StatData {

	
	public String mName = "";
	public String mData;
	public String mTimestamp;
	public DataType mDataType;
	
	public String toStringNoTimestamp()
	{
		String toString = toString();
		return toString.substring(0,toString.indexOf("Timestamp:") - 1);
	}
	
	@Override
	public String toString()
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String newTimestamp = "";
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(df.parse(mTimestamp));
			newTimestamp = c.get(Calendar.MONTH) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" +  c.get(Calendar.YEAR);
		} catch (ParseException e) {
			newTimestamp = mTimestamp;
		}
		switch (mDataType) 
		{
		case TIME:
			Date d = new Date(Long.parseLong(mData));
			DateFormat f = new SimpleDateFormat("HH:mm:ss");
			String formatedDate = f.format(d);
			if(mName.equals(""))
			{
				return "Data: " + formatedDate + "\tTimestamp: " + newTimestamp;
			}
			else
			{
				return mName + "\nData: " + formatedDate + "\tTimestamp: " + newTimestamp;
			}
		case STRING:
		case NUMBER:
		default:
			if(mName.equals(""))
			{
				return "Data: " + mData + "\tTimestamp: " + newTimestamp;
			}
			else
			{
				return mName + "\nData: " + mData + "\tTimestamp: " + newTimestamp;
			}
			
		}
	}

}
