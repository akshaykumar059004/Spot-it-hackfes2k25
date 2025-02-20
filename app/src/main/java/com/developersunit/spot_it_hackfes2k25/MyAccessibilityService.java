package com.developersunit.spot_it_hackfes2k25;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("Accessibility", "Event received: " + event.toString());
    }

    @Override
    public void onInterrupt() {
        Log.d("Accessibility", "Service Interrupted");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d("Accessibility", "Service Connected");
    }
}
