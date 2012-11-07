package com.quackware.tric.database;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.quackware.tric.stats.Stats;

import android.content.ContentValues;
import android.content.Context;
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
			this.dropTables(db);
			onCreate(db);
		}
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
	}
	
	private void dropTables(SQLiteDatabase db)
	{
		for(int i = 0;i<Stats.mStatsNames.size();i++)
		{
			db.execSQL(TABLE_DROP + Stats.mStatsNames.get(i));
		}
	}

}
