package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"id"}, unique = true)})
public class TemplateModules {
    @PrimaryKey
    private int id;
    private String moduleName;

    //constructor
    public TemplateModules(int id, String moduleName) {
        this.id = id;
        this.moduleName = moduleName;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
