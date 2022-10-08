package com.example.firebasepractice;

import static androidx.activity.result.contract.ActivityResultContracts.*;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.firebasepractice.databinding.ActivityUploadImageBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UploadImage extends AppCompatActivity {
    ActivityUploadImageBinding binding;
    public Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUploadImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnSave.setEnabled(false);

        FirebaseStorage storage=FirebaseStorage.getInstance("gs://fir-practice-75108.appspot.com");
        StorageReference storageRef = storage.getReference();

        ActivityResultLauncher<String> launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri u) {
                binding.imageView.setImageURI(u);
                uri=u;
                binding.btnSave.setEnabled(true);
            }
        });

        binding.btnUpload.setOnClickListener(view -> launcher.launch("image/*"));

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog p=new ProgressDialog(UploadImage.this);
                p.setTitle("Uploading...");
                p.show();

                if(uri!=null){
                    StorageReference mountainsRef = storageRef.child("picture/"+UUID.randomUUID().toString()); //will give random name to the uploaded files
                    mountainsRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            p.dismiss();
                            Toast.makeText(UploadImage.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }
}