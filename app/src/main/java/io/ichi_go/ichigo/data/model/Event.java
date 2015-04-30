package io.ichi_go.ichigo.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Event class that stores all information regarding an event
 */
public class Event implements Comparable<Event>, Parcelable {
    private String name = "";
    private String description;
    private String latitude;
    private String longitude;
    private String location = "none";
    private String owner = "";

    private LatLng latLng;
    private String id = "";

    /**
     * Constructor for an event
     * @param id the id of the event
     * @param name the name of the event
     * @param description the description of the event
     * @param latitude the latitude of the event
     * @param longitude the longitude of the event
     * @param location the location of the event
     * @param owner the owner of the event
     */
    public Event(String id, String name, String description, String latitude, String longitude, String location, String owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.owner = owner;
    }

    /**
     * Gets the owner of the event
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the event
     * @param owner the new owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Gets the name of the event
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the event
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the event
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the LatLng of the event
     * @return the LatLng
     */
    public LatLng getLatLng() {
        return latLng;
    }

    /**
     * Sets the LatLng of the event
     * @param latLng the new LatLng
     */
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    /**
     * Gets the id of the event
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the event
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the latitude of the event
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the event
     * @param latitude the new latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the event
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the event
     * @param longitude the new longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the location of the event
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event
     * @param location the new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Compares this event to another
     * @param another the event to compare to
     * @return the names compared to each other
     */
    @Override
    public int compareTo(Event another) {

        return this.getName().compareTo(another.getName());

    }

    /**
     * Checks if two events are the same event
     * @param o the other event to compare
     * @return if the two events are the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (!id.equals(event.id)) return false;
        if (!name.equals(event.name)) return false;

        return true;
    }

    /**
     * Generates the hashcode for this event
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + id.hashCode();
        return result;
    }

    /**
     * Converts this event's information into a JSON object
     * @return the JSON object
     */
    public JSONObject getJSON() {
        JSONObject jso = new JSONObject();
        try {

            jso.put("longitude", Double.valueOf(this.getLongitude()));
            jso.put("latitude", Double.valueOf(this.getLatitude()));
            jso.put("name", this.getName());
            jso.put("description", this.getDescription());
            jso.put("location", this.getLocation());
            jso.put("owner", this.getOwner());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jso;
    }

    /**
     * Describes the content of this event
     * @return 0?
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Turns this event into a parcel that can be passed between activities
     * @param dest the parcel to be converted into
     * @param flags flags for the parcel
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(location);
        dest.writeString(owner);
    }

    /**
     * Creates an event from a parcel
     */
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }

    };

    /**
     * Constructs an event based on a parcel
     * @param in the parcel that is an event
     */
    public Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        location = in.readString();
        owner = in.readString();
    }
}
