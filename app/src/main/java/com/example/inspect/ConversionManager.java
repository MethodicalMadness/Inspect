package com.example.inspect;

import android.content.Context;
import android.graphics.*;
import android.graphics.pdf.PdfDocument;
import android.os.*;
import android.print.*;
import android.print.pdf.PrintedPdfDocument;
import android.view.View;
import java.io.FileOutputStream;
import java.io.IOException;


public class ConversionManager {

    //Converts the template to a pdf file/document//
    public static void convertTemplate(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Generates a preview of the converted file/document//
    public static void generateTemplatePreview(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Generates a preview of the current saved state of the converted file/document//
    public static void generateSavedStatePreview(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Saves the converted file/pdf//
    public static void savePdf(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }
}
