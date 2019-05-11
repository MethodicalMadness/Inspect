package com.example.inspect;

import android.content.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Template {

    private ArrayList<TemplatePage> templatePages = new ArrayList<>();

    public Template() {
    }

    public Template(TemplatePage templatePage) {
        addPage(templatePage);
    }

    // add page to this template
    public void addPage(TemplatePage templatePage) {
        templatePages.add(templatePage);
    }

    // remove an existing element from this page
    public void removePage(int index) {
        templatePages.remove(index);
    }

    // get page
    public TemplatePage focusPage(int index) {
        return templatePages.get(index);
    }

    //Saves current state of the template//
    public void saveState() {
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEMPLATE", "Template");
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
        // TODO: write to file
    }

    //Loads data from file and creates data objects related
    // to pages and elements that make up templates
    // TODO: instantiate and bind xml layouts related to data objects
    public void loadState(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            Pattern newPage = Pattern.compile("[0-9]");
            String splitBy = ",";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Matcher matcher = newPage.matcher(line);
                TemplatePage currentPage = new TemplatePage(0);
                // if the line is just a number, it is a new page
                if (matcher.find()){
                    currentPage = new TemplatePage(Integer.valueOf(line));
                    addPage(currentPage);
                }
                //else it is an element
                else {
                    //element[0] is the type, following values are specific to the element
                    String[] element = line.split(splitBy);
                    if (Integer.valueOf(element[0]) == 1){
                        ElementTextField textField = new ElementTextField(element[1],element[2]);
                        currentPage.addElement(textField);
                    }
                    else if (Integer.valueOf(element[0]) == 2) {
                        ElementParagraphField paraField = new ElementParagraphField(element[1],element[2]);
                        currentPage.addElement(paraField);
                    }
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
