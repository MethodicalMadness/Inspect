package com.example.inspect;

import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PopUp extends AppCompatActivity {

    public void createPopUp(PopUpManager popUpInputs){
        TextView popHeader = findViewById(R.id.tvPopHeader);
        TextView popMessage = findViewById(R.id.tvPopMessage);
        Button btnPositive = findViewById(R.id.btnPositive);
        Button btnNegative = findViewById(R.id.btnNegative);

        popHeader.setText(popUpInputs.getHeading());
        popMessage.setText(popUpInputs.getMessage());
        btnPositive.setText(popUpInputs.getPositiveBtn());
        btnNegative.setText(popUpInputs.getNegativeBtn());

        setContentView(R.layout.pop_up);
    }
}
