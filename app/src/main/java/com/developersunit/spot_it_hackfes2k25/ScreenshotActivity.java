package com.developersunit.spot_it_hackfes2k25;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ScreenshotActivity extends Activity {
    private static final int SCREENSHOT_REQUEST_CODE = 1001;
    private MediaProjectionManager mediaProjectionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaProjectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        Intent intent = mediaProjectionManager.createScreenCaptureIntent();
        startActivityForResult(intent, SCREENSHOT_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREENSHOT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Intent serviceIntent = new Intent(this, ScreenshotService.class);
            serviceIntent.putExtra("RESULT_CODE", resultCode);
            serviceIntent.putExtra("DATA", data);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                startForegroundService(serviceIntent);
            } else {
                startService(serviceIntent);
            }
        } else {
            Toast.makeText(this, "Screenshot permission denied", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
