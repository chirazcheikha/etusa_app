package etusapp.dell.com.etusa;

/**
 * Created by DELL on 07/05/2018.
 */


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "etusa2.sqlite";
    public static final String DBLOCATION = "/data/data/etusapp.dell.com.etusa/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }




    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public List<Ligne> getListLigne() {
        Ligne ligne = null;
        List<Ligne> lignelist = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM ligne", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ligne = new Ligne(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3));
            lignelist.add(ligne);

            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return lignelist;
    }




    public List<etusapp.dell.com.etusa.Arret> getListArretByLigne(int ligneId) {

        etusapp.dell.com.etusa.Arret arret = null;
        List<etusapp.dell.com.etusa.Arret> arretlist = new ArrayList<>();

        mDatabase = this.getWritableDatabase();
        Cursor cursor =
                mDatabase.rawQuery("SELECT NomArret FROM affectation f , arret a where a.idArret = f.idArret and f.idLigne= "+ ligneId  ,null);

        while (cursor.moveToNext()) {
            arret = new etusapp.dell.com.etusa.Arret();
            arret.setNomArret(cursor.getString(0));

            arretlist.add(arret);
        }

        cursor.close();
        closeDatabase();
        return arretlist;
    }

    public List<Station> getListStation() {
        Station station = null;
        List<Station> stationlist = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM station GROUP BY nomstation ORDER BY nomstation DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            station = new Station(cursor.getInt(0),cursor.getString(1),cursor.getInt(2));
            stationlist.add(station);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return stationlist;
    }


    public List<Direction> getListdirectionbystation(int ids) {

        Direction direction = null;
        List<Direction> directionlist = new ArrayList<>();

        mDatabase = this.getWritableDatabase();
        Cursor cursor =
                mDatabase.rawQuery("SELECT * FROM ligne l , station s where l.depart=s.nomstation and idstation= " + ids, null);

        while (cursor.moveToNext()) {
            direction = new Direction(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getString(3));
            directionlist.add(direction);
        }

        cursor.close();
        closeDatabase();
        return directionlist;


    }


   public List<Horaire> getListHorairebydirection(int directionid){
       List<Horaire> horairelist = new ArrayList<>();
            Horaire horaire=null;
       mDatabase = this.getWritableDatabase();
       Cursor cursor =
               mDatabase.rawQuery("SELECT * FROM horaires where idLigne= "+ directionid, null);

       while (cursor.moveToNext()) {
         horaire = new Horaire(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getInt(3));
           horairelist.add(horaire);
       }

       cursor.close();
       closeDatabase();
       return horairelist;
   }

    public List<Abonnement> getListabo() {
        Abonnement abo = null;
        List<Abonnement> abolist = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM abonnement GROUP BY titreabonnement order by idabonnement asc ", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            abo = new Abonnement(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4));
            abolist.add(abo);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return abolist;
    }
    public List<Contenu> getListabobyid(int a) {
        Contenu abo = null;
        List<Contenu> abolist = new ArrayList<>();
        openDatabase();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM abonnement where idpartitre="+a, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            abo = new Contenu(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),cursor.getInt(4));
            abolist.add(abo);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return abolist;
    }


}
