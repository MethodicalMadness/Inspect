package com.example.inspect.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.ArrayList;
import java.util.List;

@TypeConverters({ModuleTypeConverter.class})
@Dao
interface TemplateDao {
    //Queries
    @Query("SELECT name FROM Template")
    public String getTemplateName();

    //ERROR CAUSED HERE
    @Query("SELECT template_modules FROM template")
    public List<Modules> getModules();

    //Insert



}
