package com.example.inspect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.net.DatagramPacket;


public class MainActivity<sizeIndex, nameIndex> extends AppCompatActivity {

    private static final String TAG = "Main Activity";
    private static final int REQUEST_CODE = 1;

    // The path to the root of this app's internal storage
    private File privateRootDir;
    // The path to the "images" subdirectory
    private File imagesDir;
    // Array of files in the images subdirectory
    File[] imageFiles;
    // Array of filenames corresponding to imageFiles
    String[] imageFilenames;
    // Initialize the Activity

    private Intent requestFileIntent;
    private ParcelFileDescriptor inputPFD;
    private Intent returnIntent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        requestFileIntent = new Intent(Intent.ACTION_PICK);
        requestFileIntent.setType("image/jpg");
        checkPermissions();
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "onCreate");
        // Set up an Intent to send back to apps that request a file
        final Intent resultIntent = new Intent("com.example.myapp.ACTION_RETURN_FILE");
        // Get the files/ subdirectory of internal storage
        privateRootDir = getFilesDir();
        // Get the files/images subdirectory;
        imagesDir = new File(privateRootDir, "images");
        // Get the files in the images subdirectory
        imageFiles = imagesDir.listFiles();
        // Set the Activity's result to null to begin with
        setResult(Activity.RESULT_CANCELED, null);
        /*
         * Display the file names in the ListView fileListView.
         * Back the ListView with the array imageFilenames, which
         * you can create by iterating through imageFiles and
         * calling File.getAbsolutePath() for each File
         */

        // Define a listener that responds to clicks on a file in the ListView
        AdapterView fileListView = null;
        fileListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    /*
                     * When a filename in the ListView is clicked, get its
                     * content URI and send it to the requesting app
                     */
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view,
                                            int position,
                                            long rowId) {
                        /*
                         * Get a File for the selected file name.
                         * Assume that the file names are in the
                         * imageFilename array.
                         */
                        String[] imageFilename = null;
                        File requestFile = new File(imageFilename[position]);
                        /*
                         * Most file-related method calls need to be in
                         * try-catch blocks.
                         */
                        // Use the FileProvider to get a content URI
                        try {
                            Uri fileUri = FileProvider.getUriForFile(
                                    MainActivity.this,
                                    "com.example.myapp.fileprovider",
                                    requestFile);
                        } catch (IllegalArgumentException e) {
                            Log.e("File Selector",
                                    "The selected file can't be shared: " + requestFile.toString());
                        }
                    }
                });

        // Define a listener that responds to clicks in the ListView
        fileListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view,
                                            int position,
                                            long rowId) {

                        Object fileUri = null;
                        if (fileUri != null) {
                            // Grant temporary read permission to the content URI
                            resultIntent.addFlags(
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        }
                
                    }
             
                });

        // Define a listener that responds to clicks on a file in the ListView
        fileListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view,
                                            int position,
                                            long rowId) {
                        Uri fileUri = null;
                        if (fileUri != null) {
                            // Put the Uri and MIME type in the result Intent
                            resultIntent.setDataAndType(
                                    fileUri,
                                    getContentResolver().getType(fileUri));
                            // Set the result
                            MainActivity.this.setResult(Activity.RESULT_OK,
                                    resultIntent);
                        } else {
                            resultIntent.setDataAndType(null, "");
                            MainActivity.this.setResult(RESULT_CANCELED,
                                    resultIntent);
                        }
                    }
                });

        /*
         * Get the file's content URI from the incoming Intent,
         * then query the server app to get the file's display name
         * and size.
         */
        Uri returnUri = returnIntent.getData();
        Cursor returnCursor =
                getContentResolver().query(returnUri, null, null, null, null);
        /*
         * Get the column indexes of the data in the Cursor,
         * move to the first row in the Cursor, get the data,
         * and display it.
         */
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        TextView nameView = (TextView) findViewById(R.id.filename_text);
        TextView sizeView = (TextView) findViewById(R.id.filesize_text);
        nameView.setText(returnCursor.getString(nameIndex));
        sizeView.setText(Long.toString(returnCursor.getLong(sizeIndex)));
    }

    /*
     * When the Activity of the app that hosts files sets a result and calls
     * finish(), this method is invoked. The returned Intent contains the
     * content URI of a selected file. The result code indicates if the
     * selection worked or not.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent returnIntent) {
        // If the selection didn't work
        if (resultCode != RESULT_OK) {
            // Exit without doing anything else
            return;
        } else {
            // Get the file's content URI from the incoming Intent
            Uri returnUri = returnIntent.getData();
            /*
             * Try to open the file for "read" access using the
             * returned URI. If the file isn't found, write to the
             * error log and return.
             */
            try {
                /*
                 * Get the content resolver instance for this context, and use it
                 * to get a ParcelFileDescriptor for the file.
                 */
                inputPFD = getContentResolver().openFileDescriptor(returnUri, "r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.e("MainActivity", "File not found.");
                return;
            }
            // Get a regular file descriptor for the file
            FileDescriptor fd = inputPFD.getFileDescriptor();
        }
    }

    public void onDoneClick(View v) {
        // Associate a method with the Done button
        finish();
    }

    protected void requestFile() {
        /**
         * When the user requests a file, send an Intent to the
         * server app.
         * files.
         */
        startActivityForResult(requestFileIntent, 0);
    }

    //setting up menu activities
    public void toTemplateMenu(View view){
        Intent intent = new Intent(MainActivity.this, TemplateMenu.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toTemplateMenu");
    }

    public void toLoadInspection(View view){
        Intent intent = new Intent(MainActivity.this, LoadInspection.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toLoadInspection");
    }

    public void toManageTemplates(View view){
        Intent intent = new Intent(MainActivity.this, ManageTemplateMenu.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "toManageTemplates");
    }

    public void newBlankTemplate(View view) {
        Intent intent = new Intent(this, TemplateEditor.class);
        startActivity(intent);
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "newBlankTemplate");
    }

    private void checkPermissions(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "MAINACTIVITY", "checkPermissions");
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(), permissions[2]) == PackageManager.PERMISSION_GRANTED){
            Context context2 = App.getContext();
            LogManager.reportStatus(context2, "MAINACTIVITY", "checkPermissions PERMISSIONS PASSED CORRECTLY");
        } else{
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
            Context context2 = App.getContext();
            LogManager.reportStatus(context2, "MAINACTIVITY", "checkPermissions PERMISSIONS REQUESTED");
        }
    }




}
