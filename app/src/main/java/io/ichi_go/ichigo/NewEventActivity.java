package io.ichi_go.ichigo;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import io.ichi_go.ichigo.data.controller.EventManager;
import io.ichi_go.ichigo.data.model.Event;

import static android.os.StrictMode.setThreadPolicy;

import static io.ichi_go.ichigo.R.id.event_description;
import static io.ichi_go.ichigo.R.id.event_location;
import static io.ichi_go.ichigo.R.id.event_name;


public class NewEventActivity extends ActionBarActivity {

    private Event newEvent;

    /**
     * This method creates the activity every time
     * @param savedInstanceState Used if instance state was saved
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newEvent = new Event("", "", "", "", "", "", "");

        Intent i = getIntent();
        if (i != null) {
            newEvent.setLatitude(i.getStringExtra("latitude"));
            newEvent.setLongitude(i.getStringExtra("longitude"));
        }
    }

    /**
     * Inflates the options menu for the activity
     * @param menu the menu being inflated with a layout
     * @return true if successful
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
        return true;
    }

    /**
     * Ran when an option in the inflated menu is selected
     * @param item The MenuItem that was selected
     * @return true if successful
     */
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

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Ran when the user clicks on the 'Create event' button. Tries to create the event
     * @param v the view that called this function
     */
    public void createEvent(View v) {
        if (v.getId() == R.id.create_event_button) {

            //Don't allow new events to be created without a name
            if (String.valueOf(((EditText) findViewById(R.id.event_name)).getText()).isEmpty()) {
                Toast.makeText(this,
                        "Please enter a name for the event",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            //Button animation when clicked
            YoYo.with(Techniques.RotateOut)
                    .duration(700)
                    .playOn(findViewById(R.id.create_event_button));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    NewEventActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            EditText etName = (EditText) findViewById(event_name);
                            EditText etDes = (EditText) findViewById(event_description);
                            EditText etLoc = (EditText) findViewById(event_location);
                            newEvent.setName(String.valueOf(etName.getText()));
                            newEvent.setDescription(String.valueOf(etDes.getText()));
                            newEvent.setLocation(String.valueOf(etLoc.getText()));

                            //Give default string values if they are empty
                            if (newEvent.getDescription().isEmpty()) {
                                System.out.println("Setting Description");
                                newEvent.setDescription("No description.");
                            }
                            if (newEvent.getLocation().isEmpty()) {
                                newEvent.setLocation("No location specified.");
                            }

                            EventManager eventManager = EventManager.getInstance();
                            newEvent.setOwner(eventManager.getUsername());
                            eventManager.addEvent(newEvent);

                            Intent i = NavUtils.getParentActivityIntent(NewEventActivity.this);
                            i.putExtra("latitude", Double.valueOf(newEvent.getLatitude()));
                            i.putExtra("longitude", Double.valueOf(newEvent.getLongitude()));
                            NavUtils.navigateUpTo(NewEventActivity.this, i);
                        }
                    });
                }

            }).start();

        }
    }
}