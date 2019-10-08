package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import etusapp.Config.Conf;
import etusapp.Config.MySingleton;


public class FavMain extends Activity {

    ArrayList<String> listeStations = new ArrayList<>();
    ArrayList<String> listeIdligne = new ArrayList<>();
    ArrayList<String> listeorigine = new ArrayList<>();
    ArrayList<String> listedestination = new ArrayList<>();

    String email;
    ListView StationListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewfav);
        StationListe = findViewById(R.id.listview_fav);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        getStation();

        StationListe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent( FavMain.this,Horairefav.class);
                listeStations.get(position);
                i.putExtra("favligneid",listeIdligne.get(position));
                i.putExtra("origine",listeorigine.get(position));
                i.putExtra("destination",listedestination.get(position));
                startActivity(i);
            }
        });
    }




    private void getStation(){
        listeStations.clear();
        StringRequest stringRequest= new StringRequest(StringRequest.Method.POST, Conf.getStation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String Station = jsonObject.getString("station");
                        String idligne = Integer.toString(jsonObject.getInt("idligne"));
                        String depart = jsonObject.getString("depart");
                        String arrivee = jsonObject.getString("arrivee");

                        listeStations.add(Station);
                        listeIdligne.add(idligne);
                        listeorigine.add(depart);
                        listedestination.add(arrivee);
                        OnFinish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("email",email);
                return map;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void OnFinish(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listeStations);

        StationListe.setAdapter(adapter);
    }
}
