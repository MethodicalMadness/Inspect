package com.example.inspect;

import android.content.Context;

public class PhotoManager {

    //Allows you to take a Photo to attach to the template//
    public static void takePhoto(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTO", "Photo");
    }

    //Allows you to alter/edit the photo//
    public static void alterPhoto() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTO", "Photo");
    }

    //Allows you to upload a previously taken photo from photo album/gallery on device//
    public static void getPhotoFromGallery(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "PHOTO", "Photo");
    }


}
