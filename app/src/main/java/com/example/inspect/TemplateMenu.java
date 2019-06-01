package com.example.inspect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class TemplateMenu extends AppCompatActivity {

    private LinearLayout fragmentContainer2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_menu);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.paddingContainer);

        //set up back btn
        configureBackBtn();

        //parent container for content
        fragmentContainer2 = (LinearLayout) findViewById(R.id.fragmentContainer2);

        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEMENU", "onCreate");
    }

    //Functionality for back button
    public void configureBackBtn(){
        Button backBtn = (Button)findViewById(R.id.btnTemplateBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATEMENU", "configureBackBtn");
    }

    public void configureEditNameButton(){
        //TODO: Create pop up that allows user to add a name to the template

    }

    public void configureResetBtn(View v){
        Button resetBtn = (Button)findViewById(R.id.btnTemplateReset);
        Context context = this;

        //create new alert with message
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(context);
        aBuilder.setMessage("Do you want to reset the template?");
        aBuilder.setCancelable(true);

        //positive button on alert - resets view
        aBuilder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //NOTE: this may not work when we start saving states
                        recreate();
                    }
        });

        //negative button on alert - does nothing
        aBuilder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        //shows the alert
        AlertDialog alertPopUp = aBuilder.create();
        alertPopUp.show();
    }

    public void onAddField(View v) {

        //TODO: get input from the module menu
        //TODO: save input and display it
        //TODO: remove item from list
        //add element to list
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View fragment = inflater.inflate(R.layout.fragement_template_module, null);
        fragmentContainer2.addView(fragment, fragmentContainer2.getChildCount() - 1);
    }

}
