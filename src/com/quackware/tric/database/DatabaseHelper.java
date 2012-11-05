package com.quackware.tric.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "tricDB";
	private static final int DATABASE_VERSION = 1;
	
	private static final boolean rebuild = false;
	
	public DatabaseHelper(Context context)
	{
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//create tables.
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		if (oldVersion < DATABASE_VERSION && rebuild)
		{
			//drop tables.
			onCreate(db);
		}
	}

}
