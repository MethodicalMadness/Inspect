package com.example.inspect.Data;

import androidx.room.Query;
import androidx.room.Dao;


@Dao
public interface ModulesDao {
    //Queries
    public int getModuleId();
    public String getModuleName();
    public int getModulePosition();

    //Insert
    public void setModuleId();
    public void setModuleName();
    public void setModulePosition();

    //Update
    public void updateModuleName();
    public void updateModulePosition();

    //Delete
    public void removeModule();
    public void removeModuleName();
}
