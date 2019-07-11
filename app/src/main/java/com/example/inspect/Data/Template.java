package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Template {
    @PrimaryKey(autoGenerate = true)
    private int template_Id;
    private String template_Name;

    //constructor
    public Template(int template_Id, String template_Name) {
        this.template_Id = template_Id;
        this.template_Name = template_Name;
    }

    //setters and getters
    public int getTemplate_Id() {
        return template_Id;
    }

    public void setTemplate_Id(int template_Id) {
        this.template_Id = template_Id;
    }

    public String getTemplate_Name() {
        return template_Name;
    }

    public void setTemplate_Name(String template_Name) {
        this.template_Name = template_Name;
    }

}
