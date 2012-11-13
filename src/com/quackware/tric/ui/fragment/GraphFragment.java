package com.quackware.tric.ui.fragment;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import com.quackware.tric.R;
import com.quackware.tric.database.StatData;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class GraphFragment extends Fragment{
	
	private ArrayList<StatData> mData;
	private String mName;
	
	public GraphFragment(String pName, ArrayList<StatData> pData)
	{
		mData = pData;
		mName = pName;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState)
	{
		View v = inflator.inflate(R.layout.graph_fragment, container,false);
		final GraphicalView graphView = createGraphView();
		RelativeLayout rl = (RelativeLayout)v.findViewById(R.id.graph_rel_layout);
		rl.addView(graphView);
		return v;
	}
	
	private GraphicalView createGraphView()
	{
		int[] colors = new int[] { Color.parseColor("#77c4d3")};
		XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(Orientation.HORIZONTAL);
        //These setting should be based on what type of filter the user has selected.
        setChartSettings(renderer, "tric " + mName, "Timestamp", "Amount", 0,
                12.5, 0, getHighestData(), Color.BLACK, Color.BLACK);
        
        renderer.setXLabels(1);
        renderer.setYLabels(10);
        for(int i = 0;i<mData.size();i++)
        {
        	renderer.addXTextLabel(i+1,mData.get(i).mTimestamp);
        }
        
        int length = renderer.getSeriesRendererCount();
        for(int i = 0;i<length;i++)
        {
        	SimpleSeriesRenderer seriesRenderer = renderer.getSeriesRendererAt(i);
        	seriesRenderer.setDisplayChartValues(true);
        }
        
        final GraphicalView grfv = ChartFactory.getBarChartView(this.getActivity(), buildBarDataset(), renderer, Type.DEFAULT);
        return grfv;
	}
	
	private Integer getHighestData()
	{
		int highest = 0;
		for(int i = 0;i<mData.size();i++)
		{
			//We want to check data type
			if(Integer.parseInt(mData.get(i).mData) > highest)
			{
				highest = Integer.parseInt(mData.get(i).mData);
			}
		}
		return highest;
	}
	
	protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
            String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
            int labelsColor) {
          renderer.setChartTitle(title);
          renderer.setYLabelsAlign(Align.RIGHT);
          renderer.setXTitle(xTitle);
          renderer.setYTitle(yTitle);
          renderer.setXAxisMin(xMin);
          renderer.setXAxisMax(xMax);
          renderer.setYAxisMin(yMin);
          renderer.setYAxisMax(yMax);
          renderer.setMargins(new int[] { 10, 65, 10, 15 });
          renderer.setAxesColor(axesColor);
          renderer.setLabelsColor(labelsColor);
        }
	
	protected XYMultipleSeriesDataset buildBarDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        CategorySeries series = new CategorySeries(mName);
        for(int i = 0;i<mData.size();i++)
        {
        	//We want to check data type
        	series.add(Double.parseDouble(mData.get(i).mData));
        }
        dataset.addSeries(series.toXYSeries());
        return dataset;
      }
	
	protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(16);
        renderer.setChartTitleTextSize(20);
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setBarSpacing(1);
         
        renderer.setMarginsColor(Color.parseColor("#EEEDED"));
        renderer.setXLabelsColor(Color.BLACK);
        renderer.setYLabelsColor(0,Color.BLACK);
         
        renderer.setApplyBackgroundColor(true);
        renderer.setBackgroundColor(Color.parseColor("#FBFBFC"));
         
        int length = colors.length;
        for (int i = 0; i < length; i++) {
          SimpleSeriesRenderer r = new SimpleSeriesRenderer();
          r.setColor(colors[i]);
          //r.setChartvalueAngle(-90);
          r.setChartValuesSpacing(15);
          renderer.addSeriesRenderer(r);
        }
        return renderer;
      }

}
