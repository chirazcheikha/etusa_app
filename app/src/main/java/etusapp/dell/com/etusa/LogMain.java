package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

/**
 * Created by DELL on 10/06/2018.
 */
 import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.firebase.ui.auth.ui.email.RegisterEmailFragment.TAG;

public class LogMain extends AppCompatActivity implements View.OnClickListener {
        FirebaseAuth mAuth;
        EditText ET1,ET2;
        Button B1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            findViewById(R.id.textv).setOnClickListener(this);
            findViewById(R.id.Buttonid);
            mAuth= FirebaseAuth.getInstance();
            ET1 =(EditText)findViewById(R.id.usert);
            ET2=(EditText)findViewById(R.id.paswwordet);
            findViewById(R.id.Buttonid).setOnClickListener(this);


        }
        public void userLogin(){
            String email = ET1.getText().toString().trim();
            String password = ET2.getText().toString().trim();
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWifi.isConnected()) {
                if (email.isEmpty()) {
                    ET1.setError("Veuillez saisir un email");
                    ET1.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ET1.setError("Saisissez un email valide S'il vous plait");
                    ET1.requestFocus();
                    return;
                }


                if (password.isEmpty()) {
                    ET2.setError("Veuillez saisir un mot de passe");
                    ET2.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    ET2.setError("Saisissez au moins 6 caractères ");
                    ET2.requestFocus();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(), "vous êtes connecté(e)", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogMain.this, Acceuil.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {

                            Toast.makeText(LogMain.this, "Email ou mot de passe incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "Verifiez votre connexion à internet", Toast.LENGTH_LONG).show();

            }
        }



        @Override
        public  void onClick (View view){
            switch (view.getId()){
                case R.id.textv:
                    startActivity(new Intent(getApplicationContext(),Sign_up.class));
                    break;
                case R.id.Buttonid:
                    userLogin();
                    break;

            }
        }
}