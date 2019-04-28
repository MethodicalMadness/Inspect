package com.example.inspect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void newBlankTemplate(View view) {
        Intent intent = new Intent(this, TemplateEditor.class);
        startActivity(intent);
    }
}
