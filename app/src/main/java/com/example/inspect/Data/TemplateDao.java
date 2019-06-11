package com.example.inspect.Data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
interface TemplateDao {
    //Queries
    @Query("SELECT name FROM Template")
    public String getTemplateName();

    //ERROR CAUSED HERE
    //@Query("SELECT * FROM template INNER JOIN templatemodules ON template.moduleId = templateModules.id")
    //public String moduleNames();


    //Insert



}
