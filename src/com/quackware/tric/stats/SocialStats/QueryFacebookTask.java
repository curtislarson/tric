package com.quackware.tric.stats.SocialStats;

import org.json.JSONArray;
import org.json.JSONObject;

import com.quackware.tric.MyApplication;
import com.quackware.tric.database.DatabaseHelper;
import com.quackware.tric.stats.Stats;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class QueryFacebookTask extends AsyncTask<String,Void,String>{

	private static final String TAG = "QueryFacebookTask";
	
	private String mName;
	
	private Stats mInstance;
	
	public QueryFacebookTask(Stats instance)
	{
		mInstance = instance;
	}
	
	@Override
	protected String doInBackground(String... arg) {
		try
		{
			mName = arg[1];
			Bundle params = new Bundle();
			params.putString("method", "fql.query");
			params.putString("query", arg[0]);
			//params.putString("query", "SELECT friend_count FROM user WHERE uid = me()");
			return MyApplication.getFacebook().request(params);
		} catch(Exception ex)
		{
			Log.e(TAG,"" + ex.getMessage());
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(String response)
	{
		if(response == null)
			return;
		try
		{
			JSONArray arr = new JSONArray(response);
			JSONObject o = (JSONObject)arr.get(0);
			mInstance.setStats((Integer)o.get(mName));
			//Insert into db.
			DatabaseHelper helper = MyApplication.getDatabaseHelper();
			helper.insertNewStat(mInstance);
		}
		catch(Exception ex)
		{
			Log.e(TAG,"" + ex.getMessage());
		}
	}

}
