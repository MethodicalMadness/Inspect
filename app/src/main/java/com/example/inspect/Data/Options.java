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
public class Options {

    @PrimaryKey(autoGenerate = true)
    private int options_id;
    private int module_id;
    private int template_id;
    //may need type converter eventually
    private String options_Chosen;

    //constructor
    public Options(int options_id, int module_id, int template_id, String options_Chosen) {
        this.options_id = options_id;
        this.module_id = module_id;
        this.template_id = template_id;
        this.options_Chosen = options_Chosen;
    }

    //setter and getters
    public int getOptions_id() {
        return options_id;
    }

    public void setOptions_id(int options_id) {
        this.options_id = options_id;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public String getOptions_Chosen() {
        return options_Chosen;
    }

    public void setOptions_Chosen(String options_Chosen) {
        this.options_Chosen = options_Chosen;
    }
}
