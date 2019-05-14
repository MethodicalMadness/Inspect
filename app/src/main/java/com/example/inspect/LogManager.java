package com.example.inspect;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

public class LogManager {

    private static int e;


    //Allows the classes to report and display an error
    public static void wtf(String logMessageTag, String logMessage, Throwable throwableException){
        throw new RuntimeException("Error:" + logMessage);

    }

    //Reports error status to the log without displaying it
    public static void reportStatus(Context context, String logStatusTag, String logStatus){
        int logResult = Log.e(logStatusTag, logStatus);
        if (logResult > 0)
            logToFile(context, logStatusTag, logStatus);
    }

    //Sends an error message and the exception to LogCat and to a log file
    public static void e(Context context, String logMessageTag, String logMessage, Throwable throwableException)
    {
        if (!Log.isLoggable(logMessageTag, Log.ERROR))
            return;

        int logResult = Log.e(logMessageTag, logMessage, throwableException);
        if (logResult > 0)
            logToFile(context, logMessageTag, logMessage + "\r\n" + Log.getStackTraceString(throwableException));
    }

    //Writes a message to the log file on the device
    private static void logToFile(Context context, String logMessageTag, String logMessage) {

        Date currentTime = Calendar.getInstance().getTime();
        final String STRING = new String((String.format("%1s [%2s]:%3s\r\n", "[" + currentTime + "]", logMessageTag + "", logMessage + "")));
        try { // catches IOException below
            //Date currentTime = Calendar.getInstance().getTime();
            //final String STRING = new String((String.format("%1s [%2s]:%3s\r\n", "[" + currentTime + "]", logMessageTag + "", logMessage + "")));

            // ##### Write a file to the disk #####
            /* We have to use the openFileOutput()-method
             * the ActivityContext provides, to
             * protect your file from others and
             * This is done for security-reasons.
             * We chose MODE_WORLD_READABLE, because
             *  we have nothing to hide in our file */
            FileOutputStream fOut = context.openFileOutput("InspectLog.txt", Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.write(STRING);
            /* ensure that everything is
             * really written out and close */
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }


        try{
            //Opens the file and puts the content into an array to test the string went in correctly
            FileInputStream fileIn = context.openFileInput("InspectLog.txt");
            InputStreamReader inputReader = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[STRING.length()];
        // Fill the Buffer with data from the file and read
            inputReader.read(inputBuffer);
            String readString = new String(inputBuffer);
/*
        // Check if we read back the same chars that we had written out
            boolean isTheSame = STRING.equals(readString);
        // Prints out the above boolean to determine if the strings compared are the same
            Log.i("File Reading stuff", "Success? " + isTheSame);
*/
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


/*

        //Absolute stored location will be /storage/emulated/0/InspectLog.txt
        try
        {
            // Gets the log file from the root of the primary storage. If it does not exist, the file is created.
            File logFile = new File(Environment.getExternalStorageDirectory().getPath(), "InspectLog.txt");
            if (!logFile.exists()) {
                logFile.mkdirs();
            }
            // Write the message to the log with a timestamp
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
            Date currentTime = Calendar.getInstance().getTime();
            writer.write(String.format("%1s [%2s]:%3s\r\n", "[" + currentTime + "]", logMessageTag + "", logMessage + ""));
            writer.close();
            // Refresh the data so it can seen when the device is plugged into a computer.
            MediaScannerConnection.scanFile(context,
                    new String[] { logFile.toString() },
                    null,
                    null);

        }
        catch (IOException e)
        {
            Log.e("logTag", "Unable to log exception to file.");
        }

        */

        //Displays the error
    public static void displayError(String displayError){
        //TODO UI integration to be called from reportError()
        Context context = App.getContext();
        LogManager.reportStatus(context, "LOGMANAGER", "displayError");
    }

}
