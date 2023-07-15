package com.example.Eudemonia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Eudemonia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseStorage storage;
    ImageView image;
    TextView name,email,name2,regno2,email2,proctor,password;
    Button back;
    FirebaseAuth Auth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        name=findViewById(R.id.textname);
        name2=findViewById(R.id.name_text);
        email2=findViewById(R.id.email_text);
        regno2=findViewById(R.id.regno_text);
        proctor=findViewById(R.id.proctor_text);
        image=findViewById(R.id.imageView);
        email=findViewById(R.id.textView2);
        back=findViewById(R.id.button);
        password=findViewById(R.id.password);
        Auth=FirebaseAuth.getInstance();
        DatabaseReference reference = database.getReference().child("user").child(Auth.getUid());
        StorageReference storageReference = storage.getReference().child("Upload").child(Auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name1 = snapshot.child("userName").getValue().toString();
                String profile = snapshot.child("profilepic").getValue().toString();
                String email1 = snapshot.child("mail").getValue().toString();
                String proctor2 = snapshot.child("age").getValue().toString();
                name.setText(name1);
                email.setText(email1);
                email2.setText(email1);
                name2.setText(name1);
                regno2.setText(snapshot.child("phone").getValue().toString());
                proctor.setText(proctor2);
                password.setText(snapshot.child("phone").getValue().toString());

                Picasso.get().load(profile).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });

    }
}