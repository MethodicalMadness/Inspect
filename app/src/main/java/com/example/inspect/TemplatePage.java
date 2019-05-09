package com.example.inspect;

import android.view.View;

import java.util.ArrayList;

public class TemplatePage {

    private ArrayList<TemplateElement> ElementList = new ArrayList<>();

    // constructor
    public TemplatePage() {

    }

    // add element to this page
    public void addElement(TemplateElement element){
        ElementList.add(element);
    }

    // remove an existing element from this page
    public void removeElement(int index){
        ElementList.remove(index);
    }

}