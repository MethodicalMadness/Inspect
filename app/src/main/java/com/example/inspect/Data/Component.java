package com.example.inspect.Data;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
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
})

public class Component {
    @PrimaryKey(autoGenerate = true)
    private int component_Id;
    private int module_Id;
    private int template_id;
    private int component_Position;
    private String component_type;
    private String component_Label;
    private String textBox_Content;
    private Uri image_URI;

    //Constructor
    public Component(int component_Id, int module_Id, int template_id, int component_Position, String component_type, String component_Label, String textBox_Content, Uri image_URI) {
        this.component_Id = component_Id;
        this.module_Id = module_Id;
        this.template_id = template_id;
        this.component_Position = component_Position;
        this.component_type = component_type;
        this.component_Label = component_Label;
        this.textBox_Content = textBox_Content;
        this.image_URI = image_URI;
    }

    //Getters and Setters
    public int getComponent_Id() {
        return component_Id;
    }

    public void setComponent_Id(int component_Id) {
        this.component_Id = component_Id;
    }

    public int getModule_Id() {
        return module_Id;
    }

    public void setModule_Id(int module_Id) {
        this.module_Id = module_Id;
    }

    public int getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }

    public int getComponent_Position() {
        return component_Position;
    }

    public void setComponent_Position(int component_Position) {
        this.component_Position = component_Position;
    }

    public String getComponent_type() {
        return component_type;
    }

    public void setComponent_type(String component_type) {
        this.component_type = component_type;
    }

    public String getComponent_Label() {
        return component_Label;
    }

    public void setComponent_Label(String component_Label) {
        this.component_Label = component_Label;
    }

    public String getTextBox_Content() {
        return textBox_Content;
    }

    public void setTextBox_Content(String textBox_Content) {
        this.textBox_Content = textBox_Content;
    }

    public Uri getImage_URI() {
        return image_URI;
    }

    public void setImage_URI(Uri image_URI) {
        this.image_URI = image_URI;
    }
}
