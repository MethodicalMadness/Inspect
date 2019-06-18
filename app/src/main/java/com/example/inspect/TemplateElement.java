package com.example.inspect;

import androidx.databinding.BaseObservable;

/**
 * Abstract class for Elements that are on the TemplatePage
 */
public abstract class TemplateElement extends BaseObservable {

    private String type;

    /**
     * Get the type of element
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of element
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * deconstructs the elements parts to be added to the blueprint
     * @return
     */
    public abstract String deconstructElement();
}
