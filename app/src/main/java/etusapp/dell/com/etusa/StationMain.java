package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class StationMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView ;
    private ActionBarDrawerToggle toggle;
    private ListView lvStation;
    private ListStationAdapter adapter;
    private List<Station> mStationList;
    private DatabaseHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        lvStation = (ListView)findViewById(R.id.listview_station);
        mDBHelper = new DatabaseHelper(this);

        lvStation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent( StationMain.this,StationDirectionMain.class);
                Station station = (Station)mStationList.get(position);
                i.putExtra("IDstation",station.getIdStation());
                startActivity(i);
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ConstraintLayout con=(ConstraintLayout)findViewById(R.id.constraintLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()) {
            mDBHelper.getReadableDatabase();
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }


        mStationList = mDBHelper.getListStation();
        adapter = new ListStationAdapter(getApplicationContext(), mStationList);
        lvStation.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
            default:

                return super.onOptionsItemSelected(item);

        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_fav :
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if(mWifi.isConnected()){
                    if(FirebaseAuth.getInstance().getCurrentUser()== null){
                        Toast.makeText(getApplicationContext(), "vous devez d'abord vous connecter", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(getApplicationContext(), LogMain.class);
                        startActivity(i2);

                    }
                    else {
                        Intent i= new Intent(getApplicationContext(),FavMain.class);
                        startActivity(i);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Verifiez votre connexion à internet", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.activity_main_drawer_horaires:
                Intent i= new Intent(getApplicationContext(),StationMain.class);
                startActivity(i);

                break;
            case R.id.activity_main_drawer_lignes:
                Intent i1= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i1);
                break;
            case R.id.activity_main_abo:
                Intent i2= new Intent(getApplicationContext(),AbonnementMain.class);
                startActivity(i2);
                break;
            case R.id.activity_main_contacter:
                Intent i3= new Intent(getApplicationContext(),Email.class);
                startActivity(i3);
                break;
            case R.id.activity_main_acceuil:
                Intent i4= new Intent(getApplicationContext(),Acceuil.class);
                startActivity(i4);
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
            Log.w("StationMain","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
