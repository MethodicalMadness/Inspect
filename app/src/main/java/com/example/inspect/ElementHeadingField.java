package com.example.inspect;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


public class ElementHeadingField extends TemplateElement {

    private final ObservableField<String> label = new ObservableField<>();

    public ElementHeadingField(String label) {
        this.label.set(label);
        setType("3");
    }

    @Bindable
    public String getLabel() {
        return this.label.get();
    }

    @Bindable
    public void setLabel(String label) {
        if (!this.label.equals(label)){
            this.label.set(label);
            notifyPropertyChanged(BR.label);
        }
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + getLabel();
        return blueprintFragment;
    }
}
