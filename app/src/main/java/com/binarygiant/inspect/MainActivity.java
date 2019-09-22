package com.binarygiant.inspect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "onCreate");
        checkPermissions();
    }

    /**
     * Checks for app permissions and requests if not given.
     */
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

    /**
     * Opens the FileManager activity.
     * @param view
     */
    public void toFileManager(View view) {
        Intent intent = new Intent(MainActivity.this, FileManager.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toFileManager");
    }
}