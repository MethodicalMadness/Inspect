package com.example.inspect.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
interface TemplateDao {
    //Queries
    @Query("Select name from Template")
    public String getTemplateName();

    @Query("Select templateModules from template")
    public ArrayList<Modules> getModules();

    //Insert



}
