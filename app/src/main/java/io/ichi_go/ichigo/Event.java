package io.ichi_go.ichigo;

/**
 * Created by russell on 2/22/15.
 */
public class Event {
    private String name;
    private String description;
    private Double latitude;
    private Double longitude;
    private String location;

    Event(String name, String description, Double latitude, Double longitude, String location){
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public String printData(){
        StringBuilder result = new StringBuilder();
        result.append(this.name);
        return result.toString();
    }

}
