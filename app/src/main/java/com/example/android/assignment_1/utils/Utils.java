package com.example.android.assignment_1.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;



public class Utils {
    public final static String MSG_KEY_INTENT = "Msg_key";

    //Using Files
    //Gets the list of added note titles
    public static String[] getListFromFiles(Context context){
        ArrayList<String> lstOfFilesInMemory = new ArrayList<>();
        try{
            File filesDir = context.getFilesDir();
            File[] files = filesDir.listFiles();
            for(int i=1;i<files.length;i++)
                lstOfFilesInMemory.add(files[i].getName().toString());
        }catch (Exception e){
            Log.e("ERROR", e.getMessage());
        }
        String[] list = lstOfFilesInMemory.toArray(new String[lstOfFilesInMemory.size()]);
        return list;
    }

    //Using Files
    //Gets the body of the notes.
    public static String getFileByName(Context context, String filename){
        String tempStr = "";
        try{
            FileInputStream inputtStream = context.openFileInput(filename.replace(" ", ""));
            int c;
            while((c=inputtStream.read())!=-1){
                tempStr+=Character.toString((char) c);
            }
            inputtStream.close();
        }catch (Exception e){
            Log.e("ERROR", e.getMessage());
        }
        return tempStr;
    }
}
