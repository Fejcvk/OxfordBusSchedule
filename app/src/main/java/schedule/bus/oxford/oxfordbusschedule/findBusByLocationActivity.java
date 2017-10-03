package schedule.bus.oxford.oxfordbusschedule;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tomasz on 02/10/2017.
 */

public class findBusByLocationActivity extends AppCompatActivity {

    ImageButton imageButton;
    TextView textView;
    SeekBar seekBar;
    TextView seekBarResultTextView;
    ArrayList<String> busStopNamesArrayList;
    private final static int LOCATION_PERMISSION_REQUEST = 1;
    private final static String GOOGLE_WEB_API_KEY = "AIzaSyCQHEEEDu6vlX63NkpaXDsdXXjPaMEhKek";
    private int radius = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_by_location_activity);

        imageButton = findViewById(R.id.getLocationButton);
        textView = findViewById(R.id.permissionTextView);

        //Setting listener for localize button
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //self check if permission is already granted
                if(ContextCompat.checkSelfPermission(findBusByLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    //if they are not granted request permissions
                    if(ActivityCompat.shouldShowRequestPermissionRationale(findBusByLocationActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION))
                    {
                        ActivityCompat.requestPermissions(findBusByLocationActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(findBusByLocationActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST);
                    }
                }
                //if permissions are already granted proceede to retriving current location
                else
                {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    try {
                        textView.setText(currLocation(location.getLatitude(),location.getLongitude()));
                        loadUpNearByBusStops(location.getLatitude(),location.getLongitude());
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(findBusByLocationActivity.this,"Location not found ;___;",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        seekBar = (SeekBar) findViewById(R.id.radiusSeekBar);
        seekBarResultTextView = findViewById(R.id.radiusTextView);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBarResultTextView.setText("Radius of search : " + String.valueOf(i));
                radius = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    //handling the answer of permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case LOCATION_PERMISSION_REQUEST:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(findBusByLocationActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            textView.setText(currLocation(location.getLatitude(),location.getLongitude()));
                            loadUpNearByBusStops(location.getLatitude(),location.getLongitude());
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                            Toast.makeText(findBusByLocationActivity.this,"Location not found ;___;",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(findBusByLocationActivity.this,"Permission not granted ;__;",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    public String currLocation(double lat, double lon) throws IOException {
        String currCity = "";
        Geocoder geocoder = new Geocoder(findBusByLocationActivity.this, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = geocoder.getFromLocation(lat,lon,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addressList.size() > 0) {
            currCity = addressList.get(0).getLocality();
        }
        return currCity;
    };
    private void loadUpNearByBusStops(double lat, double lon)
    {
        String type = "bus_station";
        StringBuilder googlePlacesUrlQuery = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            googlePlacesUrlQuery.append("location=").append(lat).append(",").append(lon);
            googlePlacesUrlQuery.append("&radius=").append(radius);
            googlePlacesUrlQuery.append("&types=").append(type);
            googlePlacesUrlQuery.append("&key=").append(GOOGLE_WEB_API_KEY);
        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrlQuery.toString(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("OXBUS ","onResponse: Resposne= " + response.toString());
                parseLocationResult(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("OXBUS", "onErrorResponse: Error= " + error);
                        Log.e("OXBUS", "onErrorResponse: Error= " + error.getMessage());
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    private void parseLocationResult(JSONObject response) {
        String name;
        busStopNamesArrayList = new ArrayList();
        try {
            JSONArray jsonArray = response.getJSONArray("responses");
            if(response.getString("status").equalsIgnoreCase("OK"))
            {
                for(int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject currStop = jsonArray.getJSONObject(i);
                    name = currStop.getString("name");
                    busStopNamesArrayList.add(name);
                }
                Toast.makeText(getBaseContext(), jsonArray.length() + " Bus Stops found!", Toast.LENGTH_SHORT).show();
            }else if (response.getString("status").equalsIgnoreCase("ZERO_RESULTS")) {
                Toast.makeText(getBaseContext(), "No Bus Stops found in " + radius + " KM radius ;___;", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("OXBUS", "parseLocationResult: Error= " + e.getMessage());
        }
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        Log.i("Message: ","Location changed, " + location.getAccuracy() + " , " + location.getLatitude()+ "," + location.getLongitude());
//        System.out.println(location.getLatitude() + location.getLongitude());
//    }
}
