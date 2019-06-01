package com.example.inspect;

import androidx.lifecycle.ViewModel;

import com.example.inspect.Data.Modules;

import java.util.ArrayList;

public class TemplateViewModel extends ViewModel {
    private int id;
    private String name;
    private ArrayList<Modules> modules;

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

    public ArrayList<Modules> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Modules> modules) {
        this.modules = modules;
    }
}

