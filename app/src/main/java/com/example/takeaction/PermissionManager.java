package com.example.takeaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionManager {

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;

    public static void checkCameraPermission(Activity activity, Callback callback) {
        checkPermission(activity, Manifest.permission.CAMERA, callback, CAMERA_PERMISSION_CODE);
    }

    public static void checkStoragePermission(Activity activity, Callback callback) {
        checkPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, callback, STORAGE_PERMISSION_CODE);
    }

    private static void checkPermission(Activity activity, String permission, Callback callback, int requestCode) {
        if (ContextCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    requestCode);
            callback.permissionDenied();
        } else {
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
