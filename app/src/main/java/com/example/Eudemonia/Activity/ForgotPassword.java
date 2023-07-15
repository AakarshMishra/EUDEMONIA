package com.example.Eudemonia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    ImageView forgetBtn;
    EditText txtEmail;
    TextView gotologin;
    String email;
    FirebaseAuth auth;
    android.app.ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending Mail...");
        progressDialog.setCancelable(false);
        auth=FirebaseAuth.getInstance();
        txtEmail=findViewById(R.id.Email);
        forgetBtn=findViewById(R.id.submit);
        gotologin = findViewById(R.id.GotoRegister);
        forgetBtn.setClickable(true);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);
                finish();
            }
        });

        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = String.valueOf(txtEmail.getText());

                if (TextUtils.isEmpty(email)) {
                    progressDialog.dismiss();
                    Toast.makeText(ForgotPassword.this, "Enter Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.show();
                            Toast.makeText(ForgotPassword.this, "Check Your Email!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, "Error! "+task.getException(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        });
    }
}