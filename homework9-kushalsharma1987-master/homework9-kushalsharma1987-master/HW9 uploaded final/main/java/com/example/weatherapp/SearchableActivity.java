package com.example.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class SearchableActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = SearchableActivity.class.getName();
    String city;
    private CardView cardView1;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        spinner= (ProgressBar) findViewById(R.id.progressBar);


        Intent intent = getIntent();
        city = intent.getStringExtra("city");

        Log.d("city", city);

        setTitle(city);
        TextView search_location = (TextView) findViewById(R.id.searchable_place_text);
        search_location.setText(city);
        getLatLon(city);

        listenCardClick();
    }
    private void getLatLon(String city) {


        String geocode_url = "http://kushals-forecast.us-west-2.elasticbeanstalk.com/geocode/" + city;
        makeVolleyRequest(geocode_url, "searchloc");
    }

    private void populateLocation(JSONObject jsonObject) throws JSONException {

        JSONArray results = jsonObject.getJSONArray("results");
        JSONObject data = results.getJSONObject(0);
        JSONObject geometry = data.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");

        Double latitude = location.getDouble("lat");
        Double longitude = location.getDouble("lng");

        getForecastData(latitude, longitude);
    }

    private void getForecastData(double Lat, double Long) throws JSONException {

        String forecast_url = "http://kushals-forecast.us-west-2.elasticbeanstalk.com/forecast/" + Lat + "," + Long;

        makeVolleyRequest(forecast_url, "forecast");

    }

    private JSONObject jsonResponse;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    public void makeVolleyRequest(String URL, String caller) {
        //RequestQueue initialized
        final String identifier = caller;
        spinner.setVisibility(View.VISIBLE);
        mRequestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "Entering the button: " + "I am in button clicked..");
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Entering the Response: " + response);
//                Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    jsonResponse = new JSONObject(response);
                    if (identifier.contains("forecast")) {
                        populateSearchCard(jsonResponse);
                    }
                    if (identifier.contains("searchloc")) {
                        populateLocation(jsonResponse);
                    }
                    spinner.setVisibility(View.GONE);
                } catch (JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

    private void listenCardClick() {

        cardView1 = (CardView) findViewById(R.id.searchable_card_view_1);

        cardView1.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        Intent i;

        if (v.getId() == R.id.searchable_card_view_1) {
            i = new Intent(SearchableActivity.this, DetailsActivity.class);
            i.putExtra("forecastData", jsonResponse.toString());
            i.putExtra("currentLocation", city);
            startActivity(i);
        }

//        if (v.getId() == R.id.action_search) {
//            i = new Intent(MainActivity.this, SearchableActivity.class);
//            i.putExtra("forecastData", jsonResponse.toString());
//            startActivity(i);
//        }
    }

    private void populateSearchCard(JSONObject jsonObject) throws JSONException {
        JSONObject currently = jsonObject.getJSONObject("currently");
        JSONObject daily = jsonObject.getJSONObject("daily");
        JSONArray dailyArray = daily.getJSONArray("data");

        ImageView icon = (ImageView) findViewById(R.id.searchable_temp_icon);
        TextView temperature = (TextView) findViewById(R.id.searchable_temp_text);
        TextView summary = (TextView) findViewById(R.id.searchable_temp_summary);
        TextView humidity = (TextView) findViewById(R.id.searchable_humidity_value);
        TextView windSpeed = (TextView) findViewById(R.id.searchable_windspeed_value);
        TextView visibility = (TextView) findViewById(R.id.searchable_visibility_value);
        TextView pressure = (TextView) findViewById(R.id.searchable_pressure_value);


        temperature.setText(Math.round(currently.getDouble("temperature")) + "\u2109");
        summary.setText(currently.getString("summary"));
        switch (currently.getString("icon")) {
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

        humidity.setText(Math.round(currently.getDouble("humidity") * 100) + "%");
        windSpeed.setText(String.format("%.02f", currently.getDouble("windSpeed")) + " mph");
        visibility.setText(String.format("%.02f", currently.getDouble("visibility")) + " km");
        pressure.setText(String.format("%.02f", currently.getDouble("pressure")) + " mb");

        String timezone = jsonObject.getString("timezone");
        // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
// give a timezone reference for formatting (see comment at the bottom)
//            TimeZone timezone = TimeZone.getTimeZone(jsonObject.getString("timezone"));
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));

        long unixSeconds;
        TextView dateview;
        ImageView imageview;
        TextView mintemp;
        TextView maxtemp;
        int imageID;
        String formattedDate;

        // #Day 1 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_1);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_1);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_1);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_1);

        JSONObject dayData = dailyArray.getJSONObject(0);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");

        // #Day 2 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_2);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_2);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_2);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_2);

        dayData = dailyArray.getJSONObject(1);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");

        // #Day 3 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_3);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_3);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_3);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_3);

        dayData = dailyArray.getJSONObject(2);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");

        // #Day 4 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_4);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_4);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_4);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_4);

        dayData = dailyArray.getJSONObject(3);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 5 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_5);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_5);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_5);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_5);

        dayData = dailyArray.getJSONObject(4);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 6 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_6);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_6);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_6);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_6);

        dayData = dailyArray.getJSONObject(5);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 7 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_7);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_7);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_7);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_7);

        dayData = dailyArray.getJSONObject(6);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 8 data
        dateview = (TextView) findViewById(R.id.searchable_scroll_date_8);
        imageview = (ImageView) findViewById(R.id.searchable_scroll_icon_8);
        mintemp = (TextView) findViewById(R.id.searchable_scroll_mintemp_8);
        maxtemp = (TextView) findViewById(R.id.searchable_scroll_maxtemp_8);

        dayData = dailyArray.getJSONObject(7);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");

    }

    private String getFormattedDate(String timezone, Long unixSeconds) {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setTimeZone(TimeZone.getTimeZone(timezone));
        Date date = new Date(unixSeconds * 1000L);
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    private int getIcon(String icon) {
        int imageID;
        switch (icon) {
            case "clear-day":
                imageID = R.drawable.weather_sunny_48;
                break;
            case "clear-night":
                imageID = R.drawable.weather_night_48;
                break;
            case "rain":
                imageID = R.drawable.weather_rainy_48;
                break;
            case "sleet":
                imageID = R.drawable.weather_snowy_rainy_48;
                break;
            case "snow":
                imageID = R.drawable.weather_snowy_48;
                break;
            case "wind":
                imageID = R.drawable.weather_windy_variant_48;
                break;
            case "fog":
                imageID = R.drawable.weather_fog_48;
                break;
            case "cloudy":
                imageID = R.drawable.weather_cloudy_48;
                break;
            case "partly-cloudy-night":
                imageID = R.drawable.weather_night_partly_cloudy_48;
                break;
            case "partly-cloudy-day":
                imageID = R.drawable.weather_partly_cloudy_48;
                break;
            default:
                imageID = R.drawable.weather_sunny_48;
                break;
        }
        return imageID;
    }
}
