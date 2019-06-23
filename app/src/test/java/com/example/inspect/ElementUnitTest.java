package com.example.inspect;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ElementUnitTest {

    private String label = "new label";
    private String fill = "user input";

    @Test
    public void textFieldCheck(){
        ElementTextField element = new ElementTextField(label, fill);
        String result = element.deconstructElement();
        String expected = "1,new label,user input";
        assertEquals(expected, result);
    }

    @Test
    public void spacerFieldCheck(){
        ElementSpacerField element = new ElementSpacerField();
        String result = element.deconstructElement();
        String expected = "4,spacer";
        assertEquals(expected, result);
    }

    @Test
    public void paragraphFieldCheck(){
        ElementParagraphField element = new ElementParagraphField(label, fill);
        String result = element.deconstructElement();
        String expected = "2,new label,user input";
        assertEquals(expected, result);
    }

    @Test
    public void imageFieldCheck(){
        ElementImageField element = new ElementImageField();
        String result = element.deconstructElement();
        String expected = "5,imageString";
        assertEquals(expected, result);
    }

    @Test
    public void headingFieldCheck(){
        ElementHeadingField element = new ElementHeadingField(label);
        String result = element.deconstructElement();
        String expected = "3,new label";
        assertEquals(expected, result);
    }

}
