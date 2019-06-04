package com.example.inspect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.*;
import java.io.File;
import java.net.URI;
import java.net.URLConnection;


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
            public void onClick(View v) {

                //Sharing plain text (Working)
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Your body here";
                String shareSub = "Your subject here";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share via"));

                //Attempt # 1: Sharing text file (Failed Attempt)
                /*
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + "abc.txt");
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/*");
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(exportPath));;
                startActivity(Intent.createChooser(sharingIntent, "Share file via"));*/

                //Attempt # 2: Sharing a PDF file (Failed Attempt - Errors within code)
                /*
                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                File fileWithinMyDir = new File(myFilePath);
                URI fileUri = FileProvider.getUriForFile(this,
                        "com.example.inspect.fileprovider",
                        fileWithinMyDir);;
                if(fileWithinMyDir.exists()) {
                    intentShareFile.setType("Inspect/pdf");
                    intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+ myFilePath));
                    intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
                            "Sharing File...");
                    intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");

                    startActivity(Intent.createChooser(intentShareFile, "Share File via"));
                }*/

                //Attempt #3: Sharing a text file (Unsure if this works)
                /*
                File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + "abc.txt");
                Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                intentShareFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
                intentShareFile.putExtra(Intent.EXTRA_STREAM,
                        Uri.parse("content://"+file.getAbsolutePath()));
                intentShareFile.putExtra(Intent.EXTRA_SUBJECT,"Sharing File Subject");
                intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File Description");
                startActivity(Intent.createChooser(intentShareFile, "Share File via"));*/
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
            ActivityCompat.requestPermissions(MainActivity.this, permissions, REQUEST_CODE);
            Context context2 = App.getContext();
            LogManager.reportStatus(context2, "MAINACTIVITY", "checkPermissions PERMISSIONS REQUESTED");
        }
    }


}