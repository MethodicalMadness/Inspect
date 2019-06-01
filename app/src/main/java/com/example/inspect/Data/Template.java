package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Template {
    @PrimaryKey
    private int id;
    private String name;
    private Modules templateModules;
}
