package com.example.takeaction.cameradialog;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


import com.example.takeaction.PermissionManager;
import com.example.takeaction.R;

import static com.example.takeaction.PermissionManager.CAMERA_PERMISSION_CODE;

public class CameraDialog {
    private Activity activity;
    private LinearLayout storage;
    private LinearLayout camera;
    private View popupView;

    public CameraDialog(Activity activity) {
        this.activity = activity;

        LayoutInflater inflater = (LayoutInflater)
                activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.pop_up, null);

        storage = popupView.findViewById(R.id.ll_gallery);
        camera = popupView.findViewById(R.id.ll_camera);

        setListeners();
    }

    public void show() {
        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(new View(activity), Gravity.CENTER, 0, 0);
    }

    private void setListeners() {
        //        // Set Buttons on Click Listeners
        storage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionManager.checkStoragePermission(activity, new PermissionManager.Callback() {
                    @Override
                    public void permissionGranted() {
                        Intent openGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if (openGallery.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivityForResult(openGallery, PermissionManager.STORAGE_PERMISSION_CODE);
                        }
                    }

                    @Override
                    public void permissionDenied() {

                    }
                });
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionManager.checkCameraPermission(activity, new PermissionManager.Callback() {
                    @Override
                    public void permissionGranted() {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
                            activity.startActivityForResult(takePictureIntent, CAMERA_PERMISSION_CODE);
                        }
                    }

                    @Override
                    public void permissionDenied() {

                    }
                });
            }
        });
    }
}
