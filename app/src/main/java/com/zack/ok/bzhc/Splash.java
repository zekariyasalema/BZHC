package com.zack.ok.bzhc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    String emailStored = "", passwordStored = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences("loginData", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                //editor.clear();
                editor.apply();
                emailStored = pref.getString("email", null);
                passwordStored = pref.getString("password", null);

                if(emailStored == null){
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                }
                else{
                    Intent in = new Intent(getApplicationContext(), Clienthome.class);
                    startActivity(in);
                }
                Splash.this.finish();
            }
        }, 3000);
    }
}