package etusapp.dell.com.etusa;

/**
 * Created by DELL on 09/06/2018.
 */

public class Abonnement {
    int idbo;
    String titreabo;
    String age;
    String tarif;
    int idbya;



    public Abonnement(int idbo, String titreabo, String age, String tarif, int idbya) {
        this.idbo = idbo;
        this.titreabo = titreabo;
        this.age = age;
        this.tarif = tarif;
        this.idbya=idbya;
    }
    public int getIdbya() {
        return idbya;
    }

    public void setIdbya(int idbya) {
        this.idbya = idbya;
    }
    public int getIdbo() {
        return idbo;
    }

    public void setIdbo(int idbo) {
        this.idbo = idbo;
    }

    public String getTitreabo() {
        return titreabo;
    }

    public void setTitreabo(String titreabo) {
        this.titreabo = titreabo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }



}
