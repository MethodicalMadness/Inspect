package com.example.inspect.Data;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;

@Dao
interface TemplateDao {
    //Queries
    @Query("SELECT template_Id FROM Template WHERE template_Name = :tempName")
    public int getTemplateId(String tempName);
    @Query("SELECT template_Name FROM Template WHERE template_Id = :tempId")
    public String getTemplateName(int tempId);

    //Insert/Update
    @Query("UPDATE template SET template_Id = :tempId")
    public void setTemplateId(int tempId);
    @Query("UPDATE template SET template_Name = :tempName WHERE template_Id = :tempId")
    public void updateTemplateName(String tempName, int tempId);

    //Delete
    @Query("DELETE FROM Template WHERE template_Id = :tempId")
    public void removeTemplate(int tempId);
    @Query("UPDATE template SET template_Name = ''")
    public void removeTemplateName();

}
