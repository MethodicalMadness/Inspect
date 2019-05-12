package com.example.inspect;

import android.content.Context;


public class ConversionManager {

    //Converts the template to a pdf file/document//
    public static void convertTemplate(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "CONVERSIONMANAGER", "convertTemplate");
    }

    //Generates a preview of the converted file/document//
    public static void generateTemplatePreview(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "CONVERSIONMANAGER", "generateTemplatePreview");
    }

    //Generates a preview of the current saved state of the converted file/document//
    public static void generateSavedStatePreview(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "CONVERSIONMANAGER", "generateSavedStatePreview");
    }

    //Saves the converted file/pdf//
    public static void savePdf(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "CONVERSIONMANAGER", "savePdf");
    }
}
