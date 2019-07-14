package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        indices = {@Index(value = {"module_Position"}, unique = true)},
        foreignKeys = {
                @ForeignKey(
                        entity = Template.class,
                        parentColumns = "module_Id",
                        childColumns = "module_Id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Template.class,
                        parentColumns = "template_Id",
                        childColumns = "template_Id",
                        onDelete = ForeignKey.CASCADE
                ),
        })
public class Template_Module{
    private int module_Id;
    private int template_Id;
    private int module_Position;

    //Constructor
    public Template_Module(int module_Id, int template_Id, int module_Position) {
        this.module_Id = module_Id;
        this.template_Id = template_Id;
        this.module_Position = module_Position;
    }

    //Getter and setters
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

    public int getModule_Position() {
        return module_Position;
    }

    public void setModule_Position(int module_Position) {
        this.module_Position = module_Position;
    }
}
