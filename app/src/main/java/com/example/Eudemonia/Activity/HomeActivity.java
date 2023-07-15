package com.example.Eudemonia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Eudemonia.Adapter.TrendsAdapter;
import com.example.Eudemonia.Domain.TrendSDomain;
import com.example.Eudemonia.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapterTrendsList;
    private RecyclerView recyclerViewTrends;
    ImageView message,society,home,logout,setting,profile;
    TextView username;
    CircleImageView imagemyinfo;
    FirebaseDatabase database;
    FirebaseStorage storage;
    FirebaseAuth Auth;
    FloatingActionButton button;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        message = findViewById(R.id.message);
        society = findViewById(R.id.society);
        imagemyinfo=findViewById(R.id.imageView8);
        username=findViewById(R.id.name);
        profile=findViewById(R.id.profile);
        home=findViewById(R.id.home);
        logout=findViewById(R.id.logout);
        setting=findViewById(R.id.settings);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        Auth=FirebaseAuth.getInstance();
        button=findViewById(R.id.bar);
        DatabaseReference reference = database.getReference().child("user").child(Auth.getUid());
       StorageReference storageReference =storage.getReference().child("Upload").child(Auth.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue().toString();
                String profile = snapshot.child("profilepic").getValue().toString();
                username.setText("Hi, "+name);
                Picasso.get().load(profile).into(imagemyinfo);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        home.setClickable(true);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
            }
        });
        profile.setClickable(true);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);
            }
        });
        setting.setClickable(true);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), setting.class);
                startActivity(i);
            }
        });
        logout.setClickable(true);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView=new ListView(HomeActivity.this);
                List<String> data=new ArrayList<>();
                data.add("-> Home");
                data.add("-> Profile");
                data.add("-> Settings");
                data.add("-> Messenger");
                data.add("-> Councellor");
                data.add("-> Society");
                data.add("-> Change Password");
                data.add("-> Logout");

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1,data);
                listView.setAdapter(adapter);
                AlertDialog.Builder builder=new AlertDialog.Builder(HomeActivity.this);
                builder.setCancelable(true);
                builder.setView(listView);
                final AlertDialog dialog=builder.create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String select=String.valueOf(adapter.getItem(position));
                        if(select.equals("-> Home"))
                        {

                        }
                        else if(select.equals("-> Profile"))
                        {

                        }
                    }
                });
            }
        });

        message.setClickable(true);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), splash.class);
                startActivity(i);
            }
        });
        society.setClickable(true);
        society.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), splash2.class);
                startActivity(i);
            }
        });

        initRecyclerView();

    }


    private void initRecyclerView() {
        ArrayList<TrendSDomain> items = new ArrayList<>();

        items.add(new TrendSDomain("See the good.", "Nido Qubein", "trends"));
        items.add(new TrendSDomain("And still, I rise.", "Maya Angelou", "trends1"));
        items.add(new TrendSDomain("Make it happen", " Leonard Cohen", "trends2"));

        recyclerViewTrends = findViewById(R.id.view1);
        recyclerViewTrends.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapterTrendsList = new TrendsAdapter(items);
        recyclerViewTrends.setAdapter(adapterTrendsList);
    }


}