package io.ichi_go.ichigo;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by ahernand on 4/17/15.
 */
public class CurrentLocation {


    private static String name = "Albuquerque stuffs";
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
        CurrentLocation.latLng = latLng;
        CurrentLocation.setLatitude(latLng.latitude);
        CurrentLocation.setLongitude(latLng.longitude);
    }

    public static String getData() {return data;}
    public static void setData(String data) {
        CurrentLocation.data = data;}

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(Double latitude) {
        CurrentLocation.latitude = formatter.format(latitude);
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(Double longitude) {
        CurrentLocation.longitude = formatter.format(longitude);
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        CurrentLocation.location = location;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        CurrentLocation.name = name;
    }
}
