package com.example.inspect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TemplateMenu extends AppCompatActivity {
    int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_menu);
        //set up back btn
        configureBackBtn();
        //Create Array to display buttons
        ArrayList<TemplateButtons> buttonList = new ArrayList<TemplateButtons>();
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

}
