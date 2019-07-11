package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (
        indices = {@Index(value = {"module_Position"}, unique = true)},
        foreignKeys = {
                @ForeignKey(
                        entity = Modules.class,
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
                @ForeignKey(
                        entity = TextBoxes.class,
                        parentColumns = "textBox_Id",
                        childColumns = "textBox_Id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Images.class,
                        parentColumns = "image_Id",
                        childColumns = "image_Id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Options.class,
                        parentColumns = "options_Id",
                        childColumns = "options_Id",
                        onDelete = ForeignKey.CASCADE
                ),
        })
public class Template_Module{
    private int module_Id;
    private String module_Name;
    private int textBox_Id;
    private int image_Id;
    private int options_Id;
    private int module_Position;

    //Constructor
    public Template_Module(int module_Id, String module_Name, int textBox_Id, int image_Id, int options_Id, int module_Position) {
        this.module_Id = module_Id;
        this.module_Name = module_Name;
        this.textBox_Id = textBox_Id;
        this.image_Id = image_Id;
        this.options_Id = options_Id;
        this.module_Position = module_Position;
    }

    //getters and setters
    public int getModule_Id() {
        return module_Id;
    }

    public void setModule_Id(int module_Id) {
        this.module_Id = module_Id;
    }

    public String getModule_Name() {
        return module_Name;
    }

    public void setModule_Name(String module_Name) {
        this.module_Name = module_Name;
    }

    public int getTextBox_Id() {
        return textBox_Id;
    }

    public void setTextBox_Id(int textBox_Id) {
        this.textBox_Id = textBox_Id;
    }

    public int getImage_Id() {
        return image_Id;
    }

    public void setImage_Id(int image_Id) {
        this.image_Id = image_Id;
    }

    public int getOptions_Id() {
        return options_Id;
    }

    public void setOptions_Id(int options_Id) {
        this.options_Id = options_Id;
    }

    public int getModule_Position() {
        return module_Position;
    }

    public void setModule_Position(int module_Position) {
        this.module_Position = module_Position;
    }
}
