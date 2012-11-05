package com.quackware.tric.ui;

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

	}

}
