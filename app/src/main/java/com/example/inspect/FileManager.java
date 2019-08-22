package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.os.health.SystemHealthManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.codekidlabs.storagechooser.StorageChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.Scanner;


public class FileManager extends AppCompatActivity {
    private boolean isInspecting = false;
    private static final int EDIT_REQUEST_CODE = 0;
    private static final int READ_REQUEST_CODE = 1;
    private static final int SHARE_REQUEST_CODE = 2;
    private static final int DELETE_REQUEST_CODE = 3;
    Uri uri;
    StorageChooser chooser;
    String globalPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_manager);
        configureBackBtn();
        Intent intent = this.getIntent();
        //get mode from intent
        if(intent.hasExtra("isInspecting")){
            isInspecting = intent.getExtras().getBoolean("isInspecting");
        }
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "onCreate");
    }

    /**
     * Creates a new template
     * @param filename
     * @param blueprint
     * @return
     */
    public static boolean createTemplate(String filename, String blueprint){
        boolean hasSaved = false;
        System.out.println("Called the createTemplate");
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "createTemplate");
        try{
            //Testing to check the path this gives - Seems correct
            //System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/Inspect/" + filename));

            //Current working line for saving the file
            FileOutputStream fOut = new FileOutputStream(new File(App.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + filename), false);

            //This is supposed to get the public documents folder location as printed from above on L63
            //FileOutputStream fOut = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Inspect/" + filename), false);
            
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            osw.write(blueprint);
            osw.flush();
            osw.close();
            hasSaved = true;
        } catch(FileNotFoundException e){
            LogManager.wtf("FILEMANAGER", "createTemplate FileNotFoundException caught", e);
            e.printStackTrace();
            hasSaved = false;
        } catch(IOException e){
            LogManager.wtf("FILEMANAGER", "createTemplate IOException caught", e);
            e.printStackTrace();
            hasSaved = false;
        }
        return hasSaved;
    }

    /**
     * Loads a template for an inspection
     * @param view
     */
    public void loadTemplateToInspect(View view) {


        //Implementing storage-chooser by codekidX
        // https://android-arsenal.com/details/1/5336
        // Initialize Builder
        chooser = new StorageChooser.Builder()

                .withActivity(this)
                //Below forces the app to keep the same path (currently what we have with SAF)
                //.shouldResumeSession(true)
                .withFragmentManager(getFragmentManager())
                .withMemoryBar(true)
                .setType(StorageChooser.FILE_PICKER)
                .allowCustomPath(true)
                //PredefinedPath is not working correctly - Checked documentation and unable to find solution
                .withPredefinedPath("/storage/emulated/0/Android/data/com.example.inspect/files/Documents/")
                /* Attempted variations of the path and could not get it to work as intended
                * /storage/emulated/0/Android/data/com.example.inspect/files/Documents/
                * */
                .disableMultiSelect()
                .build();

        // Show dialog whenever you want by
        chooser.show();

        // get path that the user has chosen
        chooser.setOnSelectListener(new StorageChooser.OnSelectListener() {
            @Override
            public void onSelect(String path) {
                globalPath = path;
                Log.e("SELECTED_PATH", path);
                System.out.println(path);
                uri = Uri.fromFile(new File (path));
                Context context = App.getContext();
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData URI is: " + uri);
                loadSavedState();
            }
        });


/*      //ORIGINAL SAF IMPLEMENTATION
        Intent intent = new Intent(this, FileManager.class);
        this.isInspecting = true;
        intent.putExtra("isInspecting", true);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint");
        StorageAccess.performFileSearch(activity, bundle, READ_REQUEST_CODE);
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint post StorageAccess");

 */
    }

    /**
     * Loads a template for to edit elements
     * @param view
     */
    public void loadTemplateToEdit(View view) {
        Intent intent = new Intent(this, FileManager.class);
        this.isInspecting = false;
        intent.putExtra("isInspecting", false);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint");
        StorageAccess.performFileSearch(activity, bundle, EDIT_REQUEST_CODE);
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint post StorageAccess");
    }

    /**
     * Creates an intent and starts the file sharing activity
     * @param view
     */
    public void onShare(View view){
        Intent intent = new Intent(this, FileManager.class);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveFileToShare");
        StorageAccess.performFileSearch(activity, bundle, SHARE_REQUEST_CODE);
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveFileToShare post StorageAccess");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult closed SAF view");

        // This grab the URI value of the file selected in StorageAccess for use depending on whic h request was made
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult RESULT_OK true");
            if (resultData != null) {
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData not null");
                //uri is to be used to access files within other sections of the program ie. loading a specific template or sharing an output
                uri = resultData.getData();
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData URI is: " + uri);
                loadSavedState();
            } else {
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData is null. Operation cancelled");
            }
        } else if (requestCode == EDIT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult RESULT_OK true");
            if (resultData != null) {
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData not null");
                //uri is to be used to access files within other sections of the program ie. loading a specific template or sharing an output
                uri = resultData.getData();
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData URI is: " + uri);
                loadSavedState();
            } else {
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData is null. Operation cancelled");
            }
        } else if (requestCode == SHARE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult RESULT_OK true");
            if (resultData != null) {
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData not null");
                uri = resultData.getData();
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData URI is: " + uri);
                shareFile();
            }
        }else if (requestCode == DELETE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //Delete file
            File file = new File(uri.getPath());
            if (file.exists()) {
                if (file.delete()) {
                    LogManager.reportStatus(context, "FILEMANAGER", "deleteObject File has been deleted");
                } else {
                    LogManager.reportStatus(context, "FILEMANAGER", "deleteObject File was not deleted");
                }
            }
        } else{
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult cancelled - resultCode is not RESULT_OK or requestCode does not equal the READ_REQUEST_CODE");
        }
    }

    /**
     * Retrieves blueprint from file and starts the inspector activity.
     * Depending on the mode it will determine if the Inspector is opened in editing mode or inspect mode.
     */
    public void loadSavedState(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "loadSavedState");
        String blueprint = "";
        try (InputStream textFileStream = getContentResolver().openInputStream(uri)){
            Scanner scanner = new Scanner(textFileStream);
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                blueprint += currentLine;
                if (scanner.hasNextLine()) {
                    blueprint += "\n";
                }
            }
            scanner.close();
            LogManager.reportStatus(context, "FILEMANAGER", "retrievedBlueprintFromFile");
            //get filename from uri
            String filename = getFileName(uri);
            String ext = getFileExt(filename);
            LogManager.reportStatus(context, "FILEMANAGER", "extension: " + ext);
            //check that the correct filetype matches the mode we are launching
            if (ext.contentEquals(".bp") || ext.contentEquals(".in")) {
                //open inspect, passing blueprint through the intent
                Intent intent = new Intent(FileManager.this, Inspector.class);
                intent.putExtra("blueprint", blueprint);
                intent.putExtra("isInspecting", this.isInspecting);
                intent.putExtra("filename", filename);
                startActivity(intent);
                LogManager.reportStatus(context, "FILEMANAGER", "openingInspector");
                this.finish();
            } else {
                Toast.makeText(this, "Incorrect File Type!", Toast.LENGTH_LONG).show();
            }
        } catch(FileNotFoundException e){
            LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint:FileNotFound");
            e.printStackTrace();
        } catch (IOException e) {
            LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint:IOException");
            e.printStackTrace();
        }
    }

    /**
     * Get the filename from a Uri
     * @param uri
     * @return
     */
    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


    /**
     * Removes a file extension from a string
     * @param filename
     * @return
     */
    public static String removeExtension(String filename){
        String result = filename;
        int dotIndex = filename.lastIndexOf('.');
        if(dotIndex >= 0) { // to prevent exception if there is no dot
            result = filename.substring(0,dotIndex);
        }
        return result;
    }

    /**
     * Retrieves a file extension from a string
     * @param filename
     * @return
     */
    public static String getFileExt(String filename){
        String result = "";
        int dotIndex = filename.lastIndexOf('.');
        if(dotIndex >= 0) { // to prevent exception if there is no dot
            result = filename.substring(dotIndex);
        }
        return result;
    }

    /**
     * Delete a file system object
     */
    public void deleteObject(){
        //Get information for the SAF method call
        Intent intent = new Intent(this, FileManager.class);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "deleteObject");
        //Call the SAF - Uri will be returned to global var uri
        StorageAccess.performFileSearch(activity, bundle, DELETE_REQUEST_CODE);
        LogManager.reportStatus(context, "FILEMANAGER", "deleteObject post StorageAccess");
    }

    /**
     * Configure the back button.
     */
    public void configureBackBtn(){
        Button backBtn = (Button)findViewById(R.id.btnFileManagerBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "configureBackBtn");
    }

    //open inspect, passing blueprint and mode through the intent

    /**
     * Open inspect in editing mode with no blueprint.
     * @param view
     */
    public void onCreateNewTemplate(View view){
        Context context = App.getContext();
        //get the filename from user
        EditText filenameEditText = findViewById(R.id.filename_text);
        String filename = filenameEditText.getText().toString();
        //remove extension a user gave and replace with ours
        filename = removeExtension(filename) + ".bp";
        isInspecting = false;
        Intent intent = new Intent(FileManager.this, Inspector.class);
        intent.putExtra("isInspecting", isInspecting);
        intent.putExtra("filename", filename);
        startActivity(intent);
        LogManager.reportStatus(context, "FILEMANAGER", "openingEditor");
        this.finish();
    }

    /**
     * Unhides filename view for user input.
     * @param view
     */
    public void showFilename(View view){
        LinearLayout layout= findViewById(R.id.filename_layout);
        layout.setVisibility(View.VISIBLE);
    }

    /**
     * When an activity result requests file share the retrived Uri is passed to the email through
     * an intent which launches the share activity.
     */
    private void shareFile() {
        Context context = App.getContext();
        //bypass restrictions
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //try start the activity
        try{
            //create intent
            Intent intentShareFile = new Intent(Intent.ACTION_SEND);
            intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //email requirements
            intentShareFile.setType(URLConnection.guessContentTypeFromName(getFileName(uri)));
            intentShareFile.putExtra(Intent.EXTRA_STREAM, uri);
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Inspect File Share: " + getFileName(uri));
            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Inspect File Share: " + getFileName(uri));
            //start activity
            this.startActivity(Intent.createChooser(intentShareFile, getFileName(uri)));
        } catch (Throwable t){
            Toast.makeText(this, "Request failed try again: " + t.toString(), Toast.LENGTH_LONG).show();
            LogManager.reportStatus(context, "FILEMANAGER", "fileShare: fileDoesNotExist");
        }
    }
}
