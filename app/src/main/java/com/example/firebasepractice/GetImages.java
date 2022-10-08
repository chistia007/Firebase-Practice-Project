package com.example.firebasepractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.firebasepractice.databinding.ActivityGetImagesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class GetImages extends AppCompatActivity {
    ActivityGetImagesBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);;
        binding=ActivityGetImagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnGetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog=new ProgressDialog(GetImages.this);
                progressDialog.setMessage("Fetching Image...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                String imageId=binding.edtImgName.getText().toString();

                StorageReference storageRef=FirebaseStorage.getInstance().getReference("images/"+imageId+".png");

                try {
                    File localFile=File.createTempFile("tempfile",".png");
                    storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Bitmap bitmap= BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            binding.imgGet.setImageBitmap(bitmap);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}