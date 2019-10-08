package etusapp.dell.com.etusa;

/**
 * Created by DELL on 07/05/2018.
 */

public class Ligne {
    private int id;
    private int numLigne;
    private String origine;
    private String destination;



    public Ligne(int id, int numLigne, String origine, String destination) {
        this.id=id ;
        this.numLigne = numLigne;
        this.origine = origine;
        this.destination = destination;

    }

    public int getId() {
        return id ;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNumLigne(int numLigne){
        this.numLigne=numLigne;
    }
    public int getNumLigne() {
        return numLigne;
    }

    public String getOrigine(){
        return origine;
    }
    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
