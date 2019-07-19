package com.example.inspect.Data;

        import androidx.room.Database;
        import androidx.room.RoomDatabase;
        import androidx.room.TypeConverters;

@Database(entities = {Template.class, Template_Module.class, Modules.class, Images.class, Options.class, TextBoxes.class}, version = 1, exportSchema = true)
@TypeConverters(UriConverter.class)
public abstract class TemplateDatabase extends RoomDatabase {
    //set up data access objects
    abstract TemplateDao templateDao();
    abstract TemplateModulesDao templateModulesDao();
    abstract ModulesDao modulesDao();
    abstract ImagesDao imagesDao();
    abstract OptionsDao optionsDao();
    abstract TextBoxesDao textBoxesDao();

    private static volatile TemplateDatabase INSTANCE;

}
