package com.example.inspect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.print.PrintManager;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void printPDF(View view) {
        PrintManager printManager = (PrintManager) getSystemService(PRINT_SERVICE);
        printManager.print("print_any_view_job_name", new ViewPrintAdapter(this, findViewById(R.id.linearLayoutVert)), null);
    }
}
