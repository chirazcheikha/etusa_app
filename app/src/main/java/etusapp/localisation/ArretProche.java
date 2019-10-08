package etusapp.localisation;

/**
 * Created by DELL on 18/06/2018.
 */

public class ArretProche {

    float longi;
    float lat;
    String arret;

    public ArretProche(float longi, float lat, String arret) {
        this.longi = longi;
        this.lat = lat;
        this.arret = arret;
    }

    public float getLongi() {
        return longi;
    }

    public void setLongi(float longi) {
        this.longi = longi;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getArret() {
        return arret;
    }

    public void setArret(String arret) {
        this.arret = arret;
    }




}
