package com.example.inspect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ShareCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.inspect.Data.TemplateDatabase;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private static final int REQUEST_CODE = 1;
    private Button btnShare;
    private String currentTextPath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        Context context = App.getContext();

        //database
        RoomDatabase.Builder db = Room.databaseBuilder(this, TemplateDatabase.class, "template_database");

        //TODO: load modules into a list here

        LogManager.reportStatus(context, "MAINACTIVITY", "onCreate");

        //Creating share button
        btnShare = (Button)findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textFilePath = null;
                try {
                    textFilePath = createTextFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                shareFile(textFilePath);
            }

        });

    }

    //Attempt # 5: Sharing a file (this works)
    private void shareFile(String filePath) {
        Context context = App.getContext();
        File f = new File(filePath);
        File fileWithinMyDir = new File(filePath);
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        intentShareFile.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //bypass restrictions
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (fileWithinMyDir.exists()) {
            LogManager.reportStatus(context, "MAINACTIVITY", "fileExists");
            intentShareFile.setType("text/*");
            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + filePath));
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Inspect File Share: " + f.getName());
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Inspect File Share: " + f.getName());
            this.startActivity(Intent.createChooser(intentShareFile, f.getName()));
        }
        else{
            LogManager.reportStatus(context, "MAINACTIVITY", "fileDoesNotExist");
        }
    }

    // get hard coded file
    private String createTextFile() throws IOException {
        Context context = App.getContext();
        File storageDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "images");
        currentTextPath = storageDir.getPath() + File.separator + "text.txt";
        LogManager.reportStatus(context, "MAINACTIVITY", "gotTextFile");
        return currentTextPath;
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