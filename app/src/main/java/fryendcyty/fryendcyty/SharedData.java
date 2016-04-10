package fryendcyty.fryendcyty;

import java.util.Date;

/**
 * Created by Alejandro on 02/04/2016.
 */
public class SharedData {

    private String title;
    private String type;
    private String dataPath;
    private double latitude;
    private double longitude;
    private Date date;

    public SharedData(String title, String type, String dataPath, double latitude, double longitude, Date date)
    {
        this.title = title;
        this.type = type;
        this. dataPath = dataPath;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public SharedData(){};

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
