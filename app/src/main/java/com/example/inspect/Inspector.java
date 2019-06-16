package com.example.inspect;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.inspect.databinding.HeadingFieldBinding;
import com.example.inspect.databinding.ParagraphFieldBinding;
import com.example.inspect.databinding.TextFieldBinding;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Inspector extends AppCompatActivity{

    private boolean isInspecting = false;
    private ImageButton imageButton;
    private String imageUriString;
    private LinearLayout linearLayoutPdf;
    private LinearLayout linearLayoutBody;
    private ArrayList<View> pageViews = new ArrayList<>();
    private TemplatePage currentPage;
    private TemplateExample templateExample = new TemplateExample(null);
    private String blueprint = "0\n";
    /*
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
            "2,Details,Description of problem\n" +
            "1\n" +
            "5,Image\n";
            */


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = App.getContext();
        setContentView(R.layout.inspection_loaded);
        linearLayoutPdf = findViewById(R.id.linearLayoutPdf);
        linearLayoutBody = findViewById(R.id.linearLayoutBody);
        Intent intent = this.getIntent();
        //get blueprint from intent
        if(intent.hasExtra("blueprint")) {
            blueprint = intent.getExtras().getString("blueprint");
        }
        //get image from intent
        if(intent.hasExtra("imageUriString")) {
            imageUriString = intent.getExtras().getString("imageUriString");
            LogManager.reportStatus(context, "INSPECTOR", "imageUriStringRetrieved");
        }
        //get mode from intent
        if(intent.hasExtra("isInspecting")){
            isInspecting = intent.getExtras().getBoolean("isInspecting");
        }
        //template editor needs editing tools
        addTools();
        //load bp
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

    // add editing tools
    private void addTools(){
        if (!isInspecting){
            //inflater needed to "inflate" layouts
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newView = inflater.inflate(R.layout.edit_menu, null);
            //Add tools under doc
            linearLayoutPdf.addView(newView, linearLayoutPdf.getChildCount()-3);
        }
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
        if (isInspecting){
            EditText editText = newView.findViewById(R.id.label);
            editText.setFocusable(false);
        }
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
        if (isInspecting){
            EditText editText = newView.findViewById(R.id.label);
            editText.setFocusable(false);
        }
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
        if (isInspecting){
            EditText editText = newView.findViewById(R.id.label);
            editText.setFocusable(false);
        }
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
        //Add new page under last
        int children = 3;
        if(!isInspecting){
            children = 4;
        }
        linearLayoutPdf.addView(newView, linearLayoutPdf.getChildCount()-children);
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

    // Add the field
    public void addImageField() {
        Context context = App.getContext();
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementImageField elementImageField = new ElementImageField();
        //add element data object to page
        currentPage.addElement(elementImageField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = inflater.inflate(R.layout.image_field, null);
        linearLayoutBody.addView(newView, linearLayoutBody.getChildCount());
        //set listener
        imageButton = (ImageButton)findViewById(R.id.camera_button);
        //set image if we have one defined
        if (imageUriString != null){
            Uri uri = Uri.parse(imageUriString);
            LogManager.reportStatus(context, "INSPECTOR", "parsingImageUri");
            try{
                InputStream inputStream = this.getContentResolver().openInputStream(uri);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                Bitmap preview_bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                Drawable image = new BitmapDrawable(getResources(),preview_bitmap);
                imageButton.setBackground(image);
                LogManager.reportStatus(context, "INSPECTOR", "imageSet");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                LogManager.reportStatus(context, "INSPECTOR", "imageNotFound");
            }
        }else{
            LogManager.reportStatus(context, "INSPECTOR", "imageUriStringIsEmpty");
        }
        LogManager.reportStatus(context, "INSPECTOR", "onAddImageField");
    }

    public void openCamera(View view){
        Intent intent = new Intent(Inspector.this, PhotoManager.class);
        intent.putExtra("blueprint", blueprint);
        intent.putExtra("isInspecting", isInspecting);
        saveTemplate("temp.txt");
        startActivity(intent);
    }

    // Remove selected view
    public void onDelete(View view) {
        linearLayoutBody.removeView((View) view.getParent());
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "onDelete");
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

    //parse blueprint and build layout hierarchy
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
                    LogManager.reportStatus(context, "INSPECTOR", "addingImageField");
                    addImageField();
                }
                //shouldn't get here ever
                else{
                    //ignore
                }
            }
        }
        LogManager.reportStatus(context, "INSPECTOR", "finishedLoading");
    }

    //button click on save
    public void onSave(View view){
        saveTemplate("temp.txt");
    }

    //button click on back
    public void onBack(View view){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "Back");
        finish();
    }

    public void onAddPage(View view){
        addPage();
    }

    public void onAddText(View view){
        addTextField("label:", "");
    }

    public void onAddHeading(View view){
        addHeadingField("HEADING");
    }

    public void onAddParagraph(View view){
        addParagraphField("Question?", "Answer");
    }

    public void onAddCamera(View view){
        addImageField();
    }

    public void onAddSpacer(View view){
        addSpacer();
    }
}

