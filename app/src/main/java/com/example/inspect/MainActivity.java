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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = App.getContext();
        checkPermissions();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            System.out.println("EXTERNAL STORAGE WRITE ACCESS DENIED");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } else{
            System.out.println("EXTERNAL STORAGE WRITE ACCESS GRANTED");
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            System.out.println("EXTERNAL STORAGE WRITE ACCESS IS STILL DENIED");
        } else{
            System.out.println("EXTERNAL STORAGE WRITE ACCESS NOW GRANTED");
        }
        LogManager.reportStatus(context, "TEST", "Test");

    }

    //setting up menu activities
    public void toTemplateMenu(View view){
        Intent intent = new Intent(MainActivity.this, TemplateMenu.class);
        startActivity(intent);
    }

    public void toLoadInspection(View view){
        Intent intent = new Intent(MainActivity.this, LoadInspection.class);
        startActivity(intent);
    }

    public void toManageTemplates(View view){
        Intent intent = new Intent(MainActivity.this, ManageTemplateMenu.class);
        startActivity(intent);
    }

    public void newBlankTemplate(View view) {
        Intent intent = new Intent(this, TemplateEditor.class);
        startActivity(intent);
    }

    private void checkPermissions(){
        System.out.println("CHECKING PERMISSIONS");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED){
            System.out.println("CHECKED OUT");
        } else{
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            System.out.println("DID NOT CHECK OUT");
        }
    }




}
