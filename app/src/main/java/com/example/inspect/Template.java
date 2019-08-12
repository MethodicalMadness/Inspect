package com.example.inspect;

import android.content.Context;
import java.util.ArrayList;

/**
 * Container for TemplatePages.
 */
public class Template {

    private ArrayList<TemplatePage> templatePages = new ArrayList<>();

    /**
     * Constructor
     */
    public Template() {
    }

    /**
     * Adds a page to the Template.
     * @param templatePage
     */


    public void addPage(TemplatePage templatePage) {
        templatePages.add(templatePage);
    }

    /**
     * Removes a page from the Template.
     * @param index
     */
    public void removePage(int index) {
        templatePages.remove(index);
    }


    /**
     * Focus on a specific page.
     * @param index
     * @return
     */



    public TemplatePage focusPage(int index) {
        return templatePages.get(index);
    }

    /**
     * For each page in the template, get the elements and their details
     * @return
     */
    public String createBlueprint(){
        Context context = App.getContext();
        String newline = System.getProperty("line.separator");
        String blueprint = "";
        //for each page
        for (int i = 0; i < templatePages.size(); i++) {
            blueprint += i + newline;
            TemplatePage templatePage = templatePages.get(i);
            //get elements in each page
            ArrayList<TemplateElement> elements = templatePage.getElements();
            //for each element in the page
            for (int ii = 0; ii < elements.size(); ii++) {
                blueprint += elements.get(ii).deconstructElement() + newline;
            }
        }
        LogManager.reportStatus(context, "TEMPLATE", "CreatedBlueprint");
        return blueprint;
    }
}
