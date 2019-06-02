package com.example.inspect.Data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

//Convert array to a string and vice versa
public class ModuleTypeConverter {
    // ERROR HERE CAUSING ISSUES
    private static Gson gson = new Gson();
    @TypeConverter
    public String fromModuleValuesList(List<Modules> modules) {
        if (modules == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Modules>>() {
        }.getType();
        String json = gson.toJson(modules, type);
        return json;
    }

    @TypeConverter
    public List<Modules> toModulesList(String modulesString) {
        if (modulesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Modules>>() {
        }.getType();
        List<Modules> modulesAllList = gson.fromJson(modulesString, type);
        return modulesAllList;
    }

}

