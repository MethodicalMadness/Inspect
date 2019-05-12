package com.example.inspect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.lang.reflect.Array;
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

        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.newTemplateLayout);

        //set up back btn
        configureBackBtn();

        //Add button to list
        Button btnName = (Button) findViewById(R.id.btnAddName);
        buttonList.add(new TemplateButtons("Template Name", "Name Template", btnName, index));
        Button newBtn = new Button(this);
        setUpListeners(index, newBtn);
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
    public void setUpListeners(final int index, final Button currentBtn){
        /*final int currentIndex = index;
        //Create an onClickListener for button at this index
        //eventually set this code up to only occur is input was received from next view

        Button currentBtn = buttonList.get(currentIndex).getBtnReference();
        currentBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Don't think this is right
                int newIndex = currentIndex + 1;
                Button newBtn = new Button(null);
                buttonList.add(new TemplateButtons("Add New Module", "Add New Module", newBtn, newIndex));
                addButton(newIndex);
            }
        });*/

        final int newIndex = index + 1;

        buttonList.get(index).getBtnReference().setOnClickListener(new View.OnClickListener() {
            public void onClick(View layout) {
                Button newBtn = currentBtn;
                buttonList.add(new TemplateButtons("Add New Module", "Add New Module", newBtn, newIndex));
                addButton(newIndex);
            }
        });
    }

    public void addButton(int newIndex){
        //Constraint Layout
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.newTemplateLayout);
        ConstraintSet set = new ConstraintSet();
        set.clone(layout);
        //add button to view
        Button currentBtn = buttonList.get(newIndex).getBtnReference();
        int oldIndex = newIndex - 1;
        Button previousBtn = buttonList.get(oldIndex).getBtnReference();
        layout.addView(currentBtn);

        set.connect(currentBtn.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
        set.connect(currentBtn.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT, 16);
        set.connect(currentBtn.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,16);
        set.constrainHeight(currentBtn.getId(), ConstraintSet.WRAP_CONTENT);
        set.applyTo(layout);

    }

}
