package io.ichi_go.ichigo;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by russell on 2/22/15.
 */
public class Event implements Comparable<Event> {
    private String name = "";
    private String description;
    private String latitude;
    private String longitude;
    private String location = "none";

    private LatLng latLng;
    private String id = "";


    //private String location;

    Event(String id, String name, String description, String latitude, String longitude, String location){
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
      //  this.location = location;
    }

    public String printData(){
        StringBuilder result = new StringBuilder();
        result.append(this.name);
        return result.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = String.format("%10s",name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = String.format("%10s",description);
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int compareTo(Event another) {

    return this.getName().compareTo(another.getName());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!id.equals(event.id)) return false;
        if (!name.equals(event.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }


    public String writeJSON() {
        JSONObject jso = new JSONObject();
        try {

            jso.put("longitude", this.getLongitude());
            jso.put("latitude", this.getLatitude());
            jso.put("name", this.getName());
            jso.put("description", this.getDescription());
            jso.put("location", this.getLocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jso.toString();
    }

    public JSONObject getJSON() {
        JSONObject jso = new JSONObject();
        try {

            jso.put("longitude", Double.valueOf(this.getLongitude()));
            jso.put("latitude", Double.valueOf(this.getLatitude()));
            jso.put("name", this.getName());
            jso.put("description", this.getDescription());
            jso.put("location", this.getLocation());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jso;
    }

}
