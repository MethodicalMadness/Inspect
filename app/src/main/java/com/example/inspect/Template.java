package com.example.inspect;

import android.content.Context;
import java.util.ArrayList;


public class Template {

    private ArrayList<TemplatePage> templatePages = new ArrayList<>();

    public Template() {
    }

    public Template(TemplatePage templatePage){
        addPage(templatePage);
    }

    // add page to this template
    public void addPage(TemplatePage templatePage){
        templatePages.add(templatePage);
    }

    // remove an existing element from this page
    public void removePage(int index){
        templatePages.remove(index);
    }

    // get page
    public TemplatePage focusPage(int index){
        return templatePages.get(index);
    }

    //Saves current state of the template//
    public void saveState(){
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
        String newline = System.getProperty("line.separator");
        String blueprint = "";
        //for each page
        for (int i = 0; i < templatePages.size(); i++){
            blueprint += "page " + i + newline;
            TemplatePage templatePage = templatePages.get(i);
            //get elements in each page
            ArrayList<TemplateElement> elements = templatePage.getElements();
            //for each element in the page
            for (int ii = 0; ii < elements.size(); ii++){
                blueprint += elements.get(ii).deconstructElement() + newline;
            }
        }
        // TODO: write to file
    }

    public void loadState(String blueprint){

    }
}
