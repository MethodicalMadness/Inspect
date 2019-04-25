package com.example.inspect;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class LogManager {

    private static int e;



    //Allows the classes to report an error//
    public static void reportError(String reportError, Throwable throwableException){
        
    }

    //Reports the status of the error//
    public static void reportStatus(String reportStatus){

    }


    //Sends an error message and the exception to LogCat and to a log file//
    public static void e(Context context, String logMessageTag, String logMessage, Throwable throwableException)
    {
        if (!Log.isLoggable(logMessageTag, Log.ERROR))
            return;

        int logResult = Log.e(logMessageTag, logMessage, throwableException);
        if (logResult > 0)
            logToFile(context, logMessageTag, logMessage + "\r\n" + Log.getStackTraceString(throwableException));
    }

    //Writes a message to the log file on the device//
    private static void logToFile(Context context, String logMessageTag, String logMessage)
    {
        try
        {
            // Gets the log file from the root of the primary storage. If it does
            // not exist, the file is created.
            File logFile = new File(Environment.getExternalStorageDirectory(), "log.txt");
            if (!logFile.exists())
                logFile.createNewFile();
            // Write the message to the log with a timestamp
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            Date currentTime = Calendar.getInstance().getTime();
            writer.write(String.format("%1s [%2s]:%3s\r\n", "[" + currentTime + "]", logMessageTag, logMessage));
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer.
            MediaScannerConnection.scanFile(context,
                    new String[] { logFile.toString() },
                    null,
                    null);

        }
        catch (IOException e)
        {
            Log.e("logTag", "Unable to log exception to file.");
        }
    }

    //Displays the error//
    public static void displayError(String displayError){
        //TODO UI integration to be called from reportError()
    }

}
