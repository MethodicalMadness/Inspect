package com.example.inspect.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Template.class, TemplateModules.class}, version = 1, exportSchema = true)
public abstract class TemplateDatabase extends RoomDatabase {
    abstract TemplateDao templateDao();
    abstract TemplateModulesDao templateModulesDao();

    private static volatile TemplateDatabase INSTANCE;

}
