package com.example.inspect;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.content.Context;
import java.util.ArrayList;

public class TemplateMenu extends AppCompatActivity {

    ArrayList<ModuleInfo> moduleInfoList = new ArrayList<ModuleInfo>();
    int index = 0;
    ArrayList<View> views = new ArrayList<>();
    private ConstraintLayout fragContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.template_menu);

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.templateContainer);

        //set up back btn
        configureBackBtn();

        //configure button
        Button btnName = (Button) findViewById(R.id.btnAddName);
        moduleInfoList.add(new ModuleInfo("Name Template",index));
        btnName.setText(moduleInfoList.get(index).getModuleName());
        fragContainer = (ConstraintLayout) findViewById(R.id.fragContainer);
      
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

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View moduleButton = inflater.inflate(R.layout.fragment_template_button, null);


        //Constraint Layout
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.fragContainer);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);

        fragContainer.addView(moduleButton);

        set.connect(fragContainer.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
        set.connect(fragContainer.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT, 16);
        set.connect(fragContainer.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,16);
        set.constrainHeight(fragContainer.getId(), ConstraintSet.WRAP_CONTENT);
        set.applyTo(layout);
    }

    public void onDelete(View v) {
        fragContainer.removeView((View) v.getParent());
    }


}
