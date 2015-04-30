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
import android.widget.Button;
import android.widget.TextView;

import io.ichi_go.ichigo.data.controller.EventManager;
import io.ichi_go.ichigo.data.model.Event;


public class DisplayEventActivity extends ActionBarActivity {
    private Event currentEvent;
    private static final String TAG = "ViewEventActivity";

    /**
     * This method creates the activity every time
     * @param savedInstanceState Used if instance state was saved
     */
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

        if (currentEvent.getOwner().equals(EventManager.getInstance().getUsername())){
            (findViewById(R.id.delete_event_button)).setVisibility(View.VISIBLE);
            (findViewById(R.id.edit_event_button)).setVisibility(View.VISIBLE);
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
     * Ran when the user clicks on the 'Edit event' button. Brings up edit event activity
     * @param v the view that called this function
     */
    public void editEvent(View v) {
        if (v == findViewById(R.id.edit_event_button)) {
            Log.d(TAG, "Participant wants to edit the event");

        }
    }

    /**
     * Ran when the user clicks on the 'Delete event' button. Should delete the event
     * @param v the view that called this function
     */
    public void deleteEvent(View v) {
        if (v == findViewById(R.id.delete_event_button)) {
            EventManager eventManager = EventManager.getInstance();
            eventManager.deleteEvent(currentEvent);
            Log.d(TAG, "Participant wants to delete the event");

        }
    }

}
