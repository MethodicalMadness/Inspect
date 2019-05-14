package com.example.inspect;

import androidx.databinding.ObservableField;


public class ElementTextField extends TemplateElement{

    private final ObservableField<String> label = new ObservableField<>();
    private final ObservableField<String> fill = new ObservableField<>();

    public ElementTextField(String label, String fill) {
        this.label.set(label);
        this.fill.set(fill);
        setType("1");
    }

    public String getLabel() {
        return this.label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getFill() {
        return this.fill.get();
    }

    public void setFill(String fill) {
        this.fill.set(fill);
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + getLabel() + "," + getFill();
        return blueprintFragment;
    }
}
