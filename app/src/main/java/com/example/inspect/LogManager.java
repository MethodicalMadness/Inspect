package com.example.inspect;

public class LogManager {

    private File logFile = new File("log.txt");


    //Allows the classes to report an error//
    public static void reportError(String reportError){

    }

    //Reports the status of the error//
    public static void reportStatus(String reportStatus){

    }

    //Logs the error//
    public static void createLog(){

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
