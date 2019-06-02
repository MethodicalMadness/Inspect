package com.example.inspect.Data;


public class Modules {
    private int id;
    private String moduleName;

    //constructor
    public Modules(int id, String moduleName) {
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
