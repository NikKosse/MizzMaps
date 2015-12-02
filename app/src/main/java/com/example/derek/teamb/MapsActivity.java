package com.example.derek.teamb;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private final LatLng lafferreSW = new LatLng(38.945685, -92.330812);
    private final LatLng lafferreNE = new LatLng(38.946480, -92.329265);
    private final LatLng ebwSW = new LatLng(38.946352, -92.331807);
    private final LatLng ebwNE = new LatLng(38.946918, -92.331114);
    private LatLngBounds lafferreBounds = new LatLngBounds(lafferreSW,lafferreNE);
    private LatLngBounds ebwBounds = new LatLngBounds(ebwSW,ebwNE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        buildGoogleApiClient();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        map.setMyLocationEnabled(true);
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng startingPoint = new LatLng(38.946165, -92.330937);
        map.moveCamera(CameraUpdateFactory.newLatLng(startingPoint));
        map.moveCamera(CameraUpdateFactory.zoomTo(18));

    }

    protected synchronized void buildGoogleApiClient(){

    }

//    public LatLng getCurrentLocation(GoogleMap googleMap){
//
//    }
}

