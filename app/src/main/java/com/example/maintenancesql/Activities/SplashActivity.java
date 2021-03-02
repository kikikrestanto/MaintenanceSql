package com.example.maintenancesql.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.maintenancesql.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //bar transparant
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //isFirstTime();
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

                if (isLoggedIn){
                    startActivity(new Intent(SplashActivity.this,HomeAct.class));
                    finish();
                }

                /*Intent toLog = new Intent(SplashActivity.this,LoginAct.class);
                startActivity(toLog);
                finish(); */
                else {
                isFirstTime();
                }
            }
        },1500);
    }

    private void isFirstTime() {
        //we need to save a value to shared preference
        SharedPreferences preferences = getApplication().getSharedPreferences("onBoard", Context.MODE_PRIVATE);
        boolean  isFirstTime = preferences.getBoolean("isFirstTime",true);
        //default value true
        if (isFirstTime){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime",false);
            editor.apply();

            //start Login Act
            startActivity(new Intent(SplashActivity.this,LoginAct.class));
            finish();
        }
        else{
            //start Home Act
            startActivity(new Intent(SplashActivity.this,HomeAct.class));
            finish();
        }
    }

}
