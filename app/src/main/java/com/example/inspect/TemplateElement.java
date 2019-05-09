package com.example.inspect;


import android.text.Layout;
import android.view.View;


public abstract class TemplateElement {


    private String type;
    private Layout element;


    public void setElement(Layout element){
        this.element = element;
    }

    public Layout getElement(){
        return element;
    }

    //deconstructs the element (view) and returns the blueprints for saving
    public String deconstructElement(){
        String blueprint = "";
        return blueprint;
    }

    //reconstructs element from the blueprints for loading
    public void reconstructElement(String blueprint){

    }
}
