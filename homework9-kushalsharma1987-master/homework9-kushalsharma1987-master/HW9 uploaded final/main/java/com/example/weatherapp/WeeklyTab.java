package com.example.weatherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WeeklyTab extends Fragment {

    private static final String TAG = "WeeklyTab";

    private LineChart mChart;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_weekly, container, false);

        DetailsActivity activity = (DetailsActivity) getActivity();
        String forecastData = activity.getForecastData();
        populateWeeklyTab(root, forecastData);
        return root;
    }

    private void populateWeeklyTab(View view, String data) {

        try {
            JSONObject forecastData = new JSONObject(data);
            JSONObject daily = forecastData.getJSONObject("daily");
            JSONArray dailyArray = daily.getJSONArray("data");
//            JSONObject currently = forecastData.getJSONObject("currently");

            ImageView icon = (ImageView) view.findViewById(R.id.weekly_icon);
            TextView summary = (TextView) view.findViewById(R.id.weekly_summary);

            summary.setText(daily.getString("summary"));
            switch (daily.getString("icon")) {
                case "clear-day":
                    icon.setImageResource(R.drawable.weather_sunny_48);
                    break;
                case "clear-night":
                    icon.setImageResource(R.drawable.weather_night_48);
                    break;
                case "rain":
                    icon.setImageResource(R.drawable.weather_rainy_48);
                    break;
                case "sleet":
                    icon.setImageResource(R.drawable.weather_snowy_rainy_48);
                    break;
                case "snow":
                    icon.setImageResource(R.drawable.weather_snowy_48);
                    break;
                case "wind":
                    icon.setImageResource(R.drawable.weather_windy_variant_48);
                    break;
                case "fog":
                    icon.setImageResource(R.drawable.weather_fog_48);
                    break;
                case "cloudy":
                    icon.setImageResource(R.drawable.weather_cloudy_48);
                    break;
                case "partly-cloudy-night":
                    icon.setImageResource(R.drawable.weather_night_partly_cloudy_48);
                    break;
                case "partly-cloudy-day":
                    icon.setImageResource(R.drawable.weather_partly_cloudy_48);
                    break;
                default:
                    icon.setImageResource(R.drawable.weather_sunny_48);
                    break;
            }


            LineChart mChart = (LineChart) view.findViewById(R.id.linechart);

//        mChart.setOnChartGestureListener(WeeklyTab.this);
//        mChart.setOnChartValueSelectedListener(WeeklyTab.this);

            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);

//        mChart.getAxisLeft().setTextColor(...); // left y-axis
//        mChart.getXAxis().setTextColor(...);
//        mChart.getLegend().setTextColor(...);

//        mChart.setTouchEnabled(true);
//        mChart.setPinchZoom(true);

            ArrayList<Entry> lowTempValues = new ArrayList<>();
            ArrayList<Entry> highTempValues = new ArrayList<>();

            for (int i = 0; i < dailyArray.length(); i++) {
                lowTempValues.add(new Entry(i, (float) dailyArray.getJSONObject(i).getDouble("temperatureLow")));
                highTempValues.add(new Entry(i, (float) dailyArray.getJSONObject(i).getDouble("temperatureHigh")));
            }

//        lowTempValues.add(new Entry(0,60f));
//        lowTempValues.add(new Entry(1,50f));
//        lowTempValues.add(new Entry(2,20f));
//        lowTempValues.add(new Entry(3,70f));
//        lowTempValues.add(new Entry(4,40f));
//        lowTempValues.add(new Entry(5,65f));
//        lowTempValues.add(new Entry(6,10f));
//        lowTempValues.add(new Entry(7,40f));
//        lowTempValues.add(new Entry(8,90f));
//        lowTempValues.add(new Entry(9,80f));
//        lowTempValues.add(new Entry(10,20f));

            LineDataSet set1 = new LineDataSet(lowTempValues, "Minimum Temperature");
            LineDataSet set2 = new LineDataSet(highTempValues, "Maximum Temperature");


            set1.setColor(Color.rgb(147, 112, 219));


            set2.setColor(Color.rgb(255, 191, 0));


            mChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
            mChart.getAxisRight().setTextColor(Color.WHITE); // right y-axis

            mChart.getXAxis().setTextColor(Color.WHITE);

            mChart.getXAxis().setDrawGridLines(false);
//        mChart.getAxisLeft().setDrawGridLines(false);
//        mChart.getAxisRight().setDrawGridLines(false);

            mChart.getAxisLeft().setGranularityEnabled(true);
            mChart.getAxisLeft().setGranularity(10);

            mChart.getAxisRight().setGranularityEnabled(true);
            mChart.getAxisRight().setGranularity(10);

//        mChart.getAxisRight().setDrawZeroLine(false);
//        mChart.getAxisRight().setSpaceMin(10);
//        mChart.getAxisRight().setSpaceMin(20);
//
//        mChart.getAxisLeft().setSpaceMin(10);
//        mChart.getAxisLeft().setSpaceMin(20);

            mChart.getLegend().setTextColor(Color.WHITE);
            mChart.getLegend().setTextSize(15);
            mChart.getLegend().setFormSize(15);

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            dataSets.add(set2);

            LineData linedata = new LineData(dataSets);

            mChart.setData(linedata);

        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }

    }


}
