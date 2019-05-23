package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;



public abstract class FileManager extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;

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
    public void loadTemplate() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "loadTemplate");

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if(resultData != null){
                uri = resultData.getData();

            }
        }
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


}
