package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;



public class FileManager extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_manager);
        configureBackBtn();
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "onCreate");
    }

    //Creates a new template
    public static void createTemplate(String filename, String blueprint){
        System.out.println("Called the createTemplate");
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "createTemplate");

        try{
            FileOutputStream fOut = context.openFileOutput(filename, Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.write(blueprint);
            osw.flush();
            osw.close();
        } catch(FileNotFoundException e){
            LogManager.wtf("FILEMANAGER", "createTemplate FileNotFoundException caught", e);
            e.printStackTrace();
        } catch(IOException e){
            LogManager.wtf("FILEMANAGER", "createTemplate IOException caught", e);
            e.printStackTrace();
        }
    }

    //Loads a template
    public void loadTemplate(View view) {
        System.out.println("I HAVE STARTED LOAD");
        Intent intent = new Intent(this, FileManager.class);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = (Activity) context;
        StorageAccess.performFileSearch(activity, bundle);
        //startActivity(intent);
        LogManager.reportStatus(context, "FILEMANAGER", "loadTemplate");
    }

    //Loads the saved state of the template
    public static void loadSavedState(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "loadSavedState");
    }

    //Deletes a template or pdf
    public static void deleteObject(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "deleteObject");
    }

    //Updates the list of files found in the storage directory to be displayed in the UI
    public static void updateDirectory(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "updateDirectory");
    }

    //Opens the pdf file created from the template
    public static void openPdf(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "openPdf");
    }

    public void configureBackBtn(){
        Button backBtn = (Button)findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "configureBackBtn");
    }
}
