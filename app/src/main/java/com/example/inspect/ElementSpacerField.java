package com.example.inspect;

public class ElementSpacerField extends TemplateElement{

    public ElementSpacerField() {
        setType("4");
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + "spacer";
        return blueprintFragment;
    }
}
