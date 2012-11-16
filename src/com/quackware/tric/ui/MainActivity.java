package com.quackware.tric.ui;

import java.util.HashSet;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.ui.view.PieChart;
import com.quackware.tric.ui.view.PieChart.OnSliceSelectedListener;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ArrayAdapter<String> mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		setupPieListeners();
		
		//Authorize, returns false if we are already authorized.
		if(!MyApplication.authorizeFacebook(this))
		{
			//Just go ahead and refresh the token.
			MyApplication.refreshFacebookToken();
		}
		
		mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		ListView lv = (ListView)findViewById(R.id.main_mostrecent_lv);
		lv.setAdapter(mAdapter);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
		HashSet<String> mostRecentStatsSet = (HashSet<String>) prefs.getStringSet("mostRecentStats", new HashSet<String>());
		Object[] mostRecentStats = mostRecentStatsSet.toArray();
		if(mostRecentStats != null)
		{
			mAdapter.clear();
			for(int i = mostRecentStats.length - 1;i>0;i--)
			{
				mAdapter.add(mostRecentStats[i].toString());
			}
		}
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//Should this be here?
		//MyApplication.getInstance().stopService();
	}
	
	private void setupPieListeners()
	{
		PieChart pc = (PieChart)findViewById(R.id.piechart);
        Resources res = getResources();
        pc.addItem("Local trics",2,res.getColor(R.color.main_red));
        pc.addItem("Preferences",1,res.getColor(R.color.main_turq));
        pc.setOnSliceSelectedListener(new OnSliceSelectedListener(){

			@Override
			public void OnSliceSelected(PieChart source, int selectedSlice) {
				switch(selectedSlice)
				{
				//Local
				case 0:
					Intent intent = new Intent(MainActivity.this,TricCategoryActivity.class);
					startActivity(intent);
					break;
				//Preferences
				case 1:
					Intent intent2 = new Intent(MainActivity.this,MyPreferenceActivity.class);
					startActivity(intent2);
					break;
					
				}
			}});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	    MyApplication.getFacebook().authorizeCallback(requestCode, resultCode, data);
	}

}
