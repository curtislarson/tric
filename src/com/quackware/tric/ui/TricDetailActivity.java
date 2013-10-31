package com.quackware.tric.ui;

import java.util.ArrayList;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.database.DatabaseHelper;
import com.quackware.tric.database.SelectType.StatType;
import com.quackware.tric.database.SelectType.TimeFrame;
import com.quackware.tric.database.StatData;
import com.quackware.tric.ui.fragment.GraphFragment;
import com.quackware.tric.ui.widget.StatDataAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
				AlertDialog.Builder builder = new AlertDialog.Builder(TricDetailActivity.this);
				// Add the buttons
				builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
								MyApplication.getDatabaseHelper().clearAllStats(mTricName);
								loadTric();
				           }
				       });
				builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               // User cancelled the dialog
				           }
				       });
				builder.setMessage(R.string.clear_stats_message).setTitle(R.string.dialog_warning);
				AlertDialog dialog = builder.create();
				dialog.show();
			}});
	}
	
	private void loadTric()
	{
		DatabaseHelper db = MyApplication.getDatabaseHelper();
		ArrayList<StatData> statData = db.selectStats(mTricName, StatType.HIGHEST, TimeFrame.ALLTIME,20);
		
		GraphFragment frag = GraphFragment.newInstance(mTricName, statData);
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		trans.replace(R.id.tricdetail_maplayout, frag).commit();

		((TextView)findViewById(R.id.tricdetail_trictitle_tv)).setText(mTricName);
		
		mAdapter = new StatDataAdapter(this,R.layout.stat_layout,statData);
		ListView statListView = (ListView)findViewById(R.id.tricdetail_listvew);
		statListView.invalidate();
		statListView.setAdapter(mAdapter);
	}
}
