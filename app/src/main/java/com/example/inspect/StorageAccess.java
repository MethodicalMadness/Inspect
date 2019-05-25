package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsProvider;

import static androidx.core.app.ActivityCompat.startActivityForResult;


public abstract class StorageAccess extends DocumentsProvider {
    private static final int READ_REQUEST_CODE = 42;

    public static void performFileSearch(Activity activity, Bundle bundle) {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(activity, intent, READ_REQUEST_CODE, bundle);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent resultData){
        if(requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri uri = null;
            if(resultData != null){
                uri = resultData.getData();

            }
        }
    }
}
