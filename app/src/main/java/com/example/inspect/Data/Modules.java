package com.example.inspect.Data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        indices = {@Index(value = {"module_Position"}, unique = true)},
        foreignKeys = {
                @ForeignKey(
                        entity = Template.class,
                        parentColumns = "module_Id",
                        childColumns = "module_Id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Template.class,
                        parentColumns = "template_Id",
                        childColumns = "template_Id",
                        onDelete = ForeignKey.CASCADE
                ),
        })
public class Modules {
    private int module_Id;
    private int image_Id;
}
