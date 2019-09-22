package com.binarygiant.inspect;

/**
 * A simple space element. Only needed to keep track of formatting.
 */
public class ElementSpacerField extends TemplateElement{

    /**
     * Constructor
     */
    public ElementSpacerField() {
        setType("4");
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + "spacer";
        return blueprintFragment;
    }
}
