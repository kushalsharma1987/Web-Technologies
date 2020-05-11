package com.example.weatherapp;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.SearchView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar spinner;
    private CardView cardView1;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    ArrayList<String> autoSuggestion = new ArrayList<>();


    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    //    private String url = "https://www.mocky.io/v2/5185415ba171ea3a00704eed";
    private String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Irvine&types=(cities)&language=en&key=AIzaSyCOXPDJRvPLiIzyaQKABZVvBQ_TP2X7o68";
//    private String url = "https://kushals-forecast.us-west-2.elasticbeanstalk.com/autocomplete/Irvine";
//    private String url = "http://ip-api.com/json/?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query";

    ArrayAdapter<String> adapter;
    SearchView.SearchAutoComplete searchAutoComplete;
    SearchView searchView;
    ListView listView;
    ArrayList<String> autoCompleteList = new ArrayList<String>();

    String currentLocation;

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner= (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        listenCardClick();
//        listenSearchButton();

        getCurrentLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_item, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

//        spinner.setVisibility(View.GONE);
        // Get the SearchView and set the searchable configuration
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();

        // Assumes current activity is the searchable activity
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true); // Do not iconify the widget; expand it by default


        // Get SearchView autocomplete object.
        searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(Color.GRAY);
        searchAutoComplete.setTextColor(Color.WHITE);
        searchAutoComplete.setDropDownBackgroundResource(R.color.white);
        searchAutoComplete.setThreshold(1);
        searchAutoComplete.setCompletionHint("Select city");

        // Create a new ArrayAdapter and add data to search auto complete object.
//        ArrayList<String> dataArr = new ArrayList<>();
//        dataArr.addAll("Apple" , "Amazon" , "Amd", "Microsoft", "Microwave", "MicroNews", "Intel", "Intelligence");
//
//        autoCompleteList.add("Apple");
//        autoCompleteList.add("Amazon");
//        autoCompleteList.add("Amd");
//        autoCompleteList.add("Microsoft");
//        autoCompleteList.add("Microwave");
//        autoCompleteList.add("MicroNews");
//        autoCompleteList.add("Intel");
//        autoCompleteList.add("Intelligence");
//        ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, autoCompleteList);
        autoSuggestAdapter = new AutoSuggestAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
//        searchAutoComplete.setAdapter(newsAdapter);
        searchAutoComplete.setAdapter(autoSuggestAdapter);

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
                searchAutoComplete.setText("" + queryString);
//                Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
            }
        });

        // Below event is triggered when submit search query.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setMessage("Search keyword is " + query);
//                alertDialog.show();
                Intent i = new Intent(MainActivity.this, SearchableActivity.class);
                i.putExtra("city", query);
//                i.putExtra("currentLocation", currentLocation);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

//                spinner.setVisibility(View.GONE);
//                Toast.makeText(getApplicationContext(), "Text is Changed: " + newText, Toast.LENGTH_SHORT).show();
                getAutoComplete(newText);

//                adapter.getFilter().filter(autoCompleteList);
                return false;
            }
        });

//        listView = (ListView) findViewById(R.id.autoText);

        // Create a new ArrayAdapter and add data to search auto complete object.
