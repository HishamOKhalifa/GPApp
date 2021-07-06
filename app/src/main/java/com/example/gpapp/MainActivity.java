package com.example.gpapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH= 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),StartActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        }, SPLASH);
    }









}
