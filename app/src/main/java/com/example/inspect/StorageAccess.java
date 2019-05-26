package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsProvider;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;

import static androidx.core.app.ActivityCompat.startActivityForResult;


public class StorageAccess extends DocumentsProvider {
    private static final int READ_REQUEST_CODE = 42;

    public static void performFileSearch(Activity activity, Bundle bundle) {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show all files. Will filter when we have the MIME type for what files we will be using
        // Will become intent.setType("MIMEDATATYPE/*")
        intent.setType("*/*");
        Context context = App.getContext();
        LogManager.reportStatus(context, "STORAGEACCESS", "performFileSearch");

        startActivityForResult(activity, intent, READ_REQUEST_CODE, bundle);
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
