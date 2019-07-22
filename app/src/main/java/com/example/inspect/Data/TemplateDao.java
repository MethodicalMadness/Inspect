package com.example.inspect.Data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
interface TemplateDao {
    //Queries
    public int getTemplateId();
    public String getTemplateName();

    //Insert
    public void setTemplateId();
    public void setTemplateName();

    //Update
    public void updateTemplateName();

    //Delete
    public void removeTemplate();
    public void removeTemplateName();

}
