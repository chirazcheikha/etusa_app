package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etusapp.Config.Conf;
import etusapp.Config.Favorit;
import etusapp.Config.MySingleton;


public class StationDirectionMain extends Activity {

    private ListView lvDirection;
    private ListStationDirectionAdapter adapter;
    private List<Direction> mDirectionList;
    private DatabaseHelper mDBHelper;
    int idStation;
    String s,d;
    boolean estFav;
    ArrayList<Favorit> stationArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_stationdirection);
        lvDirection = (ListView)findViewById(R.id.listview_direction);
        mDBHelper = new DatabaseHelper(this);
        idStation = getIntent().getIntExtra("IDstation",0);



        lvDirection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent( StationDirectionMain.this,HorairesMain.class);
                Direction direction = (Direction)mDirectionList.get(position);
             //   d = direction.getDirection();
               // s = direction.getDepart();

                i.putExtra("directionid",direction.getIdLigne());
                i.putExtra("nomdirection",direction.getDirection());
                i.putExtra("nomorigine",direction.getDepart());
                i.putExtra("estFav",estFav);
                startActivity(i);
            }
        });



        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Get  list in db when db exists
        mDirectionList = mDBHelper.getListdirectionbystation(idStation);
        //Init adapter
        adapter = new ListStationDirectionAdapter(getApplicationContext(), mDirectionList);
        //Set adapter for listview
        lvDirection.setAdapter(adapter);



    }

    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("StationDirectionMain","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
