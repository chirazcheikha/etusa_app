package etusapp.dell.com.etusa;

/**
 * Created by DELL on 08/06/2018.
 */

public class Direction {
    int idligne;
    int numligne;
    String origine;
    String direction ;

    public Direction(int idligne, int numligne, String origine, String direction){
      this.numligne=numligne;
      this.direction=direction;
      this.idligne=idligne;
      this.origine=origine;

    }
    public int getNumLigne() {
        return numligne;
    }

    public void setNumLigne(int numligne) {
        this.numligne = numligne;
    }

    public String getDepart() {
        return origine;
    }

    public void setDepart(String origine) {
        this.origine = origine;
    }

    public int getIdLigne() {
        return idligne;
    }

    public void setIdLigne(int idligne) {
        this.idligne = idligne;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }





}
