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
import java.util.HashMap;
import java.util.List;

import io.ichi_go.ichigo.data.controller.EventManager;
import io.ichi_go.ichigo.data.model.Event;

public class MapsActivity extends ActionBarActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        OnMapClickListener,
        OnMapLongClickListener {

    public static final String TAG = MapsActivity.class.getSimpleName();
    private final LatLng LOCATION_SOCORRO = new LatLng(34.0617, -106.8994);
    private final LatLng LOCATION_NMT = new LatLng(34.0668, -106.9056);
    private final Integer MARKER_NAME_LENGTH = 15;

    //Map Variables
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private HashMap<String, Event> markerHashmap;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    //Layout Variables
    private android.support.v7.widget.Toolbar toolbar;
    ChannelDrawerFragment channelDrawerFragment;
    NavigationDrawerFragment drawerFragment;

    //Event Management Variables
    private ArrayList<Event> listOfEvents;
    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        eventManager = EventManager.getInstance();
        //Make this check for current location instead of static value
        eventManager.loadEvents(LOCATION_NMT.latitude, LOCATION_NMT.longitude);
        listOfEvents = eventManager.getEvents();

        markerHashmap = new HashMap<>();
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
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
        Log.d("DB", "Update DB");
        Log.d("DB", "Update DB");
        Log.d("DB", "Update DB");

        Log.d("Maps", "onResume");
        Log.d("Maps", "onResume");
        Log.d("Maps", "onResume");

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

                        infoName.setText(marker.getTitle());

                        return view;
                    }
                });

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Event currentEvent = markerHashmap.get(marker.getId());
                        if (currentEvent != null) {
                            Intent i = new Intent(MapsActivity.this, DisplayEventActivity.class);
                            i.putExtra("currentEvent", currentEvent);
                            startActivity(i);
                        }
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
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        Double lat;
        Double lng;
        String name;
        for (Event e : listOfEvents) {
            lat = Double.valueOf(e.getLatitude());
            lng = Double.valueOf(e.getLongitude());
            name = e.getName();
            if (name.length() > MARKER_NAME_LENGTH) {
                name = name.substring(0, MARKER_NAME_LENGTH);
            }

            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(lat, lng)).title(name);
            Marker marker = mMap.addMarker(markerOptions);
            markerHashmap.put(marker.getId(), e);
        }
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

        Log.i(TAG, "Location services connected.");
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
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
        Double currentLatitude = location.getLatitude();
        Double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        //
        // Do Something with this class
        //
        CurrentLocation.setLatLng(latLng);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent i = new Intent(this, NewEventActivity.class);
        i.putExtra("latitude", latLng.latitude);
        i.putExtra("longitude", latLng.longitude);

        startActivity(i);
    }
}
