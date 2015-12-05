package com.example.derek.teamb;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {

    private GoogleMap map;
    private final String TAG = "Maps activity";
    protected GoogleApiClient mGoogleApiClient;
    protected LocationRequest mLocationRequest;
    protected Location mLastLocation;
    protected double mLatitude;
    protected double mLongitude;
    protected double accuracy;

//    private final LatLng lafferreSW = new LatLng(38.945685, -92.330812);
//    private final LatLng lafferreNE = new LatLng(38.946480, -92.329265);
//    private final LatLng ebwSW = new LatLng(38.946352, -92.331807);
//    private final LatLng ebwNE = new LatLng(38.946918, -92.331114);
//    private LatLngBounds lafferreBounds = new LatLngBounds(lafferreSW,lafferreNE);
//    private LatLngBounds ebwBounds = new LatLngBounds(ebwSW,ebwNE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng startingPoint = new LatLng(38.946165, -92.330937);
        map.moveCamera(CameraUpdateFactory.newLatLng(startingPoint));
        map.moveCamera(CameraUpdateFactory.zoomTo(18));
        getSingleLocation();
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest(){
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(10000 / 2);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void stopListening(){
        Log.d(TAG, "Stopped listening!");

    }

    public void getRecentLocation(){
        Log.d(TAG, "Getting recent location");
    }

    public void getSingleLocation(){
        Log.d(TAG, "******************************Getting single location!******************************");
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        LocationAvailability availability = LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        Log.d(TAG, "******************************Location availability is: " + availability + "******************************");

        if(mLastLocation != null){
            mLatitude = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
            accuracy = mLastLocation.getAccuracy();
            Log.d(TAG, "******************************Last Latitude: " + mLatitude + "********************************");
            Log.d(TAG, "******************************Last Longitude: " + mLongitude + "********************************");
            Log.d(TAG, "******************************Accuracy: " + accuracy + "********************************");

            LatLng lastLocation = new LatLng(mLatitude,mLongitude);
            map.addMarker(new MarkerOptions().position(lastLocation).title("Last known location!"));
        }
        else{
            Log.d(TAG, "******************************Last location was null :( ******************************");
        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnectionSuspended(int i){

    }

    @Override
    public void onConnected(Bundle bundle){

    }
}

