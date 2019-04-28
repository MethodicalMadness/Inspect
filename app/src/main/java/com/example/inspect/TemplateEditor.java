package com.example.inspect;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.ArrayList;


public class TemplateEditor extends AppCompatActivity{

    //private LinearLayout parentLinearLayout;
    private LinearLayout linearLayoutPdf;
    private LinearLayout linearLayoutBody;
    private ArrayList<View> views  = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_editor);
        //parentLinearLayout = findViewById(R.id.parent_linear_layout);
        linearLayoutPdf = findViewById(R.id.linearLayoutPdf);
        linearLayoutBody = findViewById(R.id.linearLayoutBody);
        views.add(findViewById(R.id.scrollViewPage));
    }

    // Add the new field at bottom of layout.
    public void onAddField(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.text_field, null);
        linearLayoutBody.addView(rowView, linearLayoutBody.getChildCount());
    }

    // Add the new page under previous.
    public void onAddPage(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View newView = inflater.inflate(R.layout.new_page, null);
        //Add ScrollView to the list so we keep track of the pages for printing
        views.add(newView.findViewById(R.id.scrollViewPage));
        //Add new page above the 3 buttons
        linearLayoutPdf.addView(newView, linearLayoutPdf.getChildCount()-3);
        //focus on new page
        linearLayoutBody = newView.findViewById(R.id.linearLayoutBody);
    }

    // Remove selected view
    public void onDelete(View view) {
        linearLayoutBody.removeView((View) view.getParent());
    }

    //Print the scrollView that holds the linearLayoutBody
    public void printPDF(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("print_job_name", new ViewPrintAdapter(this, views), null);
    }

    //Saves template//
    public static void saveTemplate(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Adds an element to the template//
    public static void addElement() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");

    }

    //Adds a module to the template//
    public static void addModule(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");

    }

    //Removes an element from the template//
    public static void removeElement(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }

    //Removes a module from the template//
    public static void removeModule(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
    }


}
