package com.developersunit.spot_it_hackfes2k25;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.nav_history) {
                startActivity(new Intent(MainActivity.this, History.class));
                return true;
            } else if (item.getItemId() == R.id.nav_settings) {
                startActivity(new Intent(MainActivity.this, Settings.class));
                return true;
            }
            return false;
        });
    }
}
