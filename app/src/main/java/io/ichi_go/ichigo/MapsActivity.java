package io.ichi_go.ichigo;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapClickListener,
        OnMapLongClickListener {

    public static LatLng latLng;
    public static final String TAG = MapsActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private android.support.v7.widget.Toolbar toolbar;
    ChannelDrawerFragment channelDrawerFragment;
    NavigationDrawerFragment drawerFragment;


    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        setUpMapIfNeeded();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        SQLdb info = new SQLdb(this);
        info.open();
        info.dumpDB();
//        info.initDB();
//        info.populate();
        info.close();
        Log.d("DB","Update DB");
        Log.d("DB","Update DB");
        Log.d("DB","Update DB");

    }
    @Override
    protected void onStart() {
        super.onStart();

        int opt;
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        opt = getSupportActionBar().getDisplayOptions();
        Log.d("Maps","onStart");
        Log.d("Maps","onStart");
        Log.d("Maps", "onStart");
        System.out.println(opt);
        drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        channelDrawerFragment = (ChannelDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_channel_drawer);
        channelDrawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_channel_drawer) {
            ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(Gravity.RIGHT);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();

        SQLdb info = new SQLdb(this);
        info.open();
        info.dumpDB();
//        info.initDB();
//        info.populate();
        info.close();
        Log.d("DB","Update DB");
        Log.d("DB","Update DB");
        Log.d("DB","Update DB");

        Log.d("Maps","onResume");
        Log.d("Maps","onResume");
        Log.d("Maps","onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {

            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, MapsActivity.this);
            mGoogleApiClient.disconnect();
        }
    }


    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

            //Set the customized marker window
            if (mMap != null) {
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View view = getLayoutInflater().inflate(R.layout.marker_info_window, null);
                        TextView infoName = (TextView) view.findViewById(R.id.marker_info_name);
                        TextView infoLat = (TextView) view.findViewById(R.id.marker_info_latitude);
                        TextView infoLong = (TextView) view.findViewById(R.id.marker_info_longitude);

                        LatLng latLng = marker.getPosition();

                        infoName.setText(marker.getTitle());
                        infoLat.setText("Latitude: " + latLng.latitude);
                        infoLong.setText("Longitude: " + latLng.longitude);

                        return view;
                    }
                });
            }


            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }

        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        mapSettings.setMyLocationButtonEnabled(true);



    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
        Double lat = new Double(CurrentLocation.getLatitude());
        Double lon = new Double(CurrentLocation.getLongitude());
        String name = CurrentLocation.getName();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(name));
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        List<NavItem> data = new ArrayList<>();


        SQLdb info = new SQLdb(this);
        info.open();

        ArrayList<Event> biblio2 = info.getData2();

        info.dumpDB();

//        if(UpdateFlag.getUpdate() == 1) {
//        info.initDB();

//        }
//
//        info.populate();

        info.close();


        Event sco2;
        String name2;

        int len1 = 14;


        for (int i = 0; i < biblio2.size(); i++) {
            len1 = 14;
            sco2 = biblio2.get(i);
            if (sco2.getName().length() < len1) {
                len1 = sco2.getName().length();
            }

            name2 = String.format("%-15s", sco2.getName().substring(0, len1));

             lat = new Double(sco2.getLatitude());
             lon = new Double(sco2.getLongitude());

            mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(name2));

        }

        Log.d("Maps","setUpMap");
        Log.d("Maps","setUpMap");
        Log.d("Maps","setUpMap");

    }

    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            startLocationUpdates();
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            handleNewLocation(location);
        }
        ;

        Log.i(TAG, "Location services connected.");


        SQLdb info = new SQLdb(this);
        info.open();
        info.dumpDB();
//        info.initDB();
//        info.populate();
        info.close();
        Log.d("DB","Update DB");
        Log.d("DB","Update DB");
        Log.d("DB","Update DB");

        Log.d("Maps","onConnected");
        Log.d("Maps","onConnected");
        Log.d("Maps","onConnected");

//        if(UpdateFlag.getChooseLoc() == 1) {
//            Toast.makeText(this,
//                    "Please choose a location for event",
//                    Toast.LENGTH_SHORT).show();
//        }
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        CurrentLocation.setLatLng(latLng);


        Log.d("Maps", "current location set");
        Log.d("Maps","current location set");
        Log.d("Maps","current location set");

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);

    }




    @Override
    public void onMapClick(LatLng latLng) {

//        if(UpdateFlag.getChooseLoc() == 1){
//
//
//            NewEventLocation.setLatitude(latLng.latitude);
//            NewEventLocation.setLongitude(latLng.longitude);
//
//            SQLdb entry = new SQLdb(MapsActivity.this);
//            entry.open();
//            entry.createEntry(NewEventLocation.getName(), NewEventLocation.getDescription(), NewEventLocation.getLatitude(), NewEventLocation.getLongitude(), NewEventLocation.getLocation());
//            entry.close();
//
//            UpdateFlag.setChooseLoc(0);
//
//            mMap.addMarker(new MarkerOptions().position(latLng).title(NewEventLocation.getName()));
//
//        }
//
//        Log.d("Maps","onMapClick");
//        Log.d("Maps","onMapClick");
//        Log.d("Maps","onMapClick");


    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        NewEventLocation.setLatitude(latLng.latitude);
        NewEventLocation.setLongitude(latLng.longitude);

        Log.d("Maps","onMapLongClick");
        Log.d("Maps","onMapLongClick");
        Log.d("Maps","onMapLongClick");


        startActivity(new Intent(this, NewEventActivity.class));

    }
}
