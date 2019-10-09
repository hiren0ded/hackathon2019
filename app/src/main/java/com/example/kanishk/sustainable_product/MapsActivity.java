package com.example.kanishk.sustainable_product;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{//, GoogleApiClient.ConnectionCallbacks,
        //GoogleApiClient.OnConnectionFailedListener, LocationListner {

    private GoogleMap mMap;
    Location mlocation;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //location = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng sydney = new LatLng(18.520430, 73.856743);
        LatLng res1 = new LatLng(18.5382, 73.8863);
        LatLng res2 = new LatLng(18.5351, 73.8831);
        LatLng res3 = new LatLng(18.5299, 73.8713);
        LatLng res4 = new LatLng(18.5382, 73.8863);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Pune"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addMarker(new MarkerOptions().position(res1).title("res1 pe leke chalo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(res1));
        mMap.addMarker(new MarkerOptions().position(res2).title("res2 pe leke chalo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(res2));
        mMap.addMarker(new MarkerOptions().position(res3).title("res3 pe leke chalo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(res3));
        mMap.addMarker(new MarkerOptions().position(res4).title("res4 pe leke chalo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(res4));

        //LatLng myloc = new LatLng(location.getLatitude(), location.getLongitude());
        //location.addMarker(new MarkerOptions().position(myloc).title("my location"));
    }
}
