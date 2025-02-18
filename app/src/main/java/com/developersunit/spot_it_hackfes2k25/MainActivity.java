package com.developersunit.spot_it_hackfes2k25;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;




public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        //Intent intent = new Intent(MainActivity.this, History.class);
       // startActivity(intent);
        Button history_button = findViewById(R.id.nextbutton);
        history_button.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, History.class);
            startActivity(intent2);
        });

        Button settings_button = findViewById(R.id.settingsbutton);
        settings_button.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, Settings.class);
            startActivity(intent1);
        });



    }
}
