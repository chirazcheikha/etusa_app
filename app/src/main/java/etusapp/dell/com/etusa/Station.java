package etusapp.dell.com.etusa;

/**
 * Created by DELL on 06/06/2018.
 */

public class Station {
    private int idStation;
    private String nomstation;
    private int idLigne;

    public Station(int idStation,String nomstation,int idLigne){
        this.idStation=idStation;
        this.nomstation=nomstation;
        this.idLigne=idLigne;
    }
    public int getIdStation() {
        return idStation;
    }

    public void setIdStation(int idStation) {
        this.idStation = idStation;
    }


    public String getNomstation() {
        return nomstation;
    }

    public void setNomstation(String nomstation) {
        this.nomstation = nomstation;
    }


    public int getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }

}
