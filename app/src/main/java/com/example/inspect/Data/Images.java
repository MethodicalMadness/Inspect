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
public class Images {

    @PrimaryKey(autoGenerate = true)
    private int image_id;
    private int module_id;
    private int template_id;
    //will need type converter
    private String image_URI;

    //constructor
    public Images(int image_id, int module_id, int template_id, String image_URI) {
        this.image_id = image_id;
        this.module_id = module_id;
        this.template_id = template_id;
        this.image_URI = image_URI;
    }

    //getters and setters
    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
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

    public String getImage_URI() {
        return image_URI;
    }

    public void setImage_URI(String image_URI) {
        this.image_URI = image_URI;
    }
}
