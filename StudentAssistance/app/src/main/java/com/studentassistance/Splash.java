package com.studentassistance;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    static int SPLASH_TIME_OUT = 1000;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        userLocalStore = new UserLocalStore(this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if(userLocalStore.getuserloggedIn()) {
                    Intent i = new Intent(Splash.this, Home.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
