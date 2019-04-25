package com.example.inspect;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogManager {

    private static int e;
    private File logFile = new File("log.txt");


    //Allows the classes to report an error//
    public static void reportError(String reportError){

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
            File logFile = new File(Environment.getExternalStorageDirectory(), "Inspect.txt");
            if (!logFile.exists())
                logFile.createNewFile();
            // Write the message to the log with a timestamp
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            writer.write(String.format("%1s [%2s]:%3s\r\n", getDateTimeStamp(), logMessageTag, logMessage));
            writer.close();
            // Refresh the data so it can seen when the device is plugged in a
            // computer. You may have to unplug and replug to see the latest
            // changes
            MediaScannerConnection.scanFile(context,
                    new String[] { logFile.toString() },
                    null,
                    null);

        }
        catch (IOException e)
        {
            Log.e("com.eliaszanbaka.Logger", "Unable to log exception to file.");
        }
    }




    //Appends or adds errors to the error log//
    public static void appendLog(){
        try{
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));
            //variable "text" is a string to be appended
            bw.append(text);
            bw.newLine();
            bw.close();
        }
        catch (IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Displays the error//
    public static void displayError(String displayError){

    }

    //Displays the status of the error//
    public static void displayStatus(String displayStatus){

    }


}
