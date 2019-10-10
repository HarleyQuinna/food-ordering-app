package com.example.sylviachen.alexandrostakeout;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

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
        mMap = googleMap;
        double lat = 43.641476;
        double lang = -79.375259;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng position = new LatLng(lat,lang);
        final String add = getAddress(this,lat, lang);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.logo);
        mMap.addMarker(new MarkerOptions().position(position).title(add));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {

                return null;
            }

        @Override
        public View getInfoContents(Marker marker) {
            View v = getLayoutInflater().inflate(R.layout.activity_image__layout, null);


            final ImageView info1 = (ImageView) v.findViewById(R.id.imageView);
            final TextView tvAdd1=v.findViewById(R.id.tvAddress);
            //final TextView tvAdd2=v.findViewById(R.id.tvMap);

            info1.setImageDrawable(getDrawable(R.drawable.logo));
            tvAdd1.setText(add);
            return v;
        }
    });

}
    private String getAddress(Context ctxt, double lat, double lang) {
        String fullAdd = new String();

        try
        {
            Geocoder geocoder = new Geocoder(ctxt, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(lat,lang,1);
            //if (addresses != null && addresses.size() > 0) {
              Address address = addresses.get(0);
             // sending back first address line and locality
               fullAdd = address.getAddressLine(0) + ", " + address.getLocality();
            String address0 = addresses.get(0).getAddressLine(0);
            String address1 = addresses.get(0).getAddressLine(1);
            String postal = addresses.get(0).getPostalCode();
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();

            fullAdd = address0;
            //}


        }
        catch(IOException ex)
        {
            //ex.getMessage();
            Log.e("test",ex.getMessage());
        }
        return fullAdd;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
