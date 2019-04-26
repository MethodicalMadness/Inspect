package com.example.inspect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newBlankTemplate(View view) {
        Intent intent = new Intent(this, TemplateEditor.class);
        startActivity(intent);
    }
}
