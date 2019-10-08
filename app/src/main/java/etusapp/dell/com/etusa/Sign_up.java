package etusapp.dell.com.etusa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Sign_up extends Activity implements View.OnClickListener {
    EditText T1, T2;
    TextView TV;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        T1 = (EditText) findViewById(R.id.email);
        T2 = (EditText) findViewById(R.id.password);
        TV = (TextView) findViewById(R.id.signintv);
        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.signintv).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        String email = T1.getText().toString().trim();
        String password = T2.getText().toString().trim();
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            if (email.isEmpty()) {
                T1.setError("Veuillez saisir un email");
                T1.requestFocus();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                T1.setError("Saisissez un email valide S'il vous plait");
                T1.requestFocus();
                return;
            }


            if (password.isEmpty()) {
                T2.setError("Veuillez saisir un mot de passe");
                T2.requestFocus();
                return;
            }
            if (password.length() < 6) {
                T2.setError("Saisissez au moins 6 caractères ");
                T2.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "Utilisateur enregistré avec Succès", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LogMain.class));

                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(getApplicationContext(), "Vous vous êtes dèja inscrit(e)", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Sign_up.this, LogMain.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            // ...
                            }
                            });
                        } else {
                Toast.makeText(getApplicationContext(), "Verifiez votre connexion à internet", Toast.LENGTH_LONG).show();
            }
                    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                registerUser();
                break;
            case R.id.signintv:
                startActivity(new Intent(getApplicationContext(), LogMain.class));
                break;
        }

    }
}
