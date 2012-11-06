package com.quackware.tric.ui;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.R.layout;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//Should this be here?
		MyApplication.getInstance().stopService();
	}

}
