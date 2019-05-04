package com.example.inspect;

import android.content.Context;


public class FileManager {

    //Creates a new template//
    public static void createTemplate(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Loads a template//
    public static void loadTemplate() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Loads the saved state of the template//
    public static void loadSavedState(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Deletes a template or pdf//
    public static void deleteObject(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Updates the list of files found in the storage directory to be displayed in the UI//
    public static void updateDirectory(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Opens the pdf file created from the template//
    public static void openPdf(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }


}
