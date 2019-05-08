package com.example.inspect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TemplateMenu extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_menu);
        //set up back btn
        configureBackBtn();
        //Create Array to display buttons
        ArrayList<ModuleRef> buttonList = new ArrayList<ModuleRef>();
        //Add button to list
        Button btnName = (Button) findViewById(R.id.btnAddName);
        buttonList.add(new ModuleRef("Template Name", "Name Template", btnName));
        btnName.setText(buttonList.get(0).getTextOnBtn());
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
