package com.example.inspect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private static final int REQUEST_CODE = 1;

    Button btnShare;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "onCreate");

        //Creating share button
        btnShare = (Button)findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Your body here";
                String shareSub = "Your subject here";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share via"));
            }

        });

    }

    //setting up menu activities
    public void toTemplateMenu(View view) {
        Intent intent = new Intent(MainActivity.this, TemplateMenu.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toTemplateMenu");
    }

    public void toLoadInspection(View view) {
        Intent intent = new Intent(MainActivity.this, LoadInspection.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toLoadInspection");
    }

    public void toManageTemplates(View view) {
        Intent intent = new Intent(MainActivity.this, ManageTemplateMenu.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toManageTemplates");
    }

    public void toPhotoManager(View view){
        Intent intent = new Intent(MainActivity.this, PhotoManager.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toPhotoManager");
    }
  
    public void toFileManager(View view) {
        Intent intent = new Intent(MainActivity.this, FileManager.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toFileManager");
    }

    public void newBlankTemplate(View view) {
        Intent intent = new Intent(this, TemplateEditor.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "newBlankTemplate");
    }

    void checkPermissions() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "checkPermissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED) {
            Context context2 = App.getContext();
            LogManager.reportStatus(context2, "MAINACTIVITY", "checkPermissions PERMISSIONS PASSED CORRECTLY");
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            Context context2 = App.getContext();
            LogManager.reportStatus(context2, "MAINACTIVITY", "checkPermissions PERMISSIONS REQUESTED");
        }
    }


}