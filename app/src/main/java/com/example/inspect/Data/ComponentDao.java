package com.example.inspect.Data;

import android.net.Uri;

public interface ComponentDao {
    //Queries
    public int getComponentId();
    public int getComponentPosition();
    public String getComponentType();
    public String getComponentLabel();
    public String getTextBoxContent();
    public Uri getImageURI();

    //Insert
    public void setComponentId();
    public void setComponentPosition();
    public void setComponentType();
    public void setComponentLabel();
    public void setTextBoxContent();
    public void setImageURI();

    //Update
    public void updateComponentPosition();
    public void updateComponentLabel();
    public void updateTextBoxContent();
    public void updateImageURI();

    //Delete
    public void removeComponent();
    public void removeComponentLabel();
    public void removeTextBoxContent();
    public void removeImageURI();
}
