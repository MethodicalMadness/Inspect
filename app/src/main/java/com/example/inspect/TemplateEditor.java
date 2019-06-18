package com.example.inspect;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.inspect.databinding.TextFieldBinding;
import java.util.ArrayList;


public class TemplateEditor extends AppCompatActivity{

    private LinearLayout linearLayoutPdf;
    private LinearLayout linearLayoutBody;
    private ArrayList<View> pageViews = new ArrayList<>();
    private TemplatePage currentPage = new TemplatePage(0);
    private TemplateExample templateExample = new TemplateExample(currentPage);


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_editor);
        linearLayoutPdf = findViewById(R.id.linearLayoutPdf);
        linearLayoutBody = findViewById(R.id.linearLayoutBody);
        if(pageViews.size() == 0){
            onAddPage(null);
        }
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "onCreate");
    }

    public void onAddHeading(View view){

    }

    // TODO: rework when GUI is sorted
    // Add the new field at bottom of layout.
    public void onAddField(View view) {
        //inflater needed to "inflate" layouts
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //instantiate our data object.
        ElementTextField elementTextField = new ElementTextField("New Label: ", "User filled data");
        //add element data object to page
        currentPage.addElement(elementTextField);
        //we need to instantiate our xml fragment (text field) and bind it to the data object
        TextFieldBinding textFieldBinding = TextFieldBinding.inflate(inflater, null,false);
        textFieldBinding.setElementTextField(elementTextField);
        //get new view (our xml fragment -the text field) and add it to current view
        View newView = textFieldBinding.getRoot();
        linearLayoutBody.addView(newView, linearLayoutBody.getChildCount());
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "onAddField");
    }

    // TODO: rework when GUI is sorted
    // Add the new page under previous.
    public void onAddPage(View view) {
        //get index for new current page
        int index = currentPage.getIndex() + 1;
        currentPage = new TemplatePage(index);
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
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "onAddPage");
    }

    // TODO: remove from page data object, rework when GUI is sorted
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
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "printPdf");
    }

    //Saves templateExample//
    public void saveTemplate(String Filename){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "saveTemplate");
        templateExample.saveState(Filename);
    }

    //Adds an element to the templateExample//
    public static void addElement() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "addElement");

    }

    //Adds a module to the templateExample//
    public static void addModule(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "addModule");

    }

    //Removes an element from the templateExample//
    public static void removeElement(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "removeElement");
    }

    //Removes a module from the templateExample//
    public static void removeModule(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEEDITOR", "removeModule");
    }
}
