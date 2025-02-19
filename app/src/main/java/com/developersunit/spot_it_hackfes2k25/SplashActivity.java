package com.developersunit.spot_it_hackfes2k25;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.developersunit.spot_it_hackfes2k25.Module.Utility;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);

       final Context context = this;
        Thread timerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Pause for 1 second (1000 milliseconds)
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // Setup and navigate to MainActivity after the splash screen
                    SetupWords(context);
                    finish();
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
       timerThread.start();
    }
    private void SetupWords(Context context){
        Utility utility = new Utility();
        SharedPreferences GeneralSettings = getSharedPreferences("GeneralSettings",MODE_PRIVATE);
        String WordsLoaded = GeneralSettings.getString("WordsLoaded","False");

        if(WordsLoaded.equals("False")){
            utility.SaveWordsFromFile(context,"dictionary.txt");
            SharedPreferences.Editor editor;
            editor = GeneralSettings.edit();
            editor.putString("WordsLoaded","True");
            editor.apply();

        }

    }
}
