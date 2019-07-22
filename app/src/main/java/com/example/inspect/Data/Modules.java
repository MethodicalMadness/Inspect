package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (
    foreignKeys = {
        @ForeignKey(
            entity = Template.class,
            parentColumns = "template_Id",
            childColumns = "template_Id",
            onDelete = ForeignKey.CASCADE
        ),
    }
)

public class Modules {
    @PrimaryKey(autoGenerate = true)
    private int module_Id;
    private int template_Id;
    private String module_name;
    private int module_position;

    //Constructor
    public Modules(int module_Id, int template_Id, String module_name, int module_position) {
        this.module_Id = module_Id;
        this.template_Id = template_Id;
        this.module_name = module_name;
        this.module_position = module_position;
    }

    //Getters and Setters
    public int getModule_Id() {
        return module_Id;
    }

    public void setModule_Id(int module_Id) {
        this.module_Id = module_Id;
    }

    public int getTemplate_Id() {
        return template_Id;
    }

    public void setTemplate_Id(int template_Id) {
        this.template_Id = template_Id;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public int getModule_position() {
        return module_position;
    }

    public void setModule_position(int module_position) {
        this.module_position = module_position;
    }
}
