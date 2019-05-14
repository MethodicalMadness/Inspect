package com.example.inspect;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoadInspection extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspection_menu);
        configureBackBtn();
        Context context = App.getContext();
        LogManager.reportStatus(context, "LOADINSPECTION", "onCreate");
    }

    public void configureBackBtn(){
        Button backBtn = (Button)findViewById(R.id.btnBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Context context = App.getContext();
        LogManager.reportStatus(context, "LOADINSPECTION", "configureBackBtn");
    }
}
