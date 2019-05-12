package com.example.inspect;

import android.content.Context;
import android.view.View;


public class FileManager {

    //Creates a new template
    public void createTemplate(View view){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "createTemplate");
    }

    //Loads a template
    public static void loadTemplate() {
        Context context = App.getContext();
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


}
