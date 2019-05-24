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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_menu);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.paddingContainer);

        //set up back btn
        configureBackBtn();

        //configure button

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


}
