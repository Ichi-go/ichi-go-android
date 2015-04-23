package io.ichi_go.ichigo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.os.StrictMode.setThreadPolicy;


public class MyEventsActivity2 extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_events2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tv = (TextView) findViewById(R.id.tvSQLinfo);

//        tv.setTypeface(Typeface.MONOSPACE);
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
//
//        SQLdb info = new SQLdb(this);
//        info.open();
//        String data = info.getData();
//
//    System.out.println(data);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);


//        String input = readCentral();
//        try {
//
//            //JsonReader jsonReader = Json.createReader(input);
//            JSONArray array = new JSONArray(input);
//
//            Log.i(JsonParseActivity.class.getName(), array.toString());
//
//            String data = array.getJSONObject(3).toString();
//            tv.setText(data);
//
//            System.out.println(array.getJSONObject(3).getString("latitude"));
//            System.out.println("latitude");
//            System.out.println("latitude");
//            System.out.println("latitude");
//
//
//        } catch (JSONException e1) {
//            e1.printStackTrace();
//        }


    }

    public String readCentral() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://10.0.2.2:8000/getEvents");
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(MyEventsActivity2.class.toString(), "Failed to download file");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
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
        if(v.getId() == R.id.create_event_button2) {

            YoYo.with(Techniques.Wave)
                    .duration(700)
                    .playOn(findViewById(R.id.create_event_button2));

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

                            YoYo.with(Techniques.Wobble)
                                    .duration(700)
                                    .playOn(findViewById(R.id.create_event_button2));


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


                                            YoYo.with(Techniques.Swing)
                                                    .duration(700)
                                                    .playOn(findViewById(R.id.create_event_button2));


                                        }
                                    });
                                }

                            }).start();


                        }
                    });
                }

            }).start();





            //startActivity(new Intent(getActivity(), NewEventActivity.class));
        }
    }


}
