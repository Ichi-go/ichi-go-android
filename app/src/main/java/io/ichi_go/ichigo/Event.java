package io.ichi_go.ichigo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by russell on 2/22/15.
 */
public class Event implements Parcelable {
    private Integer id;
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String location;

    Event(Integer id, String name, String description, Double latitude, Double longitude, String location) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public String printData() {
        return "Id: " + id + "\nEvent: " + name + "\nDescription: " + description
                + "\nLat/Long: " + latitude + " / " + longitude
                + "\nLocation: " + location;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(location);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }

    };

    public Event(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        location = in.readString();
    }

}
