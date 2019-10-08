package etusapp.dell.com.etusa;

/**
 * Created by DELL on 16/06/2018.
 */


public class Favoris {
    int idfav;
    String email;
    String stationfav;
    int idlignefav;


    public Favoris(int idfav, String email, String stationfav, int idlignefav) {
        this.idfav = idfav;
        this.email = email;
        this.stationfav = stationfav;
        this.idlignefav = idlignefav;
    }




    public int getIdfav() {
        return idfav;
    }

    public void setIdfav(int idfav) {
        this.idfav = idfav;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStationfav() {
        return stationfav;
    }

    public void setStationfav(String stationfav) {
        this.stationfav = stationfav;
    }

    public int getIdlignefav() {
        return idlignefav;
    }

    public void setIdlignefav(int idlignefav) {
        this.idlignefav = idlignefav;
    }



}
