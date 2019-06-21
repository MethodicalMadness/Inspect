package com.example.inspect;

import android.content.Context;
import android.util.Log;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

/**
 * Handles logging to file.
 */
public class LogManager {

    private static int e;

    /**
     * Allows the classes to report and display an error
     * @param logMessageTag
     * @param logMessage
     * @param throwableException
     */
    public static void wtf(String logMessageTag, String logMessage, Throwable throwableException){
        throw new RuntimeException("Error:" + logMessage);
    }

    /**
     * Reports error status to the log without displaying it
     * @param context
     * @param logStatusTag
     * @param logStatus
     */
    public static void reportStatus(Context context, String logStatusTag, String logStatus){
        int logResult = Log.e(logStatusTag, logStatus);
        if (logResult > 0) {
            logToFile(context, logStatusTag, logStatus);
        }
    }

    /**
     * Sends an error message and the exception to LogCat and to a log file
     * @param context
     * @param logMessageTag
     * @param logMessage
     * @param throwableException
     */
    public static void e(Context context, String logMessageTag, String logMessage, Throwable throwableException) {
        if (!Log.isLoggable(logMessageTag, Log.ERROR)) {
            return;
        }
        int logResult = Log.e(logMessageTag, logMessage, throwableException);
        if (logResult > 0) {
            logToFile(context, logMessageTag, logMessage + "\r\n" + Log.getStackTraceString(throwableException));
        }
    }

    /**
     * Writes a message to the log file on the device
     * @param context
     * @param logMessageTag
     * @param logMessage
     */
    private static void logToFile(Context context, String logMessageTag, String logMessage) {
        Date currentTime = Calendar.getInstance().getTime();
        final String STRING = new String((String.format("%1s [%2s]:%3s\r\n", "[" + currentTime + "]", logMessageTag + "", logMessage + "")));
        try {
            FileOutputStream fOut = context.openFileOutput("InspectLog.txt", Context.MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            // Write the string to the file
            osw.write(STRING);
            // Flush and close the file to finish the log entry
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
