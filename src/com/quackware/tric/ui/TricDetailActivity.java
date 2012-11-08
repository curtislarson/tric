package com.quackware.tric.ui;

import java.util.ArrayList;

import com.quackware.tric.R;
import com.quackware.tric.database.DatabaseHelper;
import com.quackware.tric.database.DatabaseHelper.StatData;
import com.quackware.tric.database.SelectType.StatType;
import com.quackware.tric.database.SelectType.TimeFrame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TricDetailActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tric_detail);
		
		String tricName = getIntent().getExtras().getString("tricName");
		loadTric(tricName);
	}
	
	private void loadTric(String tricName)
	{
		LinearLayout ll = (LinearLayout)findViewById(R.id.tricdetail_ll);
		DatabaseHelper db = new DatabaseHelper(this);
		ArrayList<StatData> statData = db.selectStats(tricName, StatType.HIGHEST, TimeFrame.ALLTIME);
		for(int i = 0;i<statData.size();i++)
		{
			TextView tv = new TextView(this);
			tv.setText("Data: " + statData.get(i).mData + "\nTimestamp: " + statData.get(i).mTimestamp);
			ll.addView(tv);
		}
	}
}
