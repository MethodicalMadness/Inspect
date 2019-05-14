package com.example.inspect;

public class ModuleInfo {
    private String moduleName;
    private int moduleRefNum;
    private int index;

    public ModuleInfo(String name, int newIndex){
        moduleName = name;
        moduleRefNum = 0;
        index = newIndex;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getModuleRefNum() {
        return moduleRefNum;
    }

    public void setModuleRefNum(int moduleRefNum) {
        this.moduleRefNum = moduleRefNum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
