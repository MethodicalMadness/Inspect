package com.example.inspect;

import android.view.View;
import android.widget.Button;

import java.lang.reflect.Method;

public class TemplateButtons{
    private String moduleName;
    private String textOnBtn;
    private int indexForBtn;
    private Button btnReference;


    //constructors
    public TemplateButtons(){
        moduleName = "";
        textOnBtn = "Add New Module";
        btnReference = null;
        indexForBtn = 0;
    }

    public TemplateButtons(String newModuleName, String newText, Button newBtn, int newIndex){
        moduleName = newModuleName;
        textOnBtn = newText;
        btnReference = newBtn;
        indexForBtn = newIndex;
        setNameForButton();
        newBtn.setId(newIndex);
    }

    //getters and setters

    public String getModuleName() {
        return moduleName;

    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getTextOnBtn() {
        return textOnBtn;
    }

    public void setTextOnBtn(String textOnBtn) {
        this.textOnBtn = textOnBtn;
    }

    public Button getBtnReference() {
        return btnReference;
    }

    public void setBtnReference(Button btnReference) {
        this.btnReference = btnReference;
    }

    public int getIndexForBtn() {
        return indexForBtn;
    }

    public void setIndexForBtn(int indexForBtn) {
        this.indexForBtn = indexForBtn;
    }

    //Methods
    public void setNameForButton(){
        this.btnReference.setText(textOnBtn);
    }

}
