package io.ichi_go.ichigo;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import io.ichi_go.ichigo.data.model.Event;


public class DisplayEventActivity extends ActionBarActivity {
    private Event currentEvent;
    private static final String TAG = "ViewEventActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("View an Event");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        currentEvent = (Event) i.getParcelableExtra("currentEvent");
        TextView displayName = (TextView) findViewById(R.id.display_event_name);
        TextView displayDescription = (TextView) findViewById(R.id.display_description);

        displayName.setText(currentEvent.getName());
        displayDescription.setText(currentEvent.getDescription());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_event, menu);
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

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void editEvent(View v) {
        if (v == findViewById(R.id.edit_event_button)) {
            Log.d(TAG, "Participant wants to edit the event");

        }
    }

    public void deleteEvent(View v) {
        if (v == findViewById(R.id.delete_event_button)) {
            Log.d(TAG, "Participant wants to delete the event");

        }
    }

}
