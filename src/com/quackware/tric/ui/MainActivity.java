package com.quackware.tric.ui;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MainActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		setupButtonListeners();
		
		//Authorize, returns false if we are already authorized.
		if(!MyApplication.authorizeFacebook(this))
		{
			//Just go ahead and refresh the token.
			MyApplication.refreshFacebookToken();
		}
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//Should this be here?
		MyApplication.getInstance().stopService();
	}
	
	private void setupButtonListeners()
	{
		((Button)findViewById(R.id.prefs)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,MyPreferenceActivity.class);
				startActivity(intent);
			}});
		
		((Button)findViewById(R.id.viewtrics)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,TricCategoryActivity.class);
				startActivity(intent);
			}});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	    MyApplication.getFacebook().authorizeCallback(requestCode, resultCode, data);
	}

}
