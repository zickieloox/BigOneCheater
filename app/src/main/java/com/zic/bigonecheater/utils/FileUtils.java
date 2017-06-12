package com.zic.bigonecheater.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static void writeToTextFile(String dirName, String fileName, String body)
    {
        FileOutputStream fos;

        try {
            final File dir = new File(dirName);

            if (!dir.exists())
            {
                if(!dir.mkdirs()){
                    Log.e(TAG, "writeToTextFile: Could not create the directory.");
                }
            }

            final File myFile = new File(dir, fileName + ".txt");

            if (!myFile.exists())
            {
                myFile.createNewFile();
            }

            fos = new FileOutputStream(myFile);

            fos.write(body.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getLineList(String textFilePath) {
        BufferedReader br;
        ArrayList<String> lineList = new ArrayList<>();

        try {
            br = new BufferedReader(new FileReader(new File(textFilePath)));
            for (String line; (line = br.readLine()) != null; ) {
                lineList.add(line);
            }

            return lineList;
        } catch (IOException e) {
            Log.e(TAG, "getLineList: " + e.toString());
            return null;
        }

    }

    public static String getLine(String textFilePath, int lineNumber) {
        ArrayList<String> textLines;
        textLines = getLineList(textFilePath);
        if (textLines == null) {
            return null;
        } else if (lineNumber < 0 || lineNumber >= textLines.size()) {
            Log.e(TAG, "getLine: " + "line number " + lineNumber + " was not found!");
            return null;
        } else {
            return textLines.get(lineNumber);
        }
    }
}
