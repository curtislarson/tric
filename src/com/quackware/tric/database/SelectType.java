package com.quackware.tric.database;

public class SelectType {
	
	public enum TimeFrame
	{
		DAY,
		WEEK,
		MONTH,
		YEAR,
		ALLTIME
	};
	
	public enum StatType
	{
		HIGHEST,
		LOWEST,
		AVERAGE,
		MEDIAN,
		STDEV
	};

}
