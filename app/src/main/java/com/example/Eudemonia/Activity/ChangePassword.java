package com.example.Eudemonia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Eudemonia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ChangePassword extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseStorage storage;

    Button back;
    EditText password,confirmp;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Changing the Password");
        progressDialog.setCancelable(false);
        mAuth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        password=findViewById(R.id.TextPassword);
        confirmp=findViewById(R.id.TextConfirmPassword);
        DatabaseReference reference;
        StorageReference storageReference;
        reference = database.getReference().child("user").child(mAuth.getUid());
        storageReference = storage.getReference().child("Upload").child(mAuth.getUid());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Password = password.getText().toString();
                String cPassword = confirmp.getText().toString();
                if (TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword)){
                    progressDialog.dismiss();
                    Toast.makeText(ChangePassword.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();}
                else if(Password.length()<6){
                    progressDialog.dismiss();
                    password.setError("Password Must Be 6 Characters Or More");
                }else if(!Password.equals(cPassword)){
                    progressDialog.dismiss();
                    password.setError("The Password Doesn't Match");
                }else{
                    HashMap user=new HashMap<>();
                    user.put("password",Password);
                    reference.updateChildren(user).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if(task.isSuccessful())
                            {
//                                progressDialog.show();
                                FirebaseUser User=mAuth.getCurrentUser();
                                User.updatePassword(Password).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ChangePassword.this,"Updated Sucessfully!",Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChangePassword.this,"Failed to Update!",Toast.LENGTH_LONG).show();
                                    }
                                });
                                password.setText("");
                                confirmp.setText("");
                                progressDialog.show();
                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                                finish();




                            }
                            else {
                                Toast.makeText(ChangePassword.this,"Failed to Update!",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

    }
}