package com.example.wsugooglemaps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.constants.ListAppsActivityContract;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//please write code comments to make it readable 
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    boolean isPermissionGrnted;
    GoogleMap mGoogleMap;
    FloatingActionButton fab;
    private FusedLocationProviderClient mLocationClient;
    private Object initMap;
    private int GPS_REQUEST_CODE = 9001;

    EditText locSearch;
    ImageView searchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);

        locSearch = findViewById(R.id.et_search);
        searchIcon = findViewById(R.id.search_icon);

        // what is the purpose of this function?
        //to check/ ask for user permission to get their current location
        checkMyPermission();

        initMap();

        mLocationClient = new FusedLocationProviderClient(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLoc();
            }
        });

        Spinner popularLocations = findViewById(R.id.spinner);
        popularLocations.setOnItemSelectedListener(this);

        List<String> listOfLocations = new ArrayList<String>();
        listOfLocations.add("Wayne State University Department of Physics and Astronomy");
        listOfLocations.add("General Lectures");
        listOfLocations.add("Prentis Building");
        listOfLocations.add("DeRoy Auditorium");
        listOfLocations.add("Science Hall");
        listOfLocations.add("Life Science Building");

        ArrayAdapter<String> popularLocationsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listOfLocations);
        popularLocations.setAdapter(popularLocationsAdapter);

        searchIcon.setOnClickListener(this::geoLocate);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view,
                               int position, long id) {
        switch(parent.getId())
        {
            case R.id.spinner:
                if(position == 0)
                {
                    gotoLocation(42.35400386604689, -83.06949827390679);
                }
                if(position == 1)
                {
                    gotoLocation(42.35511182951506, -83.07211836611843);
                }
                if(position == 2)
                {
                    gotoLocation(42.35801421215564, -83.06823415994502);
                }
                if(position == 3)
                {
                    gotoLocation(42.35770959568507, -83.06863408283621);
                }
                if(position == 4)
                {
                    gotoLocation(42.35633548159699, -83.06742468849461);
                }
                if(position == 5)
                {
                    gotoLocation(42.355811104191346, -83.06861066966165);
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void geoLocate(View view) {
        String locationName = locSearch.getText().toString();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addressList = geocoder.getFromLocationName(locationName, 1);

            if (addressList.size()>0) {
                Address address = addressList.get(0);

                gotoLocation(address.getLatitude(), address.getLongitude());

                mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(), address.getLongitude())));

                Toast.makeText(this, address.getLocality(), Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {

        }
    }




    private  void  initMap() {
        if (isPermissionGrnted){
            if(isGPSenable()) {
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
                supportMapFragment.getMapAsync(this);
            }
        }

    }

    private boolean isGPSenable() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (providerEnable) {
            return true;
        }
        else {

            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage ("GPS is required for this app to work. Please enable GPS")
                    .setPositiveButton("yes", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }

        return false;
    }

    @SuppressLint("MissingPermission")
    private void  gotoCurrentLoc() {
        mLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Location location = task.getResult();
                 
                 LatLng LatLng = new LatLng(location.getLatitude(), location.getLongitude());

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng, 18);
                mGoogleMap.moveCamera(cameraUpdate);
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng LatLng = new LatLng(latitude, longitude);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng, 18);
        mGoogleMap.moveCamera(cameraUpdate);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    private void checkMyPermission() {

        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
                isPermissionGrnted = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), "");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        //mGoogleMap.setMyLocationEnabled(true);

        //Location marker for Wayne State University
        LatLng wsu = new LatLng(42.35740456607535, -83.06532964687997);
        mGoogleMap.addMarker(new MarkerOptions().position(wsu).title("Marker at Wayne State University"));
        //Location marker for Wayne State University Department of Physics and Astronomy
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(wsu, 18));
        LatLng physicsbuild = new LatLng(42.35400386604689, -83.06949827390679);
        mGoogleMap.addMarker(new MarkerOptions().position(physicsbuild).title("Wayne State University Department of Physics and" +
                "Astronomy"));
        //Location marker for General Lectures building
        LatLng generalLec = new LatLng(42.35511182951506, -83.07211836611843);
        mGoogleMap.addMarker(new MarkerOptions().position(generalLec).title("General Lectures"));
        //Location marker for Prentis Building
        LatLng prentis = new LatLng(42.35801421215564, -83.06823415994502);
        mGoogleMap.addMarker(new MarkerOptions().position(prentis).title("Prentis Building"));
        //Location marker for DeRoy Auditorium
        LatLng DeRoyAud = new LatLng(42.35770959568507, -83.06863408283621);
        mGoogleMap.addMarker(new MarkerOptions().position(DeRoyAud).title("DeRoy Auditorium"));
        //Locatoin marker for Science Hall
        LatLng SciHall = new LatLng(42.35633548159699, -83.06742468849461);
        mGoogleMap.addMarker(new MarkerOptions().position(SciHall).title("Science Hall"));
        //Location marker for Life Science Building
        LatLng lifesci = new LatLng(42.355811104191346, -83.06861066966165);
        mGoogleMap.addMarker(new MarkerOptions().position(lifesci).title("Life Science Building"));



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GPS_REQUEST_CODE) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            boolean providerEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (providerEnable) {
                Toast.makeText(this, "GPS is enable", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "GPS is not enable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
