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
import android.widget.Toast;

import com.example.inspect.databinding.HeadingFieldBinding;
import com.example.inspect.databinding.ParagraphFieldBinding;
import com.example.inspect.databinding.TextFieldBinding;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles constructing templates from supplied blueprints.
 */
public class Inspector extends AppCompatActivity{

    private String filename;
    private boolean isInspecting = false;
    private ImageButton imageButton;
    private String imageUriString;
    private LinearLayout linearLayoutPdf;
    private LinearLayout linearLayoutBody;
    private ArrayList<View> pageViews = new ArrayList<>();
    private TemplatePage currentPage;
    private Template currentTemplate = new Template();
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
        //if no page exists
        if(currentPage == null){
            //add page to template
            addPage();
            //currentPage = new TemplatePage(0);
            //currentTemplate.addPage(currentPage);
        }
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
        //get filename from intent
        if(intent.hasExtra("filename")){
            filename = intent.getExtras().getString("filename");
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
        blueprint = currentTemplate.createBlueprint();
        Intent intent = this.getIntent();
        intent.putExtra("blueprint", blueprint);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "savedInstance");
    }

    /**
     * Adds editing tools to the inspector.
     */
    private void addTools(){
        if (!isInspecting){
            //inflater needed to "inflate" layouts
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newView = inflater.inflate(R.layout.edit_menu, null);
            //Add tools under doc
            linearLayoutPdf.addView(newView, linearLayoutPdf.getChildCount()-3);
        }
    }

    /**
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     * @param label
     */
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

    /**
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     * @param label
     * @param fill
     */
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

    /**
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     * @param label
     * @param fill
     */
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

    /**
     * Adds a page to the view hierarchy and a TemplatePage to the Template.
     */
    public void addPage() {
        Context context = App.getContext();
        int index;
        if (currentPage != null) {
            //get index for new current page
            index = currentPage.getIndex() + 1;
        } else {
            index = 0;
        }
        if(currentPage == null || !currentPage.isPageEmpty()){
            //add page to template
            currentPage = new TemplatePage(index);
            currentTemplate.addPage(currentPage);
            //inflater needed to "inflate" layouts
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newView = inflater.inflate(R.layout.new_page, null);
            //Add ScrollView to the list so we keep track of the pages for printing
            pageViews.add(newView.findViewById(R.id.scrollViewPage));
            //Add new page under last
            int children = 3;
            if (!isInspecting) {
                children = 4;
            }
            linearLayoutPdf.addView(newView, linearLayoutPdf.getChildCount() - children);
            //focus on new page
            linearLayoutBody = newView.findViewById(R.id.linearLayoutBody);
            LogManager.reportStatus(context, "INSPECTOR", "onAddPage");
        } else{
            //do nothing, current page is empty
            LogManager.reportStatus(context, "INSPECTOR", "pageNotAdded:PageEmpty");
        }
    }

    /**
     * Adds a spacer to the view hierarchy and a ElementSpacerField to the TemplatePage.
     */
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

    /**
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     */
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

    /**
     * Starts PhotoManager activity, passing important details through the intent.
     * @param view
     */
    public void openCamera(View view){
        if (isInspecting) {
            Intent intent = new Intent(Inspector.this, PhotoManager.class);
            intent.putExtra("blueprint", blueprint);
            intent.putExtra("isInspecting", isInspecting);
            intent.putExtra("filename", filename);
            startActivity(intent);
        }
    }

    /**
     * Deletes the supplied view
     * @param view
     */
    public void onDelete(View view) {
        linearLayoutBody.removeView((View) view.getParent());
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "onDelete");
    }

    /**
     * Print the scrollView that holds the linearLayoutBody.
     * @param view
     */
    public void printPdf(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("print_job_name", new ViewPrintAdapter(this, pageViews), null);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "printPdf");
    }

    /**
     * Passes blueprint to FileManager for saving.
     * @param Filename
     */
    public void saveTemplate(String Filename){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "savingTemplate");
        blueprint = currentTemplate.createBlueprint();
        boolean hasSaved = FileManager.createTemplate(Filename, blueprint);
        if (hasSaved){
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Did not save", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Parses blueprint and builds layout hierarchy
     * @param blueprint
     */
    public void loadString(String blueprint){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "loading");
        Scanner scanner = new Scanner(blueprint);
        String splitBy = ",";
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            //if it is just a number by itself, it is a page.
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
                    if (element.length == 3 ) {
                        addTextField(element[1], element[2]);
                    } else if (element.length == 2 ) {
                        addTextField(element[1], "");
                    } else{
                        addTextField("", "");
                    }
                }
                //add paragraph
                else if (Integer.valueOf(element[0]) == 2) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingParaField");
                    if (element.length == 3 ) {
                        addParagraphField(element[1], element[2]);
                    } else if (element.length == 2 ) {
                        addParagraphField(element[1], "");
                    } else {
                        addParagraphField("","");
                    }
                }
                //add heading
                else if (Integer.valueOf(element[0]) == 3) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingHeadingField");
                    if (element.length == 2 ) {
                        addHeadingField(element[1]);
                    } else {
                        addHeadingField("");
                    }
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

    /**
     * For ui to interact with saveTemplate().
     * @param view
     */
    public void onSave(View view){
        saveTemplate(filename);
        Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
    }

    /**
     * To go back to MainActivity.
     * @param view
     */
    public void onBack(View view){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "Back");
        finish();
    }

    /**
     * For ui to interact with addPage()
     * @param view
     */
    public void onAddPage(View view){
        addPage();
        Toast.makeText(this, "Page Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addTextField()
     * @param view
     */
    public void onAddText(View view){
        addTextField("label:", "");
        Toast.makeText(this, "Short Query Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addHeadingField()
     * @param view
     */
    public void onAddHeading(View view){
        addHeadingField("HEADING");
        Toast.makeText(this, "Heading Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addParagraphField()
     * @param view
     */
    public void onAddParagraph(View view){
        addParagraphField("Question?", "Answer");
        Toast.makeText(this, "Long Query Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addImageField()
     * @param view
     */
    public void onAddCamera(View view){
        addImageField();
        Toast.makeText(this, "Request Camera Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addSpacer()
     * @param view
     */
    public void onAddSpacer(View view){
        addSpacer();
        Toast.makeText(this, "Space Added", Toast.LENGTH_LONG).show();
    }
}

