package com.quackware.tric.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.database.DatabaseHelper;
import com.quackware.tric.database.SelectType.StatType;
import com.quackware.tric.database.SelectType.TimeFrame;
import com.quackware.tric.database.StatData;
import com.quackware.tric.ui.fragment.GraphFragment;
import com.quackware.tric.ui.widget.StatDataAdapter;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TricDetailActivity extends Activity {
	
	private StatDataAdapter mAdapter;
	
	private String mTricName = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tric_detail);
		
		String tricName = getIntent().getExtras().getString("tricName");
		if(tricName != null)
		{
			mTricName = tricName;
		}
		loadTric();
		
		setupButtonListeners();
	}

	private void setupButtonListeners()
	{
		((Button)findViewById(R.id.tricdetail_preferences_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(TricDetailActivity.this,MyPreferenceActivity.class);
				intent.putExtra("tricName", mTricName);
				startActivity(intent);
			}});
		
		((Button)findViewById(R.id.tricdetail_refresh_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				MyApplication.getService().refreshStatsInfo(MyApplication.getStatsByName(mTricName));
				loadTric();
			}});
		
		((Button)findViewById(R.id.tricdetail_reset_button)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				//Add confirmation.
				DatabaseHelper db = new DatabaseHelper(TricDetailActivity.this);
				db.clearAllStats(mTricName);
				loadTric();
			}});
	}
	private void loadTric()
	{
		DatabaseHelper db = new DatabaseHelper(this);
		ArrayList<StatData> statData = db.selectStats(mTricName, StatType.HIGHEST, TimeFrame.ALLTIME,20);
		
		GraphFragment frag = new GraphFragment(mTricName,statData);
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.tricdetail_maplayout, frag).commit();

		((TextView)findViewById(R.id.tricdetail_trictitle_tv)).setText(mTricName);
		
		mAdapter = new StatDataAdapter(this,R.layout.stat_layout,statData);
		ListView statListView = (ListView)findViewById(R.id.tricdetail_listvew);
		statListView.invalidate();
		statListView.setAdapter(mAdapter);
	}
}
