package com.example.takeaction.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.takeaction.NavigationDrawer;
import com.example.takeaction.PermissionManager;
import com.example.takeaction.R;
import com.example.takeaction.cameradialog.CameraDialog;

import java.io.IOException;
import java.util.Objects;

import static com.example.takeaction.PermissionManager.CAMERA_PERMISSION_CODE;
import static com.example.takeaction.PermissionManager.STORAGE_PERMISSION_CODE;

@SuppressLint("Registered")
public class ProfileActivity extends NavigationDrawer {

    private static final int pic_id = 123;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = findViewById(R.id.imgV_avatar);

    }
    @Override
    protected int getLayoutRes() {
        return R.layout.activity_profile;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this,
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(ProfileActivity.this,
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else if (requestCode == PermissionManager.STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ProfileActivity.this,
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(ProfileActivity.this,
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


    public void onClickShowPopUp(View view) {
        CameraDialog cameraDialog = new CameraDialog(this);
        cameraDialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == CAMERA_PERMISSION_CODE || requestCode == STORAGE_PERMISSION_CODE) && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            imageView.setImageBitmap(bitmap);
        }
        if (requestCode == STORAGE_PERMISSION_CODE && resultCode == RESULT_OK && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageView imageView = findViewById(R.id.imgV_avatar);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}