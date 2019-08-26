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
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.inspect.databinding.HeadingFieldBinding;
import com.example.inspect.databinding.ParagraphFieldBinding;
import com.example.inspect.databinding.TextFieldBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private boolean isLoading = false;
    private String blueprint;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = App.getContext();
        setContentView(R.layout.inspection_loaded);
        linearLayoutPdf = findViewById(R.id.linearLayoutPdf);
        Intent intent = this.getIntent();
        //get image from intent
        if (intent.hasExtra("imageUriString")) {
            imageUriString = intent.getExtras().getString("imageUriString");
            LogManager.reportStatus(context, "INSPECTOR", "imageUriStringRetrieved");
        }
        //get filename from intent
        if (intent.hasExtra("filename")) {
            filename = intent.getExtras().getString("filename");
            TextView docTitle = findViewById(R.id.filename);
            docTitle.setText(FileManager.removeExtension(filename));
        }
        //get blueprint from intent
        if (intent.hasExtra("blueprint")) {
            blueprint = intent.getExtras().getString("blueprint");
            isLoading = true;
            loadString(blueprint);
            isLoading = false;
        //if no page exists if no page exists
        }else{
            addPage();
        }
        //get mode from intent
        if(intent.hasExtra("isInspecting")){
            isInspecting = intent.getExtras().getBoolean("isInspecting");
        }
        //template editor needs editing tools
        ViewGroup vg = this.findViewById(R.id.linearLayoutBody);
        if (isInspecting) {
            LogManager.reportStatus(context, "INSPECTOR", "searching for tools");
            ArrayList<View> views = getViewsByTag(vg, "editor");
            for (View v : views) {
                v.setVisibility(View.GONE);
                LogManager.reportStatus(context, "INSPECTOR", "editor tool hidden");
            }
        }

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
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     * @param label
     */
    public void addHeadingField(String label, int index) {
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementHeadingField elementHeadingField = new ElementHeadingField(label);
        //insert element data object in slot on page
        if (isLoading){
            currentPage.addElement(elementHeadingField);
        }else{
            currentPage.insertElement(index, elementHeadingField);
        }
        //we need to instantiate our xml fragment (text field) and bind it to the data object
        HeadingFieldBinding headingFieldBinding = HeadingFieldBinding.inflate(inflater, null,false);
        headingFieldBinding.setElementHeadingField(elementHeadingField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = headingFieldBinding.getRoot();
        if (isInspecting){
            EditText editText = newView.findViewById(R.id.label);
            editText.setFocusable(false);
        }
        linearLayoutBody.addView(newView, index);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddHeadingField");
    }



    /**
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     * @param label
     * @param fill
     */
    public void addTextField(String label, String fill, int index) {
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementTextField elementTextField = new ElementTextField(label, fill);
        //insert element data object in slot on page
        if (isLoading){
            currentPage.addElement(elementTextField);
        }else{
            currentPage.insertElement(index, elementTextField);
        }
        //we need to instantiate our xml fragment (text field) and bind it to the data object
        TextFieldBinding textFieldBinding = TextFieldBinding.inflate(inflater, null,false);
        textFieldBinding.setElementTextField(elementTextField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = textFieldBinding.getRoot();
        if (isInspecting){
            EditText editText = newView.findViewById(R.id.label);
            editText.setFocusable(false);
        }
        linearLayoutBody.addView(newView, index);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onTextField");
    }

    /**
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     * @param label
     * @param fill
     */
    public void addParagraphField(String label, String fill, int index) {
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementParagraphField elementParagraphField = new ElementParagraphField(label, fill);
        //insert element data object in slot on page
        if (isLoading){
            currentPage.addElement(elementParagraphField);
        }else{
            currentPage.insertElement(index, elementParagraphField);
        }
        //we need to instantiate our xml fragment (text field) and bind it to the data object
        ParagraphFieldBinding paragraphFieldBinding = ParagraphFieldBinding.inflate(inflater, null,false);
        paragraphFieldBinding.setElementParagraghField(elementParagraphField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = paragraphFieldBinding.getRoot();
        if (isInspecting){
            EditText editText = newView.findViewById(R.id.label);
            editText.setFocusable(false);
        }
        linearLayoutBody.addView(newView, index);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddParagraphField");
    }

    /**
     * Adds a page to the view hierarchy and a TemplatePage to the Template.
     */
    public void addPage() {
        Context context = App.getContext();
        //get index for new current page
        int index = 0;
        if (currentPage != null) {
            index = currentPage.getIndex() + 1;
        }
        if(currentPage == null || !currentPage.isPageEmpty()){
            //add page to template
            currentPage = new TemplatePage(index);
            currentTemplate.addPage(currentPage);
            //inflater needed to "inflate" layouts
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View newView = inflater.inflate(R.layout.new_page, null);
            //Add page to the list so we keep track of the pages for printing
            pageViews.add(newView.findViewById(R.id.linearLayoutBody));
            //Add new page under last
            linearLayoutPdf.addView(newView, linearLayoutPdf.getChildCount());
            //focus on new page
            linearLayoutBody = newView.findViewById(R.id.linearLayoutBody);
            //Number the page
            linearLayoutBody.setTag(currentPage);
            TextView footer = linearLayoutBody.findViewById(R.id.footer);
            footer.setText("Page " + (index + 1));
            //log
            LogManager.reportStatus(context, "INSPECTOR", "onAddPage");
            //add slots
            if(!isLoading){
                isLoading = true;
                int slot = 16;
                while (slot > 0){
                    addSpacer(0);
                    slot = slot - 1;
                }
                isLoading = false;
            }
        } else{
            //do nothing, current page is empty
            LogManager.reportStatus(context, "INSPECTOR", "pageNotAdded:PageEmpty");
        }
    }

    /**
     * Adds a spacer to the view hierarchy and a ElementSpacerField to the TemplatePage.
     */
    public void addSpacer(int index){
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementSpacerField elementSpacerField = new ElementSpacerField();
        //if loading, add element data object to page, else we are editing and insert it
        if(isLoading) {
            currentPage.addElement(elementSpacerField);
        }else{
            currentPage.insertElement(index, elementSpacerField);
        }
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = inflater.inflate(R.layout.spacer_field, null);
        linearLayoutBody.addView(newView, index);
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "onAddSpacerField");
    }

    /**
     * Adds the field to the TemplatePage, binds the data to a view, then adds view to existing hierarchy.
     */
    public void addImageField(int index) {
        Context context = App.getContext();
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementImageField elementImageField = new ElementImageField();
        //insert element data object in slot on page
        if (isLoading){
            currentPage.addElement(elementImageField);
        }else{
            currentPage.insertElement(index, elementImageField);
        }
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = inflater.inflate(R.layout.image_field, null);
        linearLayoutBody.addView(newView, index);
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
     * Deletes the supplied view, returns the index it was located at
     * @param view
     * @return
     */
    public int deleteView(View view) {
        LinearLayout parentPage = (LinearLayout) view.getParent().getParent().getParent();
        LinearLayout slot = (LinearLayout) view.getParent().getParent();
        int index = parentPage.indexOfChild(slot);
        parentPage.removeViewAt(index);
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "onDelete");
        return index;
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
     * @param filename
     */
    public void saveTemplate(String filename){
        Context context = App.getContext();
        LogManager.reportStatus(context, "INSPECTOR", "savingTemplate");
        blueprint = currentTemplate.createBlueprint();
        //make sure we have correct extension
        filename = FileManager.removeExtension(filename);
        if (isInspecting){
            filename = filename + ".in";
        } else {
            filename = filename + ".bp";
        }
        boolean hasSaved = FileManager.createTemplate(filename, blueprint);
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
        int index = 0;
        Scanner scanner = new Scanner(blueprint);
        String splitBy = ",";
        while (scanner.hasNextLine()) {
            String currentLine = scanner.nextLine();
            //if it is just a number by itself, it is a page.
            if (currentLine.matches("\\d+")){
                LogManager.reportStatus(context, "INSPECTOR", "addingPage");
                addPage();
                index = 0;
            }
            //else it is an element
            else {
                //element[0] is the type, following values are specific to the element
                String[] element = currentLine.split(splitBy);
                //add text field
                if (Integer.valueOf(element[0]) == 1){
                    LogManager.reportStatus(context, "INSPECTOR", "addingTextField");
                    if (element.length == 3 ) {
                        addTextField(element[1], element[2], index);
                    } else if (element.length == 2 ) {
                        addTextField(element[1], "", index);
                    } else{
                        addTextField("", "", index);
                    }
                    index = index + 1;
                }
                //add paragraph
                else if (Integer.valueOf(element[0]) == 2) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingParaField");
                    if (element.length == 3 ) {
                        addParagraphField(element[1], element[2], index);
                    } else if (element.length == 2 ) {
                        addParagraphField(element[1], "", index);
                    } else {
                        addParagraphField("","", index);
                    }
                    index = index + 1;
                }
                //add heading
                else if (Integer.valueOf(element[0]) == 3) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingHeadingField");
                    if (element.length == 2 ) {
                        addHeadingField(element[1], index);
                    } else {
                        addHeadingField("", index);
                    }
                    index = index + 1;
                }
                //add spacer
                else if (Integer.valueOf(element[0]) == 4) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingSpacerField");
                    addSpacer(index);
                    index = index + 1;
                }
                //add photo
                else if (Integer.valueOf(element[0]) == 5) {
                    LogManager.reportStatus(context, "INSPECTOR", "addingImageField");
                    addImageField(index);
                    index = index + 1;
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
        view.setVisibility(View.GONE);
        Toast.makeText(this, "Page Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addTextField()
     * @param view
     */
    public void onAddText(View view){
        setPage(view);
        int index = deleteView(view);
        addTextField("label:", "", index);
        Toast.makeText(this, "Short Query Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addHeadingField()
     * @param view
     */
    public void onAddHeading(View view){
        setPage(view);
        int index = deleteView(view);
        addHeadingField("HEADING", index);
        Toast.makeText(this, "Heading Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addParagraphField()
     * @param view
     */
    public void onAddParagraph(View view){
        setPage(view);
        int index = deleteView(view);
        addParagraphField("Question?", "Answer", index);
        Toast.makeText(this, "Long Query Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addImageField()
     * @param view
     */
    public void onAddCamera(View view){
        setPage(view);
        int index = deleteView(view);
        addImageField(index);
        Toast.makeText(this, "Request Camera Added", Toast.LENGTH_LONG).show();
    }

    /**
     * For ui to interact with addSpacer()
     * @param view
     */
    public void onAddSpacer(View view){
        setPage(view);
        int index = deleteView(view);
        addSpacer(index);
        Toast.makeText(this, "Space Added", Toast.LENGTH_LONG).show();
    }

    /**
     * sets page supplied view is on
     * @param view
     */
    public void setPage(View view){
        //page is always the great grand parent
        linearLayoutBody = (LinearLayout) view.getParent().getParent().getParent();
        currentPage = (TemplatePage) linearLayoutBody.getTag();
    }

    /**
     * toggles the edit menu within each slot
     * @param view
     */
    public void editSlotVisibilityToggle(View view){
        //get slot and panel
        LinearLayout slot = (LinearLayout) view.getParent();
        LinearLayout editPanel = (LinearLayout) slot.getChildAt(1);
        FloatingActionButton fab = (FloatingActionButton) view;
        //focus on the page
        linearLayoutBody = (LinearLayout) slot.getParent();
        //toggle visibility
        if (editPanel.getVisibility() == View.VISIBLE){
            editPanel.setVisibility(View.INVISIBLE);
        } else if (editPanel.getVisibility() == View.INVISIBLE){
            editPanel.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Traverses heirarchy searching for views with supplied tag
     * @param root
     * @param tag
     * @return
     */
    private static ArrayList<View> getViewsByTag(ViewGroup root, String tag){
        ArrayList<View> views = new ArrayList<>();
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                views.addAll(getViewsByTag((ViewGroup) child, tag));
            }
            final Object tagObj = child.getTag();
            if (tagObj != null && tagObj.toString().equals(tag)) {
                views.add(child);
            }
        }
        return views;
    }
}

