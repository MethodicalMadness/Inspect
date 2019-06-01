package com.example.inspect.Data;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Modules {
    @PrimaryKey
    private int id;
    @NonNull
    private String moduleName;
    private View moduleViewRef;
}
