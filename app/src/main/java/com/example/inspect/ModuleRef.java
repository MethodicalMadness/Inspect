package com.example.inspect;

import android.widget.Button;

public class ModuleRef {
    String moduleName;
    String textOnBtn;
    Button btnReference;

    //constructors
    public ModuleRef(){
        moduleName = "";
        textOnBtn = "Add New Module";
        btnReference = null;
    }

    public ModuleRef(String newModuleName, String newText, Button newBtn){
        moduleName = newModuleName;
        textOnBtn = newText;
        btnReference = newBtn;
        setNameForButton();
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

    public void setNameForButton(){
        btnReference.setText(textOnBtn);
    }
}
