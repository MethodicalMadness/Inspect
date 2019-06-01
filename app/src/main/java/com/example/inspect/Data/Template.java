package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.ArrayList;

@Entity
public class Template {
    @PrimaryKey
    private int id;
    private String name;

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
        return templateModules;
    }

    public void setTemplateModules(ArrayList<Modules> templateModules) {
        this.templateModules = templateModules;
    }

    @TypeConverters(ModuleTypeConverter.class)
    private ArrayList<Modules> templateModules;
}
