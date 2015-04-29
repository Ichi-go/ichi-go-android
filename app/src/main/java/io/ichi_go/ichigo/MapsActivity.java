package io.ichi_go.ichigo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
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
    private final LatLng DEFAULT_LOCATION = LOCATION_NMT;
    private final float DEFAULT_ZOOM = 17;
    private final Integer MARKER_NAME_LENGTH = 16;

    //Map Variables
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private HashMap<String, Event> markerToEventHashmap = new HashMap<>();
    private HashMap<Event, Marker> eventToMarkerHashmap = new HashMap<>();
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    //Layout Variables
    private android.support.v7.widget.Toolbar toolbar;
    ChannelDrawerFragment channelDrawerFragment;
    NavigationDrawerFragment drawerFragment;

    //Event Management Variables
    private ArrayList<Event> listOfEvents = new ArrayList<>();
    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventManager = EventManager.getInstance();
        //Make this check for current location instead of static value
        eventManager.loadEvents(LOCATION_NMT.latitude, LOCATION_NMT.longitude);
        listOfEvents = eventManager.getEvents();

        setContentView(R.layout.activity_maps2);
        setUpMapIfNeeded();

        Double lat = getIntent().getDoubleExtra("latitude", 0);
        if (lat != 0) {
            Double lng = getIntent().getDoubleExtra("longitude", 0);
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), DEFAULT_ZOOM);
            if (mMap != null) {
                mMap.moveCamera(update);
            } else {
                Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
            }
        }

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
                .setInterval(60 * 1000)        // 10 seconds, in milliseconds
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
        switch (id) {
            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                return true;

            case R.id.action_channel_drawer:
                ((DrawerLayout) findViewById(R.id.drawer_layout)).openDrawer(Gravity.RIGHT);
                break;

            case R.id.action_refresh:
                if (eventManager == null) {
                    eventManager = EventManager.getInstance();
                }
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                if (location == null) {
                    eventManager.loadEvents(DEFAULT_LOCATION.latitude, DEFAULT_LOCATION.longitude);
                } else {
                    eventManager.loadEvents(location.getLatitude(), location.getLongitude());
                }
                listOfEvents = eventManager.getEvents();
                setUpMapIfNeeded();
                break;

            case R.id.action_my_location:
                goToMyLocation();
                break;

            case R.id.action_sign_in:
                if (eventManager.getUsername().equals("")){
                    displaySignInDialog(item);

                } else {
                    Toast.makeText(this, "Logging out", Toast.LENGTH_LONG).show();
                    eventManager.setUsername("");
                    item.setTitle("Sign In");
                }
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    private void displaySignInDialog(final MenuItem item) {
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(this);
        final EditText txtInput = new EditText(this);

        dialogBuilder.setTitle("Sign In");
        dialogBuilder.setMessage("Enter your username");
        dialogBuilder.setView(txtInput);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eventManager.setUsername(txtInput.getText().toString());
                Toast.makeText(MapsActivity.this,"Logged in as: " + eventManager.getUsername(),Toast.LENGTH_SHORT).show();
                item.setTitle("Log out");
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MapsActivity.this,"Cancelled",Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
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
                        Event currentEvent = markerToEventHashmap.get(marker.getId());
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
            markerToEventHashmap.put(marker.getId(), e);
            eventToMarkerHashmap.put(e, marker);
        }


        //Go to default location
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM);
        mMap.moveCamera(update);
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
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        LatLng latLng = new LatLng(lat, lng);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public void goToLocation(Event event) {
        LatLng latLng = new LatLng(Double.valueOf(event.getLatitude()), Double.valueOf(event.getLongitude()));
        CameraUpdate update = CameraUpdateFactory.newLatLng(latLng);
        mMap.animateCamera(update);
        eventToMarkerHashmap.get(event).showInfoWindow();
    }

    public void goToMyLocation(){
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            Toast.makeText(this, "Location currently unavailable", Toast.LENGTH_SHORT).show();
        } else {
            CameraUpdate update = CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude()));
            mMap.animateCamera(update);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (eventManager == null) {
            eventManager = EventManager.getInstance();
        }
        eventManager.loadEvents(location.getLatitude(), location.getLongitude());
        handleNewLocation(location);
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent i = new Intent(this, NewEventActivity.class);
        i.putExtra("latitude", String.valueOf(latLng.latitude));
        i.putExtra("longitude", String.valueOf(latLng.longitude));

        startActivity(i);
    }
}
