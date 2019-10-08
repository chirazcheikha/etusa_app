package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
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


import etusapp.Config.Favorit;
import etusapp.Config.MySingleton;

import etusapp.Config.Conf;

public class HorairesMain extends AppCompatActivity {

    private ListView lvHoraire;

    private ListHorairesAdapter adapter;
    private List<Horaire> mHoraireList;
    private DatabaseHelper mDBHelper;
    int direction;
    String d;
    String s;
    String TempEmail;
    String TempStation;
    String Tempo;
    String Tempd;
    int Tempidligne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaires);
        lvHoraire = (ListView)findViewById(R.id.listview_horaire);
        mDBHelper = new DatabaseHelper(this);

        direction= getIntent().getIntExtra("directionid",0);
        d=getIntent().getStringExtra("nomdirection");
        s=getIntent().getStringExtra("nomorigine");

        TextView t1=(TextView)findViewById(R.id.textView13);
        TextView t2=(TextView)findViewById(R.id.Station);

        t1.setText(d);
        t2.setText(s);

        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if (false == database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if (copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        mHoraireList = mDBHelper.getListHorairebydirection(direction);
        adapter = new ListHorairesAdapter(getApplicationContext(), mHoraireList);
        lvHoraire.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {

                inflater.inflate(R.menu.addfav, menu);

            }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_addfav:

                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWifi.isConnected()) {
                    GetData();
                    insert();
                    item.setIcon(R.drawable.star);
                } else {
                    Toast.makeText(getApplicationContext(), "Verifiez votre connexion à internet", Toast.LENGTH_LONG).show();
                }

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }


    public void GetData() {

        TempEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        TempStation = s + " direction " + d;
        Tempidligne = direction;
        Tempo = s;
        Tempd = d;
    }


    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("horairesmain", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void insert(){
        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Conf.ServerURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response="Station ajoutée aux favoris";
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",TempEmail);
                params.put("station",TempStation);
                params.put("idligne",Integer.toString(Tempidligne));
                params.put("depart",Tempo);
                params.put("arrivee",Tempd);

                return params;

            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    }



