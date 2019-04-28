package com.example.inspect;

import android.content.Context;

public class Inspector {

    //Allows you to edit a text field in the template//
    public static void editTextField(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTION", "Inspection");
    }

    //Allows you to edit the attached image in the template//
    public static void editImageField(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTION", "Inspection");
    }

    //Provides functionality for when a check box is toggled//
    public static void toggleCheckBox(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTION", "Inspection");
    }


}
