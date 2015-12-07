package com.example.derek.teamb;


import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class LocationService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private final String TAG = "LocationService";
    protected GoogleApiClient googleApiClient;
    protected Location lastLocation;
    protected LatLng lastLocationLatLng;
    protected double latitude;
    protected double longitude;

    protected double accuracy;
    private Context context;

    private final LatLng lafferreSW = new LatLng(38.945685, -92.330812);
    private final LatLng lafferreNE = new LatLng(38.946480, -92.329265);
    private final LatLng ebwSW = new LatLng(38.946352, -92.331807);
    private final LatLng ebwNE = new LatLng(38.946918, -92.331114);
    private LatLngBounds lafferreBounds = new LatLngBounds(lafferreSW,lafferreNE);
    private LatLngBounds ebwBounds = new LatLngBounds(ebwSW,ebwNE);

    public LocationService(Context context) {
        this.context = context;
        buildGoogleApiClient();
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public LatLng getLastLocationLatLng(){
       lastLocationLatLng = (new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()));
        return lastLocationLatLng;
    }

    public String checkIfInside(){
        Log.d(TAG, "checking if inside");
        if(lafferreBounds.contains(lastLocationLatLng)){
            Log.d(TAG, "inside Lafferre!");
            return "Lafferre";
        }
        else{
            Log.d(TAG, "outside Lafferre!");
            return "Outside";
        }

    }

    public boolean getLocationAvailability(){
        if(LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient).equals(true)){
            return true;
        }
        else{
            return false;
        }
    }

    private void findLastLocation(){
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        Log.d(TAG, "Availability is: " + LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient));
        latitude = lastLocation.getLatitude();
        longitude = lastLocation.getLongitude();
        accuracy = lastLocation.getAccuracy();
        Log.d(TAG, "Latitude is: " + latitude);
        Log.d(TAG, "Longitude is: " + longitude);
        Log.d(TAG, "Accuracy is: " + accuracy);
        lastLocationLatLng = (new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude()));
    }



    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        Log.d(TAG, "Availability is: " + LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient));
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "Connected to Google Play services!");
        findLastLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed!");
    }
}