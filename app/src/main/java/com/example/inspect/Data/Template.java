package com.example.inspect.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


//Database
@Entity (foreignKeys = {
        @ForeignKey(
                entity = TemplateModules.class,
                parentColumns = "id",
                childColumns = "moduleId",
                onDelete = ForeignKey.CASCADE
        )
})
public class Template {
    @PrimaryKey(autoGenerate = true)
    private int templateId;
    private String name;
    private int moduleId;

    //constructor
    public Template(int templateId, String name, int moduleId) {
        this.templateId = templateId;
        this.name = name;
        this.moduleId = moduleId;
    }

    //setters and getters
    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }
}
