package com.quackware.tric.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.quackware.tric.MyApplication;
import com.quackware.tric.database.SelectType.StatType;
import com.quackware.tric.database.SelectType.TimeFrame;
import com.quackware.tric.stats.Stats;
import com.quackware.tric.stats.Stats.DataType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static final String TAG = "DatabaseHelper";
	
	private static final String DATABASE_NAME = "tricDB";
	private static final int DATABASE_VERSION = 1;
	
	private static final boolean rebuild = false;
	
	private static final String TABLE_CREATE_START = "CREATE TABLE IF NOT EXISTS ";
	private static final String TABLE_CREATE_END = " (_id INTEGER PRIMARY KEY, DATA STRING, TIMESTAMP DATE);";
	private static final String TABLE_DROP = "DROP TABLE IF EXISTS ";
	
	public DatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Add check for all database tables here.
		for(int i = 0;i<Stats.mStatsNames.size();i++)
		{
			db.execSQL(TABLE_CREATE_START + Stats.mStatsNames.get(i) + TABLE_CREATE_END);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		if (oldVersion < DATABASE_VERSION && rebuild)
		{
			//In the future this should be changed to add new tables in instead of just dropping
			this.dropTables(db);
			onCreate(db);
		}
	}
	
	public void clearAllStats(String pName)
	{
		SQLiteDatabase db = getWritableDatabase();
		db.delete(pName, null, null);
		db.close();
	}
	
	public ArrayList<StatData> selectSignificantStat(String pName, StatType pType,TimeFrame pTimeFrame)
	{
		return this.selectStats(pName, pType, pTimeFrame,1);
	}
	
	public ArrayList<StatData> selectStats(String pName,StatType pType, TimeFrame pTimeFrame, int limit)
	{
		SQLiteDatabase db = getWritableDatabase();
		String orderBy = getOrderBy(pType);
		String dataFilter = getDataFilter(pType);
		String selection = getTimeFrameFilter(pTimeFrame);

		Cursor c = db.query(
				pName, 
				new String[] {dataFilter,"TIMESTAMP"},
				selection,
				null,
				null,
				null,
				orderBy,
				"" + limit);
		
		ArrayList<StatData> sdList = new ArrayList<StatData>();
		boolean notEmpty = c.moveToFirst();
		DataType type = MyApplication.getStatsByName(pName).getDataType();
		while(notEmpty)
		{
			String data = c.getString(0);
			String dateTime = c.getString(1);
			StatData sd = new StatData();
			sd.mData = data;
			sd.mTimestamp = dateTime;
			sd.mDataType = type;
			sdList.add(sd);
			notEmpty = c.moveToNext();
		}
		c.close();
		db.close();

		return sdList;
	}
	
	
	private String getTimeFrameFilter(TimeFrame pTimeFrame)
	{
		String selection = null;
		if(pTimeFrame != null)
		{
			switch(pTimeFrame)
			{
			case DAY:
				selection = "WHERE TIMESTAMP BETWEEN datetime('now','start of day') AND datetime('now','localtime')";
				break;
			case WEEK:
				selection = "WHERE TIMESTAMP BETWEEN datetime('now',-6 days') AND datetime('now','localtime')";
				break;
			case MONTH:
				selection = "WHERE TIMESTAMP BETWEEN datetime('now','start of month') AND datetime('now','localtime')";
				break;
			case YEAR:
				selection = "WHERE TIMESTAMP BETWEEN datetime('now','start of year' AND datetime('now','localtime')";
				break;
			case ALLTIME:
				break;
			default:
				break;
			}
		}
		return selection;
	}
	
	private String getDataFilter(StatType pType)
	{
		String dataFilter = null;
		if(pType != null)
		{
			switch(pType)
			{

			case AVERAGE:
				dataFilter = "AVG(DATA)";
				break;
			case MEDIAN:
			case STDEV:
			case HIGHEST:
			case LOWEST:
			default:
				dataFilter = "DATA";
				break;
			}
		}
		return dataFilter;
	}
	
	private String getOrderBy(StatType pType)
	{
		String dataFilter = null;
		if(pType != null)
		{
			switch(pType)
			{
			case HIGHEST:
				dataFilter = "DATA DESC";
				break;
			case LOWEST:
				dataFilter = "DATA ASC";
				break;
			case AVERAGE:
			case MEDIAN:
			case STDEV:
			default:
				return null;
			}
		}
		return dataFilter;
	}
	
	public void insertNewStat(Stats pStats)
	{
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put("DATA",pStats.getStats().toString());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		values.put("TIMESTAMP",dateFormat.format(date));
		
		db.insert(pStats.getName(), null, values);
		
		db.close();
	}
	
	private void dropTables(SQLiteDatabase db)
	{
		for(int i = 0;i<Stats.mStatsNames.size();i++)
		{
			db.execSQL(TABLE_DROP + Stats.mStatsNames.get(i));
		}
	}

}
