package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
import androidx.annotation.Nullable;
import java.io.FileNotFoundException;
import java.net.URI;

import static android.provider.DocumentsContract.Root.COLUMN_ROOT_ID;
import static androidx.core.app.ActivityCompat.startActivityForResult;


public class StorageAccess extends DocumentsProvider {

    /**
     * Performs file search and returns uri for file through the intent.
     * @param activity
     * @param bundle
     * @param requestCode
     */
    public static void performFileSearch(Activity activity, Bundle bundle, int requestCode) {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        //intent.addCategory(COLUMN_ROOT_ID); This is already added by default and re adding will break the app
        String path = "Android/data/com.example.inspect/files/Documents/";
        String path2 = "android.resource://com.example.inspect/";
        intent.putExtra(COLUMN_ROOT_ID, path2);


        // Filter to only show results that can be "opened", such as a file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show all files. Will filter when we have the MIME type for what files we will be using
        // Will become intent.setType("MIMEDATATYPE/*")
        if(requestCode == 0){
            //Fiddling with this code and trying to figure how to make it specific extensions so we can separate saved state, blueprint state, and pdf state
            intent.setType("*/*");
            //intent.setType("text/plain");
        } else if(requestCode == 2) {
            //requestCode 2 is the share activity - MIME type works fine and no issue unless we want to share blueprints as well
            intent.setType("application/pdf");
        } else{
            //if the requestCode is not either of the above then it will default to allow all file types
            intent.setType("*/*");
        }
        Context context = App.getContext();
        LogManager.reportStatus(context, "STORAGEACCESS", "performFileSearch");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/Documents/");
            intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, uri);
        }

        startActivityForResult(activity, intent, requestCode, bundle);
        LogManager.reportStatus(context, "STORAGEACCESS", "performFileSearch Completed");
    }

    @Override
    public Cursor queryRoots(String[] projection) throws FileNotFoundException {
        return null;
    }

    @Override
    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        return null;
    }

    @Override
    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) throws FileNotFoundException {
        return null;
    }

    @Override
    public ParcelFileDescriptor openDocument(String documentId, String mode, @Nullable CancellationSignal signal) throws FileNotFoundException {
        return null;
    }

    @Override
    public boolean onCreate() {
        return false;
    }
}
