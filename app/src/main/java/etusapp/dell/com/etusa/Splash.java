package etusapp.dell.com.etusa;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1800;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                    Intent mainIntent = new Intent(Splash.this,Acceuil.class);
                    Splash.this.startActivity(mainIntent);
                    Splash.this.finish();


            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}