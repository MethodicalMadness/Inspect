package com.example.inspect.Data;

import android.view.View;

public class Modules {
    private int id;
    private String moduleName;

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

    public View getModuleViewRef() {
        return moduleViewRef;
    }

    public void setModuleViewRef(View moduleViewRef) {
        this.moduleViewRef = moduleViewRef;
    }

    private View moduleViewRef;
}
