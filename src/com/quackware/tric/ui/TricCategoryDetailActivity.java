package com.quackware.tric.ui;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.stats.Stats;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

public class TricCategoryDetailActivity extends Activity{
	
	
	private static final String TAG = "TricCategoryDetailActivity";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tric_category_detail);
		
		String category = getIntent().getExtras().getString("Category");
		
		loadCategory(category);
	}
	
	private void loadCategory(String category)
	{
		LinearLayout ll = (LinearLayout)findViewById(R.id.catdetail_ll);
		for(Stats s : MyApplication.getStats())
		{
			if(s.getType().equals(category))
			{
				Button b = new Button(this);
				b.setText(s.getName());
				ll.addView(b);
			}
		}
	}

}
