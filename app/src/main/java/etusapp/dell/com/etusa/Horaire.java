package etusapp.dell.com.etusa;

/**
 * Created by DELL on 06/06/2018.
 */

public class Horaire {

    private int idhoraire;
   private int idstation;
   private String horaire;
    private int idligne;

    public Horaire(int idhoraire, int idstation, String horaire, int idligne) {
        this.idhoraire = idhoraire;
        this.idstation = idstation;
        this.horaire = horaire;
        this.idligne = idligne;
    }

    public int getidligne() {
        return idligne;
    }

    public void setidligne(int idligne) {
        this.idligne = idligne;
    }

    public int getIdstation() {
        return idstation;
    }

    public void setIdstation(int idstation) {
        this.idstation = idstation;
    }

    public int getIdhoraire() {

        return idhoraire;
    }

    public void setIdhoraire(int idhoraire) {
        this.idhoraire = idhoraire;
    }

    public String getHoraire() {

        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }


}
