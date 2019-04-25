package com.example.inspect;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


public class TemplateEditor extends AppCompatActivity{

    private LinearLayout linearLayoutBody;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_editor);
        linearLayoutBody = findViewById(R.id.linearLayoutBody);
    }

    // Add the new field at bottom of layout.
    public void onAddField(View view) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.text_field, null);
        linearLayoutBody.addView(rowView, linearLayoutBody.getChildCount());
    }

    // Remove selected view
    public void onDelete(View view) {
        linearLayoutBody.removeView((View) view.getParent());
    }

    //Print the scrollView that holds the linearLayoutBody
    public void printPDF(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("print_any_view_job_name", new ViewPrintAdapter(this, findViewById(R.id.scrollView)), null);
    }

    //Saves template//
    public static void saveTemplate(){

    }

    //Adds an element to the template//
    public static void addElement() {

    }

    //Adds a module to the template//
    public static void addModule(){

    }

    //Removes an element from the template//
    public static void removeElement(){

    }

    //Removes a module from the template//
    public static void removeModule(){

    }


}