//
//        autoCompleteList.add("Apple");
//        autoCompleteList.add("Amazon");
//        autoCompleteList.add("Amd");
//        autoCompleteList.add("Microsoft");
//        autoCompleteList.add("Microwave");
//        autoCompleteList.add("MicroNews");
//        autoCompleteList.add("Intel");
//        autoCompleteList.add("Intelligence");
//
//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, autoCompleteList);
//        searchAutoComplete.setAdapter(adapter);
//
//        // Listen to search view item on click event.
//        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
//                String queryString = (String) adapterView.getItemAtPosition(itemIndex);
//                searchAutoComplete.setText("" + queryString);
//                Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
//            }
//        });
//
////        searchView.setOnQueryTextListener(
////                new SearchView.OnQueryTextListener() {
////                    @Override
////                    public boolean onQueryTextSubmit(String query) {
////                        Toast.makeText(getApplicationContext(), "Query is Submitted: " + query, Toast.LENGTH_SHORT).show();
////                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
////                        alertDialog.setMessage("Search keyword is " + query);
////                        alertDialog.show();
////                        return false;
////                    }
////
////                    @Override
////                    public boolean onQueryTextChange(String newText) {
////                        Toast.makeText(getApplicationContext(), "Text is Changed: " + newText, Toast.LENGTH_SHORT).show();
////                        getAutoComplete(newText);
////                        adapter.getFilter().filter(newText);
////                        return false;
////                    }
////                }
////        );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getAutoComplete(String text) {
        String autocomplete_url = "http://kushals-forecast.us-west-2.elasticbeanstalk.com/autocomplete/" + text;

        makeVolleyRequest(autocomplete_url, "autocomplete");
    }

    private void populateAutocomplete(JSONObject jsonObject) throws JSONException {
        JSONArray predictions = jsonObject.getJSONArray("predictions");
        autoCompleteList.clear();
        for (int i = 0; i < predictions.length() && i < 5; i++) {
            JSONObject city = predictions.getJSONObject(i);
            autoCompleteList.add(city.getString("description"));
        }
        autoSuggestAdapter.setData(autoCompleteList);
        autoSuggestAdapter.notifyDataSetChanged();

//        Toast.makeText(getApplicationContext(), "Autocomplete List: " + autoCompleteList, Toast.LENGTH_SHORT).show();
    }

    private void populateLocation(JSONObject jsonObject) throws JSONException {
        TextView current_location = (TextView) findViewById(R.id.home_place_text);

        String city = jsonObject.getString("city");
        String region = jsonObject.getString("region");
        String countryCode = jsonObject.getString("countryCode");
        Double latitude = jsonObject.getDouble("lat");
        Double longitude = jsonObject.getDouble("lon");
        currentLocation = city + ", " + region + ", " + countryCode;

        current_location.setText(currentLocation);

        getForecastData(latitude, longitude);
    }

    private void listenCardClick() {

        cardView1 = (CardView) findViewById(R.id.card_view_1);

        cardView1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent i;

        if (v.getId() == R.id.card_view_1) {
            i = new Intent(MainActivity.this, DetailsActivity.class);
            i.putExtra("forecastData", jsonResponse.toString());
            i.putExtra("currentLocation", currentLocation);
            startActivity(i);
        }

//        if (v.getId() == R.id.action_search) {
//            i = new Intent(MainActivity.this, SearchableActivity.class);
//            i.putExtra("forecastData", jsonResponse.toString());
//            startActivity(i);
//        }
    }

//    @Override
//    public void onProviderDisabled(String provider) {
//        Log.d("Latitude","disable");
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        Log.d("Latitude","enable");
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        Log.d("Latitude","status");
//    }


    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "Entering the button: " + "I am in button clicked..");

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Entering the Response:" + response);
//                Toast.makeText(getApplicationContext(), "Response :" + response, Toast.LENGTH_LONG).show();//display the response on screen

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }

//    private void listenSearchButton() {
//        btnRequest = (Button) findViewById(R.id.buttonRequest);
//        btnRequest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                  sendAndRequestResponse();
//
//            }
//        });
//    }


    private void getCurrentLocation() {

        String current_url = "http://ip-api.com/json/?fields=status,message,country,countryCode,region,regionName,city,zip,lat,lon,timezone,isp,org,as,query";
        makeVolleyRequest(current_url, "currentloc");
//
//        txtLat = (TextView) findViewById(R.id.text);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
////        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,this, null);
    }

