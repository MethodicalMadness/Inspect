package com.example.inspect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class PhotoManager extends AppCompatActivity {

    ImageView imageView;

    //Allows you to take a photo with the camera//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_manager);
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "takePhoto");

        Button btnCamera = (Button)findViewById(R.id.btnCamera);
        ImageView imageView = (ImageView)findViewById(R.id.imageView);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
    }

    //Allows you to alter/edit the photo//
    public static void alterPhoto() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "alterPhoto");
    }

    //Allows you to upload a previously taken photo from photo album/gallery on device//
    public static void getPhotoFromGallery(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTOMANAGER", "getPhotoFromGallery");
    }




}
