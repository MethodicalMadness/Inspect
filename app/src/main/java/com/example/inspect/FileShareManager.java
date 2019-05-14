package com.example.inspect;

import android.content.Context;

public class FileShareManager {

    //Allows you to send the pdf file to the Client//
    public static void sendEmail(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILESHAREMANAGER", "sendEmail");

    }


}
