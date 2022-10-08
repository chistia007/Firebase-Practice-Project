package com.example.firebasepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.firebasepractice.databinding.ActivityReadDataBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class ReadData extends AppCompatActivity {
    ActivityReadDataBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityReadDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<String> a= new ArrayList<>();


        FirebaseFirestore db = FirebaseFirestore.getInstance();
// finding specific data with document reference
       /* DocumentReference docRef = db.collection("users").document("aaa@gmail.com");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    a.add(document.getString("name")+":"+ document.getString("email"));
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(ReadData.this,android.R.layout.simple_dropdown_item_1line,a);
                    binding.lst.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });*/
// Getting data as a collection
       db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                a.add(document.getString("name")+":"+ document.getString("email"));
                                ArrayAdapter<String> adapter=new ArrayAdapter<>(ReadData.this,android.R.layout.simple_dropdown_item_1line,a);
                                binding.lst.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}