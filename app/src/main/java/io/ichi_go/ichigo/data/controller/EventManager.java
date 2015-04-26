package io.ichi_go.ichigo.data.controller;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import io.ichi_go.ichigo.data.model.Event;

import static android.os.StrictMode.setThreadPolicy;

/**
 * The event manager is a singleton that handles events and event accessories.
 * Created by randy on 4/25/15.
 * NOTE: Much of the code was adapted from Alex Hernandez's
 */
public class EventManager {
    private static volatile EventManager instance;
    private ArrayList<Event> events;

    private EventManager() {
        this.events = new ArrayList<>();
    }

    /**
     * Returns the EventManager instance
     * @return the instance of EventManager
     */
    public static EventManager getInstance() {
        if (instance == null) {
            synchronized (EventManager.class) {
                if (instance == null) {
                    instance = new EventManager();
                }
            }
        }

        return instance;
    }

    /**
     * Load all the events you may currently see.
     */
    public void loadEvents(Double lat, Double lon) {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);

        String url = "http://10.0.2.2:8000/getEvents";
        DefaultHttpClient client = new DefaultHttpClient();
        HttpPost request = new HttpPost(url);
        StringBuilder builder = new StringBuilder();

        ArrayList<NameValuePair> postParameters;
        postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("lat", lat.toString()));
        postParameters.add(new BasicNameValuePair("lon", lon.toString()));

        try {
            request.setEntity(new UrlEncodedFormEntity(postParameters));
            HttpResponse response = client.execute(request);
            InputStream content = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.loadEventsFromJSON(builder.toString());
    }

    /**
     * Retrieve all the events currently loaded.
     * @return the events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Add a new event to the list of local events
     * @param event the event to add
     */
    public void addEvent(Event event) {

        //TODO: change policy
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        setThreadPolicy(policy);


        HttpPost request = new HttpPost("http://10.0.2.2:8000/addEvent");
        DefaultHttpClient client = new DefaultHttpClient();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Writer writer = null;

        try {
            writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(event.getJSON().toString());
            writer.flush();

            /*Create and send event*/
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new ByteArrayEntity(out.toByteArray()));
            client.execute(request);
        } catch (UnsupportedEncodingException e) {
            Log.d("NewEvent", "Failed to create new event.");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("NewEvent", "Failed to create new event.");
            e.printStackTrace();
        }

        Log.d("NewEvent", "Successfully created new event.");
        events.add(event);
    }

    public void editEvent(Event event) {
        throw new UnsupportedOperationException();
    }

    /**
     * Internal: Converts a list of JSON objects to a list of events
     * @param json, the list of json objects in string form
     */
    private void loadEventsFromJSON(String json) {
        try {
            JSONArray array = new JSONArray(json);

            for (int i = 0; i < array.length(); i++) {
                JSONObject j = array.getJSONObject(i);
                Event e = new Event("0",
                                    j.getString("name"),
                                    j.getString("description"),
                                    j.getString("latitude"),
                                    j.getString("longitude"),
                                    j.getString("location"));
                this.events.add(e);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
