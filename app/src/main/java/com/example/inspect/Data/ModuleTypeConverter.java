package com.example.inspect.Data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;


public class ModuleTypeConverter {

    private static Gson gson = new Gson();
    @TypeConverter
    public static ArrayList<Modules> stringToList(String data) {
        if (data == null) {
            return (ArrayList)Collections.emptyList();
        }

        Type listType = new TypeToken<ArrayList<Modules>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String ListToString(ArrayList<Modules> someObjects) {
        return gson.toJson(someObjects);
    }
}

