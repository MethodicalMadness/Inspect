package com.example.inspect;

import android.text.Layout;


public class ElementTextField extends TemplateElement{

    private String label;
    private String fill;

    public ElementTextField(String label, String fill) {
        this.label = label;
        this.fill = fill;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }
}
