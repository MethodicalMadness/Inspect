package com.example.inspect;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;


public class ElementParagraphField extends TemplateElement {

    private final ObservableField<String> label = new ObservableField<>();
    private final ObservableField<String> fill = new ObservableField<>();

    public ElementParagraphField(String label, String fill) {
        this.label.set(label);
        this.fill.set(fill);
        setType("2");
    }

    @Bindable
    public String getLabel() {
        return this.label.get();
    }

    @Bindable
    public void setLabel(String label) {
        if (!this.label.equals(label)){
            this.label.set(label);
            //notifyPropertyChanged(BR.label);
        }
    }

    @Bindable
    public String getFill() {
        return this.fill.get();
    }

    @Bindable
    public void setFill(String fill) {
        if (!this.fill.equals(fill)) {
            this.fill.set(fill);
            //notifyPropertyChanged(BR.fill);
        }
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + getLabel() + "," + getFill();
        return blueprintFragment;
    }

}
