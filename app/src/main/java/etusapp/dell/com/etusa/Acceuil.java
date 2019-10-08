package etusapp.dell.com.etusa;


import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import etusapp.localisation.MapsActivity2;

public class Acceuil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView ;
    private ActionBarDrawerToggle toggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ConstraintLayout con=(ConstraintLayout)findViewById(R.id.constraintLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);




        CardView c1 = (CardView) findViewById(R.id.cardligne);
        CardView c2 = (CardView) findViewById(R.id.cardho);
        CardView c3 = (CardView) findViewById(R.id.cardab);
        CardView c4 = (CardView) findViewById(R.id.rec);
        CardView c5 = (CardView) findViewById(R.id.cardarret);
        CardView c6 = (CardView) findViewById(R.id.c6);


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Acceuil.this, MainActivity.class);
                startActivity(intent);
            }
        });
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Acceuil.this, StationMain.class);
                startActivity(intent);

            }
        });
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Acceuil.this, AbonnementMain.class);
                startActivity(intent);

            }
        });
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Acceuil.this, Email.class);
                startActivity(intent);

            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {
                                      LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                      ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                      NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                                      if (mWifi.isConnected() && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                          Intent intent = new Intent(Acceuil.this, MapsActivity2.class);
                                          startActivity(intent);


                                      } else {
                                          if (!mWifi.isConnected()) {
                                              Toast.makeText(getApplicationContext(), "Verifiez votre connexion à internet", Toast.LENGTH_SHORT).show();

                                          } else {
                                              if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                                  Toast.makeText(getApplicationContext(), "Veuillez activer le service de localisation", Toast.LENGTH_SHORT).show();

                                              }
                                          }

                                      }
                                  }
                              });
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWifi.isConnected()) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        Intent i = new Intent(Acceuil.this, FavMain.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(getApplicationContext(), "vous devez d'abord vous connecter", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(Acceuil.this, LogMain.class);
                        startActivity(i2);

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Verifiez votre connexion à internet", Toast.LENGTH_LONG).show();
                }
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.logged, menu);
            return true;

        } else {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return true;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_login:

                Intent i1 = new Intent(Acceuil.this, LogMain.class);
                startActivity(i1);
                return true;

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), "vous êtes déconnecté(e)", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                return true;

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




}



