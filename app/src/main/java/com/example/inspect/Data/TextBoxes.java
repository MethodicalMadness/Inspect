package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
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
public class TextBoxes {

    @PrimaryKey(autoGenerate = true)
    private int textBox_id;
    private int module_id;
    private int template_id;
    private String textBox_Content;

    //constructor
    public TextBoxes(int textBox_id, int module_id, int template_id, String textBox_Content) {
        this.textBox_id = textBox_id;
        this.module_id = module_id;
        this.template_id = template_id;
        this.textBox_Content = textBox_Content;
    }

    //getter and setters
    public int getTextBox_id() {
        return textBox_id;
    }

    public void setTextBox_id(int textBox_id) {
        this.textBox_id = textBox_id;
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

    public String getTextBox_Content() {
        return textBox_Content;
    }

    public void setTextBox_Content(String textBox_Content) {
        this.textBox_Content = textBox_Content;
    }
}
