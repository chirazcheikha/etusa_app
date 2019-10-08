package etusapp.dell.com.etusa;

/**
 * Created by DELL on 07/05/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView lvLigne;
    private ListLigneAdapter adapter;
    private List<Ligne> mLigneList;
    private DatabaseHelper mDBHelper;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView ;
    private ActionBarDrawerToggle toggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvLigne = (ListView)findViewById(R.id.listview_ligne);
        mDBHelper = new DatabaseHelper(this);


        lvLigne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent( MainActivity.this,ArretMain.class);
                Ligne ligne = (Ligne)mLigneList.get(position);
                i.putExtra("ID",ligne.getId());
                i.putExtra("numLigne",ligne.getNumLigne());
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
        mLigneList = mDBHelper.getListLigne();
        adapter = new ListLigneAdapter(this, mLigneList);
        lvLigne.setAdapter(adapter);

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
            Log.w("MainActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   
}
