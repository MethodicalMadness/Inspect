package com.example.inspect.Data;

        import androidx.room.Database;
        import androidx.room.RoomDatabase;
        import androidx.room.TypeConverters;

@Database(entities = {Template.class,  Modules.class, }, version = 1, exportSchema = true)
@TypeConverters(UriConverter.class)
public abstract class TemplateDatabase extends RoomDatabase {
    //set up data access objects
    abstract TemplateDao templateDao();
    abstract ModulesDao modulesDao();

    private static volatile TemplateDatabase INSTANCE;

}
