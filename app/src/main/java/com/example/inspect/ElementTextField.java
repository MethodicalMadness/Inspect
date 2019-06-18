package com.example.inspect;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

/**
 * Text field data object. Data binding ensures that changes in the corresponding view are
 * replicated here so that when the elements are deconstructed the users input makes it into the
 * resulting blueprint.
 */
public class ElementTextField extends TemplateElement{

    private final ObservableField<String> label = new ObservableField<>();
    private final ObservableField<String> fill = new ObservableField<>();

    /**
     * Constructor.
     * @param label
     * @param fill
     */
    public ElementTextField(String label, String fill) {
        this.label.set(label);
        this.fill.set(fill);
        setType("1");
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
            notifyPropertyChanged(BR.label);
        }
    }

    /**
     * Gets the fill (user filled string)
     * @return
     */
    @Bindable
    public String getFill() {
        return this.fill.get();
    }

    /**
     * Sets the fill (user filled string)
     * @param fill
     */
    @Bindable
    public void setFill(String fill) {
        if (!this.fill.equals(fill)) {
            this.fill.set(fill);
            notifyPropertyChanged(BR.fill);
        }
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + getLabel() + "," + getFill();
        return blueprintFragment;
    }
}
