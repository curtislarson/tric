package com.quackware.tric.ui;

import java.util.ArrayList;

import com.quackware.tric.MyApplication;
import com.quackware.tric.stats.Stats;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

public class MyPreferenceActivity extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		buildPreferenceActivity();
	}
	
	//The general structure of preferences will remain the same, however we do need to populate it based on the Stats collection.
	private void buildPreferenceActivity()
	{
		ArrayList<Stats> stats = MyApplication.getStats();
		PreferenceScreen phonePs = (PreferenceScreen)findPreference("button_phonestats_category_key");
		
	}

}
