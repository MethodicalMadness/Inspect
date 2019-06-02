package com.example.inspect.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

//Database
@Entity
public class Template {
    @PrimaryKey
    private int id;
    private String name;
    @TypeConverters({ModuleTypeConverter.class})
    @ColumnInfo(name = "template_modules")
    private ArrayList<Modules> templateModules;

    public Template(int id, String name, ArrayList<Modules> templateModules) {
        this.id = id;
        this.name = name;
        this.templateModules = templateModules;
    }

    //getter and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Modules> getTemplateModules() {
        if (templateModules == null) {
            templateModules = new ArrayList<>();
            return templateModules;
        }

        return templateModules;
    }

    public void setTemplateModules(ArrayList<Modules> templateModules) {

        this.templateModules = templateModules;
    }
}
