package com.example.inspect;

import android.content.Context;
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
        Button backBtn = (Button)findViewById(R.id.btnBack);
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

    public void configureResetBtn(){
        Button resetBtn = (Button)findViewById(R.id.btnReset);

        resetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Context context = App.getContext();
                AlertDialogHelper alertReset = new AlertDialogHelper(context, null, "Are you sure you want to reset the template?", "Yes", "Cancel");
                alertReset.run(alertReset);
            }
        });
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
