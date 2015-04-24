package io.ichi_go.ichigo;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by ahernand on 4/17/15.
 */
public class NewEventLocation {


    private static String name = "Albuquerque stuffs";
    private static String description = "a description of abq";
    private static String data;
    private static LatLng latLng;

    private static DecimalFormat formatter = new DecimalFormat("000.0000");

    private static String latitude = formatter.format(35.1107);
    private static String longitude = formatter.format(-106.6100);

    private static String location = "";

    public static LatLng getLatLng() {
        return latLng;
    }
    public static void setLatLng(LatLng latLng) {
        NewEventLocation.latLng = latLng;
        NewEventLocation.setLatitude(latLng.latitude);
        NewEventLocation.setLongitude(latLng.longitude);
    }

    public static String getData() {return data;}
    public static void setData(String data) {
        NewEventLocation.data = data;}

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(Double latitude) {
        NewEventLocation.latitude = formatter.format(latitude);
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(Double longitude) {
        NewEventLocation.longitude = formatter.format(longitude);
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        NewEventLocation.location = location;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        NewEventLocation.name = name;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        NewEventLocation.description = description;
    }
}
