package com.example.inspect;

import java.util.ArrayList;

/**
 * A container for Elements.
 */
public class TemplatePage {

    private ArrayList<TemplateElement> elements = new ArrayList<>();
    private int index;

    /**
     * Constructor
     * @param index
     */
    public TemplatePage(int index) {
        this.index = index;
    }

    /**
     * Get the elements from the page.
     * @return
     */
    public ArrayList<TemplateElement> getElements() {
        return elements;
    }

    /**
     * Returns if the page is empty
     * @return
     */
    public boolean isPageEmpty(){
        boolean result  = elements.size() == 0;
        return result;
    }

    /**
     * Get the pages index.
     * @return
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the pages index.
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Add an element to this page.
     * @param element
     */
    public void addElement(TemplateElement element){
        elements.add(element);
    }

    /**
     * Remove an element from the page
     * @param index
     */
    public void removeElement(int index){
        elements.remove(index);
    }
}