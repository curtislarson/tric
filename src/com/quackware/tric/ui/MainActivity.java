package com.quackware.tric.ui;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.ui.view.PieChart;
import com.quackware.tric.ui.view.PieChart.OnSliceSelectedListener;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends Activity {
	
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
