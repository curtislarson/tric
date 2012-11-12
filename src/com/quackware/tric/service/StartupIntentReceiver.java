package com.quackware.tric.service;

import com.quackware.tric.MyApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupIntentReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if(MyApplication.getInstance() != null)
		{
			MyApplication.getInstance().startService();
		}
	}

}
