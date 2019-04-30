package com.example.inspect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = App.getContext();
        LogManager.reportStatus(context, "TEST", "Test");

    }

    //setting up menu activities
    public void toTemplateMenu(View view){
        Intent intent = new Intent(MainActivity.this, TemplateMenu.class);
        startActivity(intent);
    }

    public void toLoadInspection(View view){
        Intent intent = new Intent(MainActivity.this, LoadInspection.class);
        startActivity(intent);
    }

    public void toManageTemplates(View view){
        Intent intent = new Intent(MainActivity.this, ManageTemplateMenu.class);
        startActivity(intent);
    }

    public void newBlankTemplate(View view) {
        Intent intent = new Intent(this, TemplateEditor.class);
        startActivity(intent);
    }
}
