package com.quackware.tric.ui.widget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.quackware.tric.MyApplication;
import com.quackware.tric.R;
import com.quackware.tric.database.StatData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StatDataAdapter extends ArrayAdapter<StatData>  
{

	private ArrayList<StatData> mItems;

	public StatDataAdapter(Context context, int textViewResourceId, ArrayList<StatData> items)
	{
		super(context,textViewResourceId,items);
		mItems = items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		if(v == null)
		{
			LayoutInflater li = (LayoutInflater)MyApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(R.layout.stat_layout, null);	
		}
		StatData data = mItems.get(position);
		if (data != null) 
		{
			TextView tv = (TextView) v.findViewById(R.id.tricdetail_tv);
			if (tv != null) 
			{
				tv.setText(data.toString());
			}
		}
		return v;
	}
}
