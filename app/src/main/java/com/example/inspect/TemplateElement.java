package com.example.inspect;


import androidx.databinding.BaseObservable;

public abstract class TemplateElement extends BaseObservable {


    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //deconstructs the element (view) and returns the blueprints for saving
    public abstract String deconstructElement();
}
