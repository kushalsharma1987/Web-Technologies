package com.example.weatherapp;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class TodayTab extends Fragment {
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.tab_today, container, false);
        DetailsActivity activity = (DetailsActivity) getActivity();
        String forecastData = activity.getForecastData();
        populateTodayTab(root, forecastData);
        return root;
    }

    private void populateTodayTab(View view, String data) {
        try {
            JSONObject forecastData = new JSONObject(data);
            JSONObject currently = forecastData.getJSONObject("currently");

            ImageView icon = (ImageView) view.findViewById(R.id.today_summary_icon);
            TextView temperature = (TextView) view.findViewById(R.id.today_temperature_value);
            TextView summary = (TextView) view.findViewById(R.id.today_summary_value);
            TextView humidity = (TextView) view.findViewById(R.id.today_humidity_value);
            TextView windSpeed = (TextView) view.findViewById(R.id.today_windspeed_value);
            TextView visibility = (TextView) view.findViewById(R.id.today_visibility_value);
            TextView pressure = (TextView) view.findViewById(R.id.today_pressure_value);
            TextView precipitation = (TextView) view.findViewById(R.id.today_precipitation_value);
            TextView ozone = (TextView) view.findViewById(R.id.today_ozone_value);
            TextView cloudcover = (TextView) view.findViewById(R.id.today_cloudcover_value);

            temperature.setText(Math.round(currently.getDouble("temperature")) + "\u2109");

            switch (currently.getString("icon")) {
                case "clear-day":
                    icon.setImageResource(R.drawable.weather_sunny_48);
                    summary.setText("clear day");
                    break;
                case "clear-night":
                    icon.setImageResource(R.drawable.weather_night_48);
                    summary.setText("clear night");
                    break;
                case "rain":
                    icon.setImageResource(R.drawable.weather_rainy_48);
                    summary.setText("rain");
                    break;
                case "sleet":
                    icon.setImageResource(R.drawable.weather_snowy_rainy_48);
                    summary.setText("sleet");
                    break;
                case "snow":
                    icon.setImageResource(R.drawable.weather_snowy_48);
                    summary.setText("snow");
                    break;
                case "wind":
                    icon.setImageResource(R.drawable.weather_windy_variant_48);
                    summary.setText("wind");
                    break;
                case "fog":
                    icon.setImageResource(R.drawable.weather_fog_48);
                    summary.setText("fog");
                    break;
                case "cloudy":
                    icon.setImageResource(R.drawable.weather_cloudy_48);
                    summary.setText("cloudy");
                    break;
                case "partly-cloudy-night":
                    icon.setImageResource(R.drawable.weather_night_partly_cloudy_48);
                    summary.setText("cloudy night");
                    break;
                case "partly-cloudy-day":
                    icon.setImageResource(R.drawable.weather_partly_cloudy_48);
                    summary.setText("cloudy day");
                    break;
                default:
                    icon.setImageResource(R.drawable.weather_sunny_48);
                    summary.setText("clear");
                    break;
            }

            humidity.setText((Math.round(currently.getDouble("humidity") * 100)) + "%");
            windSpeed.setText(String.format("%.02f", currently.getDouble("windSpeed")) + " mph");
            visibility.setText(String.format("%.02f", currently.getDouble("visibility")) + " km");
            pressure.setText(String.format("%.02f", currently.getDouble("pressure")) + " mb");
            precipitation.setText(String.format("%.02f", currently.getDouble("precipIntensity")) + " mmph");
            ozone.setText(String.format("%.02f", currently.getDouble("ozone")) + " DU");
            cloudcover.setText(Math.round(currently.getDouble("cloudCover") * 100) + "%");

        } catch (JSONException err) {
            Log.d("Error", err.toString());
        }
    }


}

