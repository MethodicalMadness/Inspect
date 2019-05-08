package com.example.inspect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TemplateMenu extends AppCompatActivity {
    //variables
    int index = 0;
    //Create Array to display buttons
    ArrayList<TemplateButtons> buttonList = new ArrayList<TemplateButtons>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_menu);
        //set up back btn
        configureBackBtn();

        //Add button to list
        Button btnName = (Button) findViewById(R.id.btnAddName);
        buttonList.add(new TemplateButtons("Template Name", "Name Template", btnName, index));
        index++;
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
    }

    //Create onClickListeners to add another button when current is clicked
    public void setUpListeners(final int atIndex){
        //Create an onClickListener for button at this index
        buttonList.get(atIndex).getBtnReference().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Don't think this is right
                Button newBtn = new Button(null);
                buttonList.add(new TemplateButtons("Add New Module", "Add New Module", newBtn, atIndex+1));

            }
        });
    }

}
