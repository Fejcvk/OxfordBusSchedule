package schedule.bus.oxford.oxfordbusschedule;

import android.*;
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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Tomasz on 02/10/2017.
 */

public class findBusByLocationActivity extends AppCompatActivity {

    ImageButton imageButton;
    TextView textView;
    private final static int LOCATION_PERMISSION_REQUEST = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_by_location_activity);

        imageButton = (ImageButton) findViewById(R.id.getLocationButton);
        textView = (TextView) findViewById(R.id.permissionTextView);

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
                        textView.setText(currLocaiton(location.getLatitude(),location.getLongitude()));
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                        Toast.makeText(findBusByLocationActivity.this,"Location not found ;___;",Toast.LENGTH_SHORT).show();
                    }
                }
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
                            textView.setText(currLocaiton(location.getLatitude(),location.getLongitude()));
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

    public String currLocaiton(double lat, double lon) throws IOException {
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
}
