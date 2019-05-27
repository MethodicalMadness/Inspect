package com.example.inspect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class PhotoManager extends AppCompatActivity {

    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    //Allows you to take a photo with the camera or select a photo from the image gallery//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_manager);
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "takePhoto");

        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        Button btnGallery = (Button)findViewById(R.id.btnGallery);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        if (resultCode == RESULT_OK && resultCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    //Allows you to open image gallery//
    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "getPhotoFromGallery");
    }

    //Allows you to alter/edit the photo//
    public static void alterPhoto() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "alterPhoto");
    }




}
