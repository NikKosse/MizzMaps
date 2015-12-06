package com.example.derek.teamb;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap map;
    private final String TAG = "Maps activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng startingPoint = new LatLng(38.946165, -92.330937);
        map.moveCamera(CameraUpdateFactory.newLatLng(startingPoint));
        map.moveCamera(CameraUpdateFactory.zoomTo(18));
        testLocationServices();
    }

    public void testLocationServices(){
        Log.d(TAG,"Testing location service!");
        LocationService locationService = new LocationService(this);
        Log.d(TAG, "Result: "+ locationService.checkIfInside());
    }

}