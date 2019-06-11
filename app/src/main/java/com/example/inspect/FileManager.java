package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;



public class FileManager extends AppCompatActivity {
    private static final int READ_REQUEST_CODE = 42;
    Uri uri;

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
        Intent intent = new Intent(this, FileManager.class);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "loadTemplate");
        StorageAccess.performFileSearch(activity, bundle);
        LogManager.reportStatus(context, "FILEMANAGER", "loadTemplate post StorageAccess");
        //TODO whatever it is with the template to load

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult closed SAF view");
        // This should grab the URI value of the file selected in StorageAccess for use
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult RESULT_OK true");
            if (resultData != null) {
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData not null");
                uri = resultData.getData();
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData URI is: " + uri);
                //uri is to be used to access files within other sections of the program ie. loading a specific template or sharing an output
            } else{
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData is null. Operation cancelled");
            }
        } else{
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult cancelled - resultCode is not RESULT_OK or requestCode does not equal the READ_REQUEST_CODE");
        }

    }

    //Loads the saved state of the template
    public static void loadSavedState(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "loadSavedState");
    }

    //Deletes a template or pdf
    public void deleteObject(){
        //Get information for the SAF method call
        Intent intent = new Intent(this, FileManager.class);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "deleteObject");
        //Call the SAF - Uri will be returned to global var uri
        StorageAccess.performFileSearch(activity, bundle);
        LogManager.reportStatus(context, "FILEMANAGER", "deleteObject post StorageAccess");
        //Delete file
        boolean confirmDelete = false;
        //TODO Add confirmation of deletion below this comment to make if statement valid
        File file = new File(uri.getPath());
        if(!confirmDelete) {
            if (file.exists()) {
                if (file.delete()) {
                    LogManager.reportStatus(context, "FILEMANAGER", "deleteObject File has been deleted");
                } else {
                    LogManager.reportStatus(context, "FILEMANAGER", "deleteObject File was not deleted");
                }
            }
        }
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
