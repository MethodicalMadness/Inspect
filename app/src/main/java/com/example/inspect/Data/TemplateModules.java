package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TemplateModules {
    @PrimaryKey
    private int moduleId;
    private String moduleName;

    //constructor
    public TemplateModules(int moduleId, String moduleName) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
    }

    //getters and setters
    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
