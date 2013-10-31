package com.quackware.tric.database;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.quackware.tric.stats.Stats;
import com.quackware.tric.stats.Stats.DataType;

public class StatData implements Parcelable {

	
	public String mName = "";
	public String mData;
	public String mTimestamp;
	public DataType mDataType;
	
	private StatData(Parcel in) {
		mName = in.readString();
		mData = in.readString();
		mTimestamp = in.readString();
		mDataType = Stats.DataType.values()[in.readInt()];
	}
	
	public StatData()
	{
	}

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

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags)
	{
		out.writeString(mName);
		out.writeString(mData);
		out.writeString(mTimestamp);
		out.writeInt(mDataType.ordinal());
	}

}
