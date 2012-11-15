package com.quackware.tric.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.quackware.tric.R;
import com.quackware.tric.database.DatabaseHelper;
import com.quackware.tric.database.SelectType.StatType;
import com.quackware.tric.database.SelectType.TimeFrame;
import com.quackware.tric.database.StatData;
import com.quackware.tric.ui.fragment.GraphFragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
		
		setupButtonListeners();
	}
	
	
	private void setupButtonListeners()
	{
		((Button)findViewById(R.id.tricdetail_preferences_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		((Button)findViewById(R.id.tricdetail_refresh_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}});
		
		((Button)findViewById(R.id.tricdetail_reset_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}});
	}
	private void loadTric(String tricName)
	{
		LinearLayout ll = (LinearLayout)findViewById(R.id.tricdetail_ll);
		DatabaseHelper db = new DatabaseHelper(this);
		ArrayList<StatData> statData = db.selectStats(tricName, StatType.HIGHEST, TimeFrame.ALLTIME,20);
		
		GraphFragment frag = new GraphFragment(tricName,statData);
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.add(R.id.tricdetail_maplayout, frag).commit();
		
		((TextView)findViewById(R.id.tricdetail_trictitle_tv)).setText(tricName);
		for(int i = 0;i<statData.size();i++)
		{
			TextView tv = new TextView(this);
			switch(statData.get(i).mDataType)
			{
			case TIME:
				Date d = new Date(Long.parseLong(statData.get(i).mData));
				DateFormat f = new SimpleDateFormat("HH:mm:ss");
				String formatedDate = f.format(d);
				tv.setText("Data: " + formatedDate + "\nTimestamp: " + statData.get(i).mTimestamp);
				break;
			case STRING:
			case NUMBER:
			default:
				tv.setText("Data: " + statData.get(i).mData + "\nTimestamp: " + statData.get(i).mTimestamp);
				break;
			}
			
			ll.addView(tv);
		}
	}
}
