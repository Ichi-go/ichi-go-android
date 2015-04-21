package io.ichi_go.ichigo;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by ahernand on 4/17/15.
 */
public class displayEvent {

    private static String name = "dummyname";
    private static String desciption = "dummydescription";
    private static String location;
    private static LatLng latLng;

    private static DecimalFormat formatter = new DecimalFormat("000.0000");

    private static String latitude = formatter.format(35.1107);
    private static String longitude = formatter.format(106.6100);

    public static LatLng getLatLng() {
        return latLng;
    }
    public static void setLatLng(LatLng latLng) {
        displayEvent.latLng = latLng;
        displayEvent.setLatitude(latLng.latitude);
        displayEvent.setLongitude(latLng.longitude);
    }

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(Double latitude) {
        displayEvent.latitude = formatter.format(latitude);
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(Double longitude) {
        displayEvent.longitude = formatter.format(longitude);
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        displayEvent.name = name;
    }

    public static String getDesciption() {
        return desciption;
    }

    public static void setDesciption(String desciption) {
        displayEvent.desciption = desciption;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        displayEvent.location = location;
    }
}
