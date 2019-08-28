package com.example.inspect;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

/**
 * Heading field data object. Data binding ensures that changes in the corresponding view are
 * replicated here so that when the elements are deconstructed the users input makes it into the
 * resulting blueprint.
 */
public class ElementHeadingField extends TemplateElement {

    private final ObservableField<String> label = new ObservableField<>();

    /**
     * Constructor.
     * @param label
     */
    public ElementHeadingField(String label) {
        this.label.set(label);
        setType("3");
    }

    /**
     * Returns the label.
     * @return
     */
    @Bindable
    public String getLabel() {
        return this.label.get();
    }

    /**
     * Sets the label.
     * @param label
     */
    @Bindable
    public void setLabel(String label) {
        if (!this.label.equals(label)){
            this.label.set(label);
            //notifyPropertyChanged(BR.label);
        }
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + getLabel();
        return blueprintFragment;
    }
}
