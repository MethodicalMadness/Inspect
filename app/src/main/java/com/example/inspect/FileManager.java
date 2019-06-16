package com.example.inspect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;


public class FileManager extends AppCompatActivity {
    private boolean isInspecting = false;
    private static final int READ_REQUEST_CODE = 42;
    Uri uri;

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

    //Creates a new template
    public static void createTemplate(String filename, String blueprint){
        System.out.println("Called the createTemplate");
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "createTemplate");

        try{
            FileOutputStream fOut = new FileOutputStream(new File(App.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/" + filename), false);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.write(blueprint);
            osw.flush();
            osw.close();
        } catch(FileNotFoundException e){
            LogManager.wtf("FILEMANAGER", "createTemplate FileNotFoundException caught", e);
            e.printStackTrace();
        } catch(IOException e){
            LogManager.wtf("FILEMANAGER", "createTemplate IOException caught", e);
            e.printStackTrace();
        }
    }
    //Loads a template
    public void loadTemplateToInspect(View view) {
        Intent intent = new Intent(this, FileManager.class);
        isInspecting = true;
        intent.putExtra("isInspecting", isInspecting);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint");
        StorageAccess.performFileSearch(activity, bundle);
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint post StorageAccess");
    }

    //Loads a template
    public void loadTemplateToEdit(View view) {
        Intent intent = new Intent(this, FileManager.class);
        isInspecting = false;
        intent.putExtra("isInspecting", isInspecting);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint");
        StorageAccess.performFileSearch(activity, bundle);
        LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint post StorageAccess");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult closed SAF view");
        // This should grab the URI value of the file selected in StorageAccess for use
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult RESULT_OK true");
            if (resultData != null) {
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData not null");
                uri = resultData.getData();
                loadSavedState();
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData URI is: " + uri);

                //uri is to be used to access files within other sections of the program ie. loading a specific template or sharing an output
            } else{
                LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult resultData is null. Operation cancelled");
            }
        } else{
            LogManager.reportStatus(context, "FILEMANAGER", "onActivityResult cancelled - resultCode is not RESULT_OK or requestCode does not equal the READ_REQUEST_CODE");
        }

    }

    //Loads the saved state of the template
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
            //open inspect, passing blueprint through the intent
            Intent intent = new Intent(FileManager.this, Inspector.class);
            intent.putExtra("blueprint", blueprint);
            intent.putExtra("isInspecting", this.isInspecting);
            startActivity(intent);
            LogManager.reportStatus(context, "FILEMANAGER", "openingInspector");
            this.finish();
        } catch(FileNotFoundException e){
            LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint:FileNotFound");
            e.printStackTrace();
        } catch (IOException e) {
            LogManager.reportStatus(context, "FILEMANAGER", "retrieveBlueprint:IOException");
            e.printStackTrace();
        }
    }

    //Deletes a template or pdf
    public void deleteObject(){
        //Get information for the SAF method call
        Intent intent = new Intent(this, FileManager.class);
        Bundle bundle = intent.getExtras();
        Context context = App.getContext();
        Activity activity = this;
        LogManager.reportStatus(context, "FILEMANAGER", "deleteObject");
        //Call the SAF - Uri will be returned to global var uri
        StorageAccess.performFileSearch(activity, bundle);
        LogManager.reportStatus(context, "FILEMANAGER", "deleteObject post StorageAccess");
        //Delete file
        boolean confirmDelete = false;
        //TODO Add confirmation of deletion below this comment to make if statement valid
        File file = new File(uri.getPath());
        if(!confirmDelete) {
            if (file.exists()) {
                if (file.delete()) {
                    LogManager.reportStatus(context, "FILEMANAGER", "deleteObject File has been deleted");
                } else {
                    LogManager.reportStatus(context, "FILEMANAGER", "deleteObject File was not deleted");
                }
            }
        }
    }

    //Updates the list of files found in the storage directory to be displayed in the UI
    public static void updateDirectory(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "updateDirectory");
    }

    //Opens the pdf file created from the template
    public static void openPdf(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "FILEMANAGER", "openPdf");
    }

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
    public void onCreateNewTemplate(View view){
        Context context = App.getContext();
        isInspecting = false;
        Intent intent = new Intent(FileManager.this, Inspector.class);
        intent.putExtra("isInspecting", isInspecting);
        startActivity(intent);
        LogManager.reportStatus(context, "FILEMANAGER", "openingEditor");
        this.finish();
    }
}
