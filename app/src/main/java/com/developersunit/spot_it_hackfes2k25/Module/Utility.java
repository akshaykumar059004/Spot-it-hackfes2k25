package com.developersunit.spot_it_hackfes2k25.Module;

import android.content.Context;
import android.util.Log;

import com.developersunit.spot_it_hackfes2k25.DBHelper.DatabaseLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Utility {

    private static final String TAG = "Utility";

    public String ReadFromFile(Context context, String filename) {
        BufferedReader reader = null;
        StringBuilder returnString = new StringBuilder();

        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename), "UTF-16LE"));

            String mline;
            while ((mline = reader.readLine()) != null) {
                returnString.append(mline);
            }
            return returnString.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error reading file: " + filename, e);
            return "";
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing reader", e);
                }
            }
        }
    }

    public void SaveWordsFromFile(Context context, String filename) {
        try {
            String words = ReadFromFile(context, filename);
            String[] allWords = words.split("00");

            DatabaseLogic dbhealper = new DatabaseLogic(context);

            for (String wordEntry : allWords) {
                String[] SingleWord = wordEntry.split("\\$");  // Fixed regex issue

                if (SingleWord.length >= 3 && !SingleWord[0].trim().isEmpty()) {
                    WordModel wordModel = new WordModel();
                    wordModel.setEnglishWord(SingleWord[0]);
                    wordModel.setMeaning1(SingleWord[1]);
                    wordModel.setMeaning2(SingleWord[2]);

                    dbhealper.addNewWord(wordModel);  // Fixed incorrect DBHelper reference
                } else {
                    Log.w(TAG, "Skipping malformed entry: " + wordEntry);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error processing file: " + filename, e);
        }
    }
}