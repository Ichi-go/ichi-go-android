package io.ichi_go.ichigo;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by russell on 2/22/15.
 */
public class Event implements Comparable<Event> {
    private String name;
    private String description;
    private String latitude;
    private String longitude;

    private LatLng latLng;
    private String id;


    //private String location;

    Event(String id, String name, String description, String latitude, String longitude){
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
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

    @Override
    public int compareTo(Event another) {

    return this.getName().compareTo(another.getName());

    }
}
