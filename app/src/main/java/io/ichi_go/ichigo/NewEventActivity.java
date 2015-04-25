package io.ichi_go.ichigo;

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

import static android.os.StrictMode.setThreadPolicy;
import static io.ichi_go.ichigo.R.id.event_description;
import static io.ichi_go.ichigo.R.id.event_name;


public class NewEventActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
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
        if(v.getId() == R.id.create_event_button) {

            YoYo.with(Techniques.RotateOut)
                    .duration(700)
                    .playOn(findViewById(R.id.create_event_button));


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

                            Event eventToSend = new Event("0", eName, eDes, NewEventLocation.getLatitude(), NewEventLocation.getLongitude(), NewEventLocation.getLocation());
                            UpdateFlag.setChooseLoc(1);

                            LatLng latLng = CurrentLocation.getLatLng();

                            SQLdb entry = new SQLdb(NewEventActivity.this);
                            entry.open();
                            entry.createEntry(eName, eDes, NewEventLocation.getLatitude(),NewEventLocation.getLongitude(), NewEventLocation.getLocation());
//                            entry.writeCentral();
                            entry.close();

                            Log.d("NewEvent", "A new event was created");
                            Log.d("NewEvent","A new event was created");
                            Log.d("NewEvent","A new event was created");

                            StrictMode.ThreadPolicy policy = new
                                    StrictMode.ThreadPolicy.Builder().permitAll().build();
                            setThreadPolicy(policy);

                            // Produce the output
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            Writer writer = null;
                            try {
                                writer = new OutputStreamWriter(out, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            try {
                                writer.write(eventToSend.getJSON().toString());
                                writer.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                            // Create the request
                            HttpPost request = new HttpPost("http://10.0.2.2:8000/addEvent");
                            request.setHeader("Content-Type", "application/json");
                            request.setEntity(new ByteArrayEntity(out.toByteArray()));

                            // Send the request
                            DefaultHttpClient client = new DefaultHttpClient();
                            try {
                                HttpResponse response = client.execute(request);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }




                            NavUtils.navigateUpFromSameTask(itself);

                        }
                    });
                }

            }).start();




        }
    }


}
