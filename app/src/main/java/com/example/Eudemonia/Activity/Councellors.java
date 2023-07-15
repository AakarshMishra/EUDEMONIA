package com.example.Eudemonia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Eudemonia.R;
import com.google.android.gms.common.util.IOUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Councellors extends AppCompatActivity {
    TextView username;
    CircleImageView imagemyinfo;
    ImageView doc1,doc2,doc3;
    FirebaseDatabase database;
    TextView testdoc1;
    FirebaseStorage storage;
    FirebaseAuth Auth;
    private Interpreter tflite;
    private LabelEncoder labelEncoder;
    String age;
    String problem;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_councellors);
        try {
            tflite = new Interpreter(loadModelFile());
            labelEncoder = loadLabelEncoder();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagemyinfo=findViewById(R.id.imageView8);
        testdoc1=findViewById(R.id.textdoc1);
        username=findViewById(R.id.name);
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        Auth=FirebaseAuth.getInstance();
        doc1=findViewById(R.id.doc1);
        doc2=findViewById(R.id.doc2);
        doc3=findViewById(R.id.doc3);
        Intent i = new Intent();
        age=i.getStringExtra("Age");
        problem=i.getStringExtra("Problem");
        recommendDoctor();
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
        doc1.setClickable(true);
        doc1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView=new ListView(Councellors.this);
                List<String> data=new ArrayList<>();
                data.add("-> Name : Dr. Jack willson");
                data.add("-> Post : Experienced Councellor");
                data.add("-> Time : 09:25 AM- 13:30 PM");
                data.add("-> Phone : 0532786544");
                data.add("-> Email : jackwillson@gmail.com");

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(Councellors.this, android.R.layout.simple_list_item_1,data);
                listView.setAdapter(adapter);
                AlertDialog.Builder builder=new AlertDialog.Builder(Councellors.this);
                builder.setCancelable(true);
                builder.setView(listView);
                final AlertDialog dialog=builder.create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String select=String.valueOf(adapter.getItem(position));
                        if(select.equals("-> Phone : 0532786544"))
                        {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel: 0532786544"));
                            startActivity(intent);
                        }
                        else if(select.equals("-> Email : jackwillson@gmail.com"))
                        {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:")); // Only email apps handle this.
                            intent.putExtra(Intent.EXTRA_EMAIL, "jackwillson@gmail.com");
                            startActivity(intent);

                        }
                    }
                });

            }
        });
        doc2.setClickable(true);
        doc2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView=new ListView(Councellors.this);
                List<String> data=new ArrayList<>();
                data.add("-> Name : Dr. Andy Brown");
                data.add("-> Post : Experienced Councellor");
                data.add("-> Time : 10:00 AM- 12:15 PM");
                data.add("-> Phone : 0532786543");
                data.add("-> Email : andy@gmail.com");

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(Councellors.this, android.R.layout.simple_list_item_1,data);
                listView.setAdapter(adapter);
                AlertDialog.Builder builder=new AlertDialog.Builder(Councellors.this);
                builder.setCancelable(true);
                builder.setView(listView);
                final AlertDialog dialog=builder.create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String select=String.valueOf(adapter.getItem(position));
                        if(select.equals("-> Phone : 0532786543"))
                        {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel: 0532786543"));
                            startActivity(intent);
                        }
                        else if(select.equals("-> Email : andy@gmail.com"))
                        {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:")); // Only email apps handle this.
                            intent.putExtra(Intent.EXTRA_EMAIL, "andy@gmail.com");
                            startActivity(intent);

                        }
                    }
                });

            }
        });
        doc3.setClickable(true);
        doc3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView=new ListView(Councellors.this);
                List<String> data=new ArrayList<>();
                data.add("-> Name : Dr. William Smith");
                data.add("-> Post : Experienced Councellor");
                data.add("-> Time : 08:25 AM- 12:30 PM");
                data.add("-> Phone : 0532786545");
                data.add("-> Email : william@gmail.com");

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(Councellors.this, android.R.layout.simple_list_item_1,data);
                listView.setAdapter(adapter);
                AlertDialog.Builder builder=new AlertDialog.Builder(Councellors.this);
                builder.setCancelable(true);
                builder.setView(listView);
                final AlertDialog dialog=builder.create();
                dialog.show();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String select=String.valueOf(adapter.getItem(position));
                        if(select.equals("-> Phone : 0532786545"))
                        {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel: 0532786545"));
                            startActivity(intent);
                        }
                        else if(select.equals("-> Email : william@gmail.com"))
                        {
                            Intent intent = new Intent(Intent.ACTION_SENDTO);
                            intent.setData(Uri.parse("mailto:")); // Only email apps handle this.
                            intent.putExtra(Intent.EXTRA_EMAIL, "william@gmail.com");
                            startActivity(intent);

                        }
                    }
                });

            }
        });
    }
    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = getAssets().openFd("doctor_model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private LabelEncoder loadLabelEncoder() {
        try {
            FileInputStream inputStream = new FileInputStream("label_encoder.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String jsonStr = stringBuilder.toString();
            JSONObject jsonObject = new JSONObject(jsonStr);
            return LabelEncoder.fromJson(jsonObject.names());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    private void recommendDoctor() {
        // ...

        // Perform validation on user input

        // Encode the problem using the loaded label encoder
        int encodedProblem[] = labelEncoder.transform(problem);

        // Prepare the input data for the model
        float[][] input = new float[1][2];
        input[0][0] = Float.parseFloat(age);
        input[0][1] = encodedProblem[0];

        // Run the model inference
        int[] output = new int[1];
        tflite.run(input, output);

        // Decode the predicted doctor using the loaded label encoder
        String recommendedDoctor = labelEncoder.inverseTransform(output)[0];
        testdoc1.setText(recommendedDoctor);
        Toast.makeText(this, recommendedDoctor, Toast.LENGTH_SHORT).show();

        // Find the doctor details based on the recommended doctor

        // ...
    }
}