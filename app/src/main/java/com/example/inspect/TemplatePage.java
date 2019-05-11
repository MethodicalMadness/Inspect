package com.example.inspect;

import java.util.ArrayList;


public class TemplatePage {

    private ArrayList<TemplateElement> elements = new ArrayList<>();
    private int index;

    // constructor
    public TemplatePage(int index) {
        this.index = index;
    }

    public ArrayList<TemplateElement> getElements() {
        return elements;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    // add element to this page
    public void addElement(TemplateElement element){
        elements.add(element);
    }

    // remove an existing element from this page
    public void removeElement(int index){
        elements.remove(index);
    }
}