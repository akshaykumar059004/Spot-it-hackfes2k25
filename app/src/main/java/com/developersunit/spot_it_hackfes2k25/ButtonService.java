package com.developersunit.spot_it_hackfes2k25;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class ButtonService extends AccessibilityService implements View.OnTouchListener {
    private WindowManager windowManager;
    private ImageView button;
    private WindowManager.LayoutParams params;

    private int initialX, initialY;
    private float initialTouchX, initialTouchY;

    private long touchStartTime = 0;


    private static final int SCREENSHOT_REQUEST_CODE = 1001;

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        Log.d("ButtonService", "Accessibility Service Connected");

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        button = new ImageView(this);
        button.setImageResource(R.drawable.button);

        params = new WindowManager.LayoutParams(
                150, 150,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.x = 0;  // Start at top-left
        params.y = 200;

        windowManager.addView(button, params);
        button.setOnTouchListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (button != null) {
            windowManager.removeView(button);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialX = params.x;
                initialY = params.y;
                initialTouchX = event.getRawX();
                initialTouchY = event.getRawY();
                touchStartTime = System.currentTimeMillis(); // Capture start time
                return true;

            case MotionEvent.ACTION_MOVE:
                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                params.y = initialY + (int) (event.getRawY() - initialTouchY);
                windowManager.updateViewLayout(button, params);
                return true;

            case MotionEvent.ACTION_UP:
                long touchDuration = System.currentTimeMillis() - touchStartTime;

                // If touch duration is short, trigger screenshot
                if (touchDuration < 200) { // 200ms threshold for a tap
                    Toast.makeText(getApplicationContext(), "Capturing screenshot...", Toast.LENGTH_SHORT).show();
                    startScreenshotActivity();
                }
                return true;
        }
        return false;
    }

    private void startScreenshotActivity() {
        Intent intent = new Intent(this, ScreenshotActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {}

    @Override
    public void onInterrupt() {
        Log.d("ButtonService", "Accessibility Service Interrupted");
    }
}
