package etusapp.Config;

/**
 * Created by DELL on 01/07/2018.
 */

public class Favorit {

    String Station,idLigne,depart,arrive;

    public Favorit(String station, String idLigne, String depart, String arrive) {
        Station = station;
        this.idLigne = idLigne;
        this.depart = depart;
        this.arrive = arrive;
    }

    public String getStation() {
        return Station;
    }

    public String getIdLigne() {
        return idLigne;
    }

    public String getDepart() {
        return depart;
    }

    public String getArrive() {
        return arrive;
    }
}