package io.ichi_go.ichigo;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.maps.model.LatLng;

import static io.ichi_go.ichigo.R.id.event_description;
import static io.ichi_go.ichigo.R.id.event_name;


public class EditEventActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void createEvent(View v) {
        if(v.getId() == R.id.edit_event_button) {

            YoYo.with(Techniques.RotateOut)
                    .duration(700)
                    .playOn(findViewById(R.id.edit_event_button));


            final ActionBarActivity itself = this;



            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            EditText etName = (EditText) findViewById(event_name);
                            EditText etDes = (EditText) findViewById(event_description);
                            String eName = String.valueOf(etName.getText());
                            String eDes = String.valueOf(etDes.getText());

                       NewEventLocation.setName(String.valueOf(etName.getText()));
                            NewEventLocation.setDescription(String.valueOf(etDes.getText()));


                           UpdateFlag.setChooseLoc(1);

                            LatLng latLng = CurrentLocation.getLatLng();

                            SQLdb entry = new SQLdb(EditEventActivity.this);
                            entry.open();
                            entry.createEntry(eName, eDes, NewEventLocation.getLatitude(), NewEventLocation.getLongitude(), NewEventLocation.getLocation());
                            entry.writeCentral();
                            entry.close();

                            Log.d("NewEvent","A new event was created");
                            Log.d("NewEvent","A new event was created");
                            Log.d("NewEvent","A new event was created");


                            NavUtils.navigateUpFromSameTask(itself);

                        }
                    });
                }

            }).start();




        }
    }


}
