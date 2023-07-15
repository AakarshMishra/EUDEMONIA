package com.example.Eudemonia.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.Eudemonia.R;

public class CouncellorOpen extends AppCompatActivity {
    Button getstarted,aicouncellor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councellor_open);
        getstarted=findViewById(R.id.getstarted);
        aicouncellor=findViewById(R.id.aicouncellor);
        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Councellor.class);
                startActivity(i);
            }
        });
        aicouncellor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SplashScreen.class);
                startActivity(i);
            }
        });
    }
}