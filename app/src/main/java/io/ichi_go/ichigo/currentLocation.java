package io.ichi_go.ichigo;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;

/**
 * Created by ahernand on 4/17/15.
 */
public class currentLocation {

    private static String data;
    private static LatLng latLng;

    private static DecimalFormat formatter = new DecimalFormat("000.0000");

    private static String latitude = formatter.format(35.1107);
    private static String longitude = formatter.format(106.6100);

    public static LatLng getLatLng() {
        return latLng;
    }
    public static void setLatLng(LatLng latLng) {
        currentLocation.latLng = latLng;
        currentLocation.setLatitude(latLng.latitude);
        currentLocation.setLongitude(latLng.longitude);
    }

    public static String getData() {return data;}
    public static void setData(String data) {
        currentLocation.data = data;}

    public static String getLatitude() {
        return latitude;
    }

    public static void setLatitude(Double latitude) {
        currentLocation.latitude = formatter.format(latitude);
    }

    public static String getLongitude() {
        return longitude;
    }

    public static void setLongitude(Double longitude) {
        currentLocation.longitude = formatter.format(longitude);
    }
}
