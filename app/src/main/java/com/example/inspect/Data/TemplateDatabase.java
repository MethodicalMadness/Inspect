package com.example.inspect.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = Template.class, version = 1, exportSchema = true)
@TypeConverters({ModuleTypeConverter.class})
public abstract class TemplateDatabase extends RoomDatabase {
    abstract TemplateDao templateDao();

    private static volatile TemplateDatabase INSTANCE;

    /*static TemplateDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TemplateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TemplateDatabase.class, "template_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }*/
}
