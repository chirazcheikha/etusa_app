package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class Email extends AppCompatActivity {
    String objet;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        getSupportActionBar().setTitle("Nouveau message");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.email, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_envoyer:
                ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWifi.isConnected()) {
                    if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                        sendEmail();
                    } else {
                        Toast.makeText(getApplicationContext(), "vous devez vous connecter", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Email.this, LogMain.class);
                        startActivity(i);
                    }
                }else {
        Toast.makeText(getApplicationContext(), "Verifiez votre connexion à internet", Toast.LENGTH_LONG).show();
                }

                return true;

            default:

                return super.onOptionsItemSelected(item);

        }
    }


        protected void sendEmail() {
            Log.i("Send email", "");
            String[] TO = {"a.communication@etusa.dz"};

            String CC = FirebaseAuth.getInstance().getCurrentUser().getEmail();
             EditText e1=(EditText)findViewById(R.id.objet);
             EditText e2=(EditText)findViewById(R.id.message);
            objet= e1.getText().toString();
            message=e2.getText().toString();
            if (objet.matches("") || message.matches("")) {
                Toast.makeText(getApplicationContext(),"vous devez remplir les deux champs", Toast.LENGTH_LONG).show();

            }
            else{
            Intent emailIntent = new Intent(Intent.ACTION_VIEW);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, objet);
            emailIntent.putExtra(Intent.EXTRA_TEXT, message);


            try {
               startActivity(Intent.createChooser(emailIntent, "Send mail..."));
              finish();


                Log.i("Finished sending email", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(Email.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }}
    }







