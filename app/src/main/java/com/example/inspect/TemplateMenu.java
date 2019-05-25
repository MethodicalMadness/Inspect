package com.example.inspect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class TemplateMenu extends AppCompatActivity {

    private ConstraintLayout fragmentContainer2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_menu);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.paddingContainer);

        //set up back btn
        configureBackBtn();

        //parent container for content
        fragmentContainer2 = (ConstraintLayout) findViewById(R.id.fragmentContainer2);

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

    public void editButton(){
        //TODO: Create pop up that allows user to add a name to the template
    }

    public void onAddField(View v) {
        //TODO: add fragment into the container
        //TODO: get input from the module menu
        //TODO: save input and display it
        //TODO: remove item from list

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View fragment = inflater.inflate(R.layout.fragement_template_module, null);
        // Add the new row before the add field button.
        fragmentContainer2.addView(fragment, fragmentContainer2.getChildCount() - 1);
    }

}
