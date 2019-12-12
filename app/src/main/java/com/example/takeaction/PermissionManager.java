package com.example.takeaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

    // Defining Buttons
    private LinearLayout storage, camera;

    // Defining Permission codes.
    // We can give any value
    // but unique for each permission.
    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;

    // Function to check and request permission.
    public static void checkCameraPermission(Activity activity, Callback callback)
    {
        checkPermission(activity, Manifest.permission.CAMERA, callback, CAMERA_PERMISSION_CODE);
    }

    public static void checkStoragePermission(Activity activity, Callback callback)
    {
        checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, callback, STORAGE_PERMISSION_CODE);
    }

    private static void checkPermission(Activity activity, String permission, Callback callback, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(activity,
                    new String[] { permission },
                    requestCode);
            callback.permissionDenied();
        }
        else {
            Toast.makeText(activity,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
            callback.permissionGranted();
        }
    }

    public interface Callback {
        void permissionGranted();
        void permissionDenied();
    }

}
