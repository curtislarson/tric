package com.quackware.tric.ui;


import com.quackware.tric.R;
import com.quackware.tric.stats.AppStats.AppStats;
import com.quackware.tric.stats.PhoneStats.PhoneStats;
import com.quackware.tric.stats.SocialStats.SocialStats;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TricCategoryActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tric_category);
		
		setupButtonListeners();
	}
	
	private void setupButtonListeners()
	{
		((Button)findViewById(R.id.cat_phonestats)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TricCategoryActivity.this,TricCategoryDetailActivity.class);
				intent.putExtra("Category","PhoneStats");
				startActivity(intent);
			}});
		
		((Button)findViewById(R.id.cat_appstats)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TricCategoryActivity.this,TricCategoryDetailActivity.class);
				intent.putExtra("Category", "AppStats");
				startActivity(intent);
			}});
		
		((Button)findViewById(R.id.cat_socialstats)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(TricCategoryActivity.this,TricCategoryDetailActivity.class);
				intent.putExtra("Category","SocialStats");
				startActivity(intent);
			}});
	}

}
