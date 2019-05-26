package com.example.inspect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogHelper {

    public AlertDialog.Builder aBuilder;

    public AlertDialogHelper(Context context, String heading, String message, String positive, String negative){
        aBuilder = new AlertDialog.Builder(context);
        aBuilder.setTitle(heading);
        aBuilder.setMessage(message);
        aBuilder.setCancelable(true);

        aBuilder.setPositiveButton(
                positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        aBuilder.setNegativeButton(
                negative,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


    }

    public void run(AlertDialogHelper aHelper){
        AlertDialog alertPopUp = aHelper.aBuilder.create();
        alertPopUp.show();
    }
}
