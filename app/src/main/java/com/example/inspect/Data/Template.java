package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class Template {
    @PrimaryKey
    private int id;
    private String name;
    private ArrayList<Modules> templateModules;
}
