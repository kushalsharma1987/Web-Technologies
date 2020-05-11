package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class DetailsActivity extends AppCompatActivity {

    private ProgressBar spinner;
    private String forecastData;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    String location;
    private static final String TAG = DetailsActivity.class.getName();
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    ArrayList<String> listImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        spinner= (ProgressBar) findViewById(R.id.progressBar);


        Intent intent = getIntent();
        forecastData = intent.getStringExtra("forecastData");
        location = intent.getStringExtra("currentLocation");

        setTitle(location);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new TodayTab(), "TODAY");
        adapter.addFragment(new WeeklyTab(), "WEEKLY");
        adapter.addFragment(new PhotosTab(), "PHOTOS");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.calendar_today);
        tabLayout.getTabAt(1).setIcon(R.drawable.trending_up);
        tabLayout.getTabAt(2).setIcon(R.drawable.google_photos);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setElevation(0);

//        URL locationUrl = null;
//        try {
//            locationUrl = new URL(URLEncoder.encode(location,"UTF-8"));
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        String search_engine_id = "006875571475360367151:dinqdswyirz";
        String search_apikey = "AIzaSyAlr1MpY6Iesx32PhwQTeFZzLMTBa3REl0";
        String search_url = "https://www.googleapis.com/customsearch/v1?q=" + location + "&cx=" + search_engine_id +
                "&imgSize=large&imgType=news&num=8&searchType=image&key=" + search_apikey;
        Log.d(TAG, "search URL: " + search_url);
        makeVolleyRequest(search_url, "searchurl");
    }

    private void processJSONImageUrl(JSONObject jsonObject) {
        Log.d(TAG, "JSON value received from searchImage: " + jsonObject.toString());
        listImage.clear();
        try {
            JSONArray item = jsonObject.getJSONArray("items");
            for (int i = 0; i < item.length() && i < 8; i++) {
                listImage.add(item.getJSONObject(i).getString("link"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "ImageURL arraylist: " + listImage);
    }

    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        spinner.setVisibility(View.VISIBLE);
        super.onBackPressed();  // optional depending on your needs
    }

    public String getForecastData() {
        return forecastData;
    }

    public ArrayList<String> getListImageUrl() {
        return listImage;
    }

    private JSONObject jsonResponse;

    public void makeVolleyRequest(String URL, String caller) {
        //RequestQueue initialized
        final String identifier = caller;
        mRequestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "Entering the button: " + "I am in button clicked..");

        spinner.setVisibility(View.VISIBLE);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Entering the Response: " + response);
//                Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    jsonResponse = new JSONObject(response);
                    if (identifier.contains("searchurl")) {
                        processJSONImageUrl(jsonResponse);
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
}