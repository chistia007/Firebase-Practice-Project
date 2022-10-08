package com.example.firebasepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasepractice.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
// setting up email as document so that we can fetch specific data with this email
/*        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,String> user=new HashMap<>();
                user.put("name",binding.edtName.getText().toString());
                user.put("email",binding.edtEmail.getText().toString());
                user.put("phone",binding.edtPhone.getText().toString());

                CollectionReference cities = db.collection("users");

                cities.document(binding.edtEmail.getText().toString())
                        .set(user);

                startActivity(new Intent(MainActivity.this,ReadData.class));


            }
        });*/

// storing data as a collection
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String,String> user=new HashMap<>();
                user.put("name",binding.edtName.getText().toString());
                user.put("email",binding.edtEmail.getText().toString());
                user.put("phone",binding.edtPhone.getText().toString());


                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "Inserted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this,ReadData.class));
                            }
                        });

                            }
                        });



        binding.btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UploadImage.class));
            }
        });

        binding.btnGetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,GetImages.class));
            }
        });


    }
}