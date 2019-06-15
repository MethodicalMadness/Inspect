package com.example.inspect;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Bindable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.inspect.databinding.HeadingFieldBinding;
import com.example.inspect.databinding.ParagraphFieldBinding;
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
    // default value for testing
    private String blueprint = "0\n" +
            "3,Inspection\n" +
            "4,Spacer\n" +
            "1,Inspector,Name\n" +
            "1,Employee ID,XXX-XXX\n" +
            "4,Spacer\n" +
            "3,Address\n" +
            "1,Street,*\n" +
            "1,Suburb,*\n" +
            "1,State,*\n" +
            "1,Postcode,XXXX\n" +
            "4,Spacer\n" +
            "2,Details,Description of problem\n";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = App.getContext();
        setContentView(R.layout.inspection_loaded);
        linearLayoutPdf = findViewById(R.id.linearLayoutPdf);
        linearLayoutBody = findViewById(R.id.linearLayoutBody);
        Intent intent = this.getIntent();
        blueprint = intent.getExtras().getString("blueprint");
        loadString(blueprint);
        LogManager.reportStatus(context, "INSPECTOR", "onCreate");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        blueprint = templateExample.createBlueprint();
        Intent intent = this.getIntent();
        intent.putExtra("blueprint", blueprint);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "savedInstance");
    }

    // Add the field
    public void addHeadingField(String label) {
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementHeadingField elementHeadingField = new ElementHeadingField(label);
        //add element data object to page
        currentPage.addElement(elementHeadingField);
        //we need to instantiate our xml fragment (text field) and bind it to the data object
        HeadingFieldBinding headingFieldBinding = HeadingFieldBinding.inflate(inflater, null,false);
        headingFieldBinding.setElementHeadingField(elementHeadingField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = headingFieldBinding.getRoot();
        linearLayoutBody.addView(newView, linearLayoutBody.getChildCount());
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddHeadingField");
    }

    // Add the field
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
        LogManager.reportStatus(context, "INSPECTOR", "onTextField");
    }

    // Add the field
    public void addParagraphField(String label, String fill) {
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementParagraphField elementParagraphField = new ElementParagraphField(label, fill);
        //add element data object to page
        currentPage.addElement(elementParagraphField);
        //we need to instantiate our xml fragment (text field) and bind it to the data object
        ParagraphFieldBinding paragraphFieldBinding = ParagraphFieldBinding.inflate(inflater, null,false);
        paragraphFieldBinding.setElementParagraghField(elementParagraphField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = paragraphFieldBinding.getRoot();
        linearLayoutBody.addView(newView, linearLayoutBody.getChildCount());
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddParagraphField");
    }

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

    //add the field
    public void addSpacer(){
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementSpacerField elementSpacerField = new ElementSpacerField();
        //add element data object to page
        currentPage.addElement(elementSpacerField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = inflater.inflate(R.layout.spacer_field, null);
        linearLayoutBody.addView(newView, linearLayoutBody.getChildCount());
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddSpacerField");
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
                //add text field
                if (Integer.valueOf(element[0]) == 1){
                    LogManager.reportStatus(context, "INSPECTOR", "addingTextField");
                    addTextField(element[1],element[2]);
                }
                //add paragraph
                else if (Integer.valueOf(element[0]) == 2) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingParaField");
                    addParagraphField(element[1],element[2]);
                }
                //add heading
                else if (Integer.valueOf(element[0]) == 3) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingHeadingField");
                    addHeadingField(element[1]);
                }
                //add spacer
                else if (Integer.valueOf(element[0]) == 4) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingSpacerField");
                    addSpacer();
                }
                //add photo
                else if (Integer.valueOf(element[0]) == 5) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingPhotoField");
                }
                //shouldn't get here ever
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