//    @Override
//    public void onLocationChanged(Location location) {
////        txtLat = (TextView) findViewById(R.id.text);
////        txtLat.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
//        try {
//            getForecastData(location.getLatitude(), location.getLongitude());
//            locationManager.removeUpdates(this);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void getForecastData(double Lat, double Long) throws JSONException {
//        String forecast_apikey = "797fe7f0bdb17655c868cd29e5fa7fe5";
//        String forecast_url = "https://api.darksky.net/forecast/" + forecast_apikey + "/" + Lat + "," + Long;

        String forecast_url = "http://kushals-forecast.us-west-2.elasticbeanstalk.com/forecast/" + Lat + "," + Long;

        makeVolleyRequest(forecast_url, "forecast");

    }

    private void populateHomeCard(JSONObject jsonObject) throws JSONException {
        JSONObject currently = jsonObject.getJSONObject("currently");
        JSONObject daily = jsonObject.getJSONObject("daily");
        JSONArray dailyArray = daily.getJSONArray("data");


        ImageView icon = (ImageView) findViewById(R.id.home_temp_icon);
        TextView temperature = (TextView) findViewById(R.id.home_temp_text);
        TextView summary = (TextView) findViewById(R.id.home_temp_summary);
        TextView humidity = (TextView) findViewById(R.id.home_humidity_value);
        TextView windSpeed = (TextView) findViewById(R.id.home_windspeed_value);
        TextView visibility = (TextView) findViewById(R.id.home_visibility_value);
        TextView pressure = (TextView) findViewById(R.id.home_pressure_value);


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
        dateview = (TextView) findViewById(R.id.home_scroll_date_1);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_1);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_1);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_1);

        JSONObject dayData = dailyArray.getJSONObject(0);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");

        // #Day 2 data
        dateview = (TextView) findViewById(R.id.home_scroll_date_2);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_2);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_2);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_2);

        dayData = dailyArray.getJSONObject(1);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");

        // #Day 3 data
        dateview = (TextView) findViewById(R.id.home_scroll_date_3);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_3);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_3);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_3);

        dayData = dailyArray.getJSONObject(2);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");

        // #Day 4 data
        dateview = (TextView) findViewById(R.id.home_scroll_date_4);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_4);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_4);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_4);

        dayData = dailyArray.getJSONObject(3);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 5 data
        dateview = (TextView) findViewById(R.id.home_scroll_date_5);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_5);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_5);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_5);

        dayData = dailyArray.getJSONObject(4);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 6 data
        dateview = (TextView) findViewById(R.id.home_scroll_date_6);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_6);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_6);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_6);

        dayData = dailyArray.getJSONObject(5);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 7 data
        dateview = (TextView) findViewById(R.id.home_scroll_date_7);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_7);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_7);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_7);

        dayData = dailyArray.getJSONObject(6);

        unixSeconds = dayData.getLong("time");
        formattedDate = getFormattedDate(timezone, unixSeconds);
        dateview.setText(formattedDate);

        imageID = getIcon(dayData.getString("icon"));
        imageview.setImageResource(imageID);

        mintemp.setText(Math.round(dayData.getDouble("temperatureLow")) + "");

        maxtemp.setText(Math.round(dayData.getDouble("temperatureHigh")) + "");


        // #Day 8 data
        dateview = (TextView) findViewById(R.id.home_scroll_date_8);
        imageview = (ImageView) findViewById(R.id.home_scroll_icon_8);
        mintemp = (TextView) findViewById(R.id.home_scroll_mintemp_8);
        maxtemp = (TextView) findViewById(R.id.home_scroll_maxtemp_8);

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

    private JSONObject jsonResponse;

    public void makeVolleyRequest(String URL, String caller) {
        //RequestQueue initialized
        final String identifier = caller;
        mRequestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "Entering the button: " + "I am in button clicked..");
//        spinner.setVisibility(View.VISIBLE);
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Entering the Response: " + response);
//                Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                try {
                    jsonResponse = new JSONObject(response);
                    if (identifier.contains("forecast")) {
                        populateHomeCard(jsonResponse);
                    }
                    if (identifier.contains("autocomplete")) {
                        populateAutocomplete(jsonResponse);
                    }
                    if (identifier.contains("currentloc")) {
                        populateLocation(jsonResponse);
                    }
//                    spinner.setVisibility(View.GONE);
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
