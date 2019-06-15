package com.example.inspect;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.inspect.databinding.TextFieldBinding;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Inspector extends AppCompatActivity{

    private LinearLayout linearLayoutPdf;
    private LinearLayout linearLayoutBody;
    private ArrayList<View> pageViews = new ArrayList<>();
    private TemplatePage currentPage;
    private TemplateExample templateExample = new TemplateExample(null);
    private String blueprint = "0\n" + "1,Name,Burt";
    private int doOnce = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = App.getContext();
        setContentView(R.layout.inspection_loaded);
        linearLayoutPdf = findViewById(R.id.linearLayoutPdf);
        linearLayoutBody = findViewById(R.id.linearLayoutBody);
        if(doOnce == 0){
            doOnce = 1;
            Intent myIntent = this.getIntent();
            //Uri fileUri = Uri.parse(myIntent.getExtras().getString("fileUri"));
            //retrieveBlueprint(fileUri);
            blueprint = myIntent.getExtras().getString("blueprint");
            loadString(blueprint);
        }else{
            loadString(blueprint);
        }

        LogManager.reportStatus(context, "INSPECTOR", "onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        blueprint = templateExample.createBlueprint();
    }

    // TODO: rework when GUI is sorted
    // Add the new field at bottom of layout.
    public void addTextField(String label, String fill) {
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementTextField elementTextField = new ElementTextField(label, fill);
        //add element data object to page
        currentPage.addElement(elementTextField);
        //we need to instantiate our xml fragment (text field) and bind it to the data object
        TextFieldBinding textFieldBinding = TextFieldBinding.inflate(inflater, null,false);
        textFieldBinding.setElementTextField(elementTextField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = textFieldBinding.getRoot();
        linearLayoutBody.addView(newView, linearLayoutBody.getChildCount());
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddField");
    }

    // TODO: rework when GUI is sorted
    // Add a new page
    public void addPage() {
        //get index for new current page
        int index;
        if (currentPage != null){
            index = currentPage.getIndex() + 1;
        } else {
            index = 0;
        }
        //add page to template
        currentPage = new TemplatePage(index);
        templateExample.addPage(currentPage);
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View newView = inflater.inflate(R.layout.new_page, null);
        //Add ScrollView to the list so we keep track of the pages for printing
        pageViews.add(newView.findViewById(R.id.scrollViewPage));
        //Add new page above the 3 buttons
        linearLayoutPdf.addView(newView, linearLayoutPdf.getChildCount()-3);
        //focus on new page
        linearLayoutBody = newView.findViewById(R.id.linearLayoutBody);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddPage");
    }

    //Print the scrollView that holds the linearLayoutBody
    public void printPdf(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("print_job_name", new ViewPrintAdapter(this, pageViews), null);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "printPdf");
    }

    //Saves templateExample//
    public void saveTemplate(String Filename){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "savingTemplate");
        blueprint = templateExample.createBlueprint();
        FileManager.createTemplate(Filename, blueprint);
    }

    //retrieve bp from uri
    public void retrieveBlueprint(Uri fileUri) {
        Context context = App.getContext();
        blueprint = "";
        if(fileUri.getPath() != null){
            LogManager.reportStatus(context, "INSPECTOR", "filePath=" + fileUri.getPath());
        }
        try (Scanner scanner = new Scanner(new File(fileUri.getPath()))) {
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                blueprint += currentLine;
                if (scanner.hasNextLine()) {
                    blueprint += "\n";
                }
            }
            LogManager.reportStatus(context, "INSPECTOR", "retrievedBlueprintFromFile");
            loadString(blueprint);
        } catch(FileNotFoundException e){
            context = App.getContext();
            LogManager.reportStatus(context, "INSPECTOR", "retrieveBlueprint:FileNotFound");
            e.printStackTrace();
        }
    }


    public void loadString(String blueprint){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "loading");
        Scanner scanner = new Scanner(blueprint);
        String splitBy = ",";
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            if (currentLine.matches("\\d+")){
                LogManager.reportStatus(context, "INSPECTOR", "addingPage");
                addPage();
            }
            //else it is an element
            else {
                //element[0] is the type, following values are specific to the element
                String[] element = currentLine.split(splitBy);
                if (Integer.valueOf(element[0]) == 1){
                    LogManager.reportStatus(context, "INSPECTOR", "addingTextField");
                    addTextField(element[1],element[2]);
                }
                else if (Integer.valueOf(element[0]) == 2) {

                }
                else{
                    //ignore
                }
            }
        }
        LogManager.reportStatus(context, "INSPECTOR", "finishedLoading");
    }

    public void onSave(View view){
        saveTemplate("temp.txt");
    }

    public void onBack(View view){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "Back");
        finish();
    }
}

