package com.example.derek.teamb;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;



public class UserLocation implements LocationListener{

    final String TAG = "UserLocation";

    private final LatLng lafferreSW = new LatLng(38.945685, -92.330812);
    private final LatLng lafferreNE = new LatLng(38.946480, -92.329265);
    private final LatLng ebwSW = new LatLng(38.946352, -92.331807);
    private final LatLng ebwNE = new LatLng(38.946918, -92.331114);
    private LatLngBounds lafferreBounds = new LatLngBounds(lafferreSW,lafferreNE);
    private LatLngBounds ebwBounds = new LatLngBounds(ebwSW,ebwNE);

    @Override
    public void onLocationChanged(Location location) {
        String provider = location.getProvider();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        float accuracy = location.getAccuracy();

        Log.d(TAG, "******************Provider is: " + provider);
        Log.d(TAG, "******************Longitude is: " + lng);
        Log.d(TAG, "******************Latitude is: " + lat);
        Log.d(TAG, "******************Accuracy is: " + accuracy);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "******************Provider enabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "******************Provider disabled: " + provider);
    }
}
