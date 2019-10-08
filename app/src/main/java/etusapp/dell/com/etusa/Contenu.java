package etusapp.dell.com.etusa;

/**
 * Created by DELL on 10/06/2018.
 */

public class Contenu {
    int idc;
    String titrec;
    String agec;
    String tarifc;
    int idbyc;


    public Contenu(int idc, String titrec, String agec, String tarifc,int idbyc) {
        this.idc = idc;
        this.titrec = titrec;
        this.agec = agec;
        this.tarifc = tarifc;
        this.idbyc=idbyc;
    }


    public int getIdbyc() {
        return idbyc;
    }

    public void setIdbyc(int idbyc) {
        this.idbyc = idbyc;
    }


    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public String getTitrec() {
        return titrec;
    }

    public void setTitrec(String titrec) {
        this.titrec = titrec;
    }

    public String getAgec() {
        return agec;
    }

    public void setAgec(String agec) {
        this.agec = agec;
    }

    public String getTarifc() {
        return tarifc;
    }

    public void setTarifc(String tarifc) {
        this.tarifc = tarifc;
    }
}
