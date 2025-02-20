package com.developersunit.spot_it_hackfes2k25;

import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.Settings.Secure;

public class SettingsFragment extends Fragment {

    private ToggleButton toggleButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toggleButton = view.findViewById(R.id.toggleButton);

        if (toggleButton != null) {
            toggleButton.setChecked(isOverlayPermissionGranted() && isAccessibilityServiceEnabled());

            toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    requestOverlayPermission();
                    requestAccessibilityPermission();
                } else {
                    revokeOverlayPermission();
                    revokeAccessibilityPermission();
                }
            });
        } else {
            Log.e("SettingsFragment", "ToggleButton is NULL. Check your layout!");
        }
    }

    // ✅ **Overlay Permission Methods**
    private boolean isOverlayPermissionGranted() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(requireContext());
    }

    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(requireContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + requireContext().getPackageName()));
            startActivity(intent);
            Toast.makeText(requireContext(), "Enable 'Draw over other apps' permission", Toast.LENGTH_LONG).show();
        }
    }

    private void revokeOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Toast.makeText(requireContext(), "Overlay permission cannot be revoked automatically. Disable it manually.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(intent);
        }
    }

    // ✅ **Accessibility Permission Methods**
    private boolean isAccessibilityServiceEnabled() {
        String serviceId = requireContext().getPackageName() + "/" + MyAccessibilityService.class.getName();
        String enabledServices = Secure.getString(requireContext().getContentResolver(), Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return enabledServices != null && enabledServices.contains(serviceId);
    }

    private void requestAccessibilityPermission() {
        if (!isAccessibilityServiceEnabled()) {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
            Toast.makeText(requireContext(), "Enable Accessibility for this app", Toast.LENGTH_LONG).show();
        }
    }

    private void revokeAccessibilityPermission() {
        Toast.makeText(requireContext(), "Go to Accessibility settings and disable the service.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
    }
}
