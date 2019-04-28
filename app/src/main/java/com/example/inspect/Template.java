package com.example.inspect;

import android.content.Context;

public class Template {

    //Saves current state of the template//
    public static void saveState(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");

    }


}
