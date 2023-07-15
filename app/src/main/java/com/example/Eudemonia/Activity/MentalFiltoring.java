package com.example.Eudemonia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.Eudemonia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MentalFiltoring extends AppCompatActivity {
    EditText message;
    ImageView postmessage;
    FirebaseDatabase database;
    FirebaseAuth mAuth;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_filtoring);
        message=findViewById(R.id.message);
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        postmessage=findViewById(R.id.postamessage);
        postmessage.setClickable(true);
        DatabaseReference reference;
        reference = database.getReference().child("message").child(mAuth.getUid());

        postmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap user=new HashMap<>();
                user.put("message",message.getText().toString());
                reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MentalFiltoring.this,"Message Posted Successfully!",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(getApplicationContext(),Councellors.class);
                            i.putExtra("Age","18");
                            i.putExtra("Problem","Mental Filtering");
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(MentalFiltoring.this,"Message Posting Unsucessful!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}