package com.quackware.tric.ui;

import java.util.ArrayList;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.service.CollectionService;
import com.quackware.tric.stats.Stats;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.text.InputType;

public class MyPreferenceActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceClickListener {
	
	private static final String FACEBOOK_APP_ID = "407653559195";
	private static final String[] FACEBOOK_PERMISSIONS = new String[] {"read_friendlists","read_stream","read_requests"};
	
	private SharedPreferences mPrefs;
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		buildPreferenceActivity();
		setPreferenceClickListeners();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}
	
	private void setPreferenceClickListeners()
	{
		Preference facebookPref = (Preference)findPreference("auth_facebook");
		facebookPref.setOnPreferenceClickListener(this);
	}
	
	//The general structure of preferences will remain the same, however we do need to populate it based on the Stats collection.
	private void buildPreferenceActivity()
	{
		ArrayList<Stats> stats = MyApplication.getStats();
		PreferenceScreen phonePs = (PreferenceScreen)findPreference("button_phonestats_category_key");
		PreferenceScreen socialPs = (PreferenceScreen)findPreference("button_socialstats_category_key");
		PreferenceScreen appPs = (PreferenceScreen)findPreference("button_appstats_category_key");
		for(Stats s : stats)
		{
			PreferenceScreen ps = generatePreferenceScreen(s);
			if(s.getType().equals("PhoneStats"))
			{
				phonePs.addPreference(ps);
			}
			else if(s.getType().equals("SocialStats"))
			{
				socialPs.addPreference(ps);
			}
			else if(s.getType().equals("AppStats"))
			{
				appPs.addPreference(ps);
			}
			else
			{
				//Unknown type.
			}
		}
	}
	
	private PreferenceScreen generatePreferenceScreen(Stats s)
	{
		CheckBoxPreference sharePreference = new CheckBoxPreference(this);
		sharePreference.setKey("checkbox_share_" + s.getName());
		sharePreference.setTitle(getString(R.string.pref_share_title));
		sharePreference.setSummary(getString(R.string.pref_share_summary));
		sharePreference.setDefaultValue(false);
		
		CheckBoxPreference collectPreference = new CheckBoxPreference(this);
		collectPreference.setKey("checkbox_collect_" + s.getName());
		collectPreference.setTitle(getString(R.string.pref_collect_title));
		collectPreference.setSummary(getString(R.string.pref_collect_summary));
		collectPreference.setDefaultValue(true);
		
		EditTextPreference collectionIntervalPreference = new EditTextPreference(this);
		collectionIntervalPreference.setKey("edittext_collectinterval_" + s.getName());
		collectionIntervalPreference.setTitle(getString(R.string.pref_collectinterval_title));
		collectionIntervalPreference.setSummary(getString(R.string.pref_collectinterval_summary));
		collectionIntervalPreference.setDefaultValue("" + s.getDefaultCollectionInterval());
		collectionIntervalPreference.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		
		PreferenceScreen ps = getPreferenceManager().createPreferenceScreen(this);
		ps.setKey("button_" + s.getName());
		ps.setPersistent(false);
		ps.setTitle(s.getName() + " tric");
		
		ps.addPreference(collectPreference);
		ps.addPreference(collectionIntervalPreference);
		ps.addPreference(sharePreference);
		
		return ps;
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		
		//Get service for callback.
		CollectionService service = MyApplication.getService();
		//First check what preference changed.
		if(key.startsWith("checkbox_share_"))
		{
			//todo later.
		}
		else if(key.startsWith("checkbox_collect_"))
		{
			String statsName = key.replace("checkbox_collect_", "");
			service.refreshStatsInfo(MyApplication.getStatsByName(statsName));
		}
		else if(key.startsWith("edittext_collectinterval_"))
		{
			String statsName = key.replace("edittext_collectinterval_", "");
			service.refreshStatsInfo(MyApplication.getStatsByName(statsName));
		}
	}

	@Override
	public boolean onPreferenceClick(Preference pref) {
		if(pref.getKey().equals("auth_facebook"))
		{
			mFacebook = new Facebook(FACEBOOK_APP_ID);
			mAsyncRunner = new AsyncFacebookRunner(mFacebook);
			
			mPrefs = getPreferences(MODE_PRIVATE);
			String access_token = mPrefs.getString("access_token", null);
			long expires = mPrefs.getLong("access_expires", 0);
			if(access_token != null)
			{
				mFacebook.setAccessToken(access_token);
			}
			if(expires != 0)
			{
				mFacebook.setAccessExpires(expires);
			}
			if(!mFacebook.isSessionValid())
			{
			mFacebook.authorize(this, FACEBOOK_PERMISSIONS, new DialogListener(){

				@Override
				public void onComplete(Bundle values) {
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token",mFacebook.getAccessToken());
					editor.putLong("access_expires",mFacebook.getAccessExpires());
					editor.commit();
				}

				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					
				}});
			}
		}
		return true;
	}

}
