package etusapp.localisation;

import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.SearchView;
import java.util.ArrayList;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import etusapp.Config.Conf;
import etusapp.Config.MySingleton;
import etusapp.dell.com.etusa.Acceuil;
import etusapp.dell.com.etusa.ArretProcheAdapter;
import etusapp.dell.com.etusa.R;



public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback,SearchView.OnQueryTextListener {


    ArrayList<ArretProche> listeArretProche = new ArrayList<>();
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    LatLng latLng;
    LatLng latlng2;
    ArrayAdapter<String> mAdapter;
    AutoCompleteTextView actv;

    ArretProcheAdapter adapter;
    ArrayList<String> arretNameList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        ImageView i1= (ImageView)findViewById(R.id.backInMap);
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), Acceuil.class);
                startActivity(i);
            }
        });


        getAllArrets();


        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);




        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);


    }

    @Override
    public void onPause() {
        super.onPause();

        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }



    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000);
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {

                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity2", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                int indx = IDarretplusproche(location);
                ArretProche arretProche = listeArretProche.get(indx);
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
                LatLng latLng1 = new LatLng(arretProche.getLat(),arretProche.getLongi());
                mCurrLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Votre position"));
                mCurrLocationMarker.showInfoWindow();

                BitmapDrawable icon=(BitmapDrawable)getResources().getDrawable(R.drawable.buspointer);
                Bitmap b=icon.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 30, 30, false);

                mCurrLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng1).title("Arrêt de bus "+
                        arretProche.getArret()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                mCurrLocationMarker.showInfoWindow();

                actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mGoogleMap.clear();
                        mCurrLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Votre position"));
                        mCurrLocationMarker.showInfoWindow();

                        //ajout du marqueur de l'arret choisie
                        String selection = (String)parent.getItemAtPosition(position);
                        int index = getArretChoisi(selection);
                        BitmapDrawable icon=(BitmapDrawable)getResources().getDrawable(R.drawable.buspointer);
                        Bitmap b=icon.getBitmap();
                        Bitmap smallMarker1 = Bitmap.createScaledBitmap(b, 30, 30, false);

                        latlng2= new LatLng(listeArretProche.get(index).getLat(),listeArretProche.get(index).getLongi());
                        mCurrLocationMarker = mGoogleMap.addMarker(new MarkerOptions().position(latlng2).title("Arrêt de bus "+
                                listeArretProche.get(index).getArret()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker1)));
                        mCurrLocationMarker.showInfoWindow();
                    }
                });


                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {


                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("Activez le service de localisation s'il vous plait")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity2.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {


                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }


        }
    }



    private void getAllArrets(){
        listeArretProche.clear();
        StringRequest stringRequest= new StringRequest(StringRequest.Method.POST, Conf.arrets, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String nomArret = jsonObject.getString("NomArret");
                        float lan = Float.parseFloat(jsonObject.getString("Longitude"));
                        float lat = Float.parseFloat(jsonObject.getString("Latitude"));

                        ArretProche arretProche = new ArretProche(lan,lat,nomArret);
                        listeArretProche.add(arretProche);


                    }

                    getAllArretsByNames();
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
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    private void getAllArretsByNames(){
        arretNameList.clear();
        for(int i = 0; i < listeArretProche.size();i++){
            arretNameList.add(listeArretProche.get(i).getArret());
        }
        OnFinish();
    }

    private int IDarretplusproche(Location location){
        Location loc1 = new Location("");
        loc1.setLatitude(location.getLatitude());
        loc1.setLongitude(location.getLongitude());

        Location loc2 = new Location("");
        int indx = 0;
        float anciennedist = 0;
        float nouvelledist = 0;
        for(int i =0;i<(listeArretProche.size());i++){
            ArretProche arretProche = listeArretProche.get(i);
            loc2.setLatitude(arretProche.getLat());
            loc2.setLongitude(arretProche.getLat());

            nouvelledist = loc1.distanceTo(loc2);
            if(nouvelledist < anciennedist){
                indx = i;
                anciennedist = nouvelledist;
            }
        }

        return indx;
    }

    private void OnFinish(){
        mAdapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, arretNameList);

        actv.setThreshold(1);
        actv.setAdapter(mAdapter);
    }

    private int getArretChoisi(String arret){
        int index = 0;
        for(int i = 0; i < listeArretProche.size(); i++){
            ArretProche arretProche = listeArretProche.get(i);

            if(arretProche.getArret().equals(arret)){
                index = i;

            }
        }
        return index;
    }

}

