package com.developersunit.spot_it_hackfes2k25;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity{
    int DRAW_REQUEST_CODE=0;
    private ToggleButton toggleButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_history) {
                selectedFragment = new HistoryFragment();
            } else if (item.getItemId() == R.id.nav_settings) {
                selectedFragment = new SettingsFragment();
            }
            if(selectedFragment != null){
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            return true;
        });

    }

    // Requests necessary permissions
    private void requestPermissions() {
        if (!Settings.canDrawOverlays(this)) {
            // Request Overlay permission
            Intent overlayIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(overlayIntent, DRAW_REQUEST_CODE);
        } else if (!isAccessibilityServiceEnabled()) {
            // Request Accessibility permission
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(this, "Enable Accessibility Service for this app", Toast.LENGTH_LONG).show();
        } else {
            // Start Floating Service if permissions are granted
            startFloatingService();
        }
    }

    // Check if the accessibility service is enabled
    private boolean isAccessibilityServiceEnabled() {
        String service = getPackageName() + "/" + MyAccessibilityService.class.getName();
        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(':');
        String enabledServices = Settings.Secure.getString(getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

        if (enabledServices != null) {
            splitter.setString(enabledServices);
            while (splitter.hasNext()) {
                if (splitter.next().equalsIgnoreCase(service)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Start Floating Button Service
    private void startFloatingService() {
        Intent intent = new Intent(this, ButtonService.class);
        startService(intent);
    }

    // Stop Floating Button Service
    private void stopFloatingService() {
        Intent intent = new Intent(this, ButtonService.class);
        stopService(intent);
    }

    // Handle Overlay Permission Result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DRAW_REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                requestPermissions(); // Check accessibility permission next
            } else {
                toggleButton.setChecked(false); // Disable toggle if permission is denied
            }
        }
    }
}

