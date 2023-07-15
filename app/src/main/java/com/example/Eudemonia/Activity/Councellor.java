package com.example.Eudemonia.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.Eudemonia.R;

public class Councellor extends AppCompatActivity {
    ImageView mental,catas,blamer,emotional;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councellor);
        mental=findViewById(R.id.mental);
        catas=findViewById(R.id.catas);
        blamer=findViewById(R.id.blamer);
        emotional=findViewById(R.id.emotional);
        mental.setClickable(true);
        blamer.setClickable(true);
        catas.setClickable(true);
        emotional.setClickable(true);
        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MentalFiltoring.class);
                startActivity(i);
            }
        });
        catas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Catastrophizing.class);
                startActivity(i);
            }
        });
        blamer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Blamer.class);
                startActivity(i);
            }
        });
        emotional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Emotional.class);
                startActivity(i);
            }
        });
    }
}