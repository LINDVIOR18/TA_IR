package com.example.takeaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import com.example.takeaction.data.CategoryAdapter;
import com.example.takeaction.data.DatePickerFragment;
import com.example.takeaction.model.Category;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReportIncidentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    static final  int PERMISSION_REQUEST_CAMERA = 100;
    static final int PERMISSION_REQUEST_GALLERY = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);
        Spinner categoriesSpinner = findViewById(R.id.spCategories);


        CategoryAdapter adapter = new CategoryAdapter(this,
                R.layout.spinner_category, getCategoryListMock());
        categoriesSpinner.setAdapter(adapter);

        Button btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date Picker");
            }
        });

    }

    private List<Category> getCategoryListMock() {
        List<Category> category = new ArrayList<Category>();
        category.add(new Category(1, "Fire", R.drawable.ic_fire));
        category.add(new Category(2, "Flood", R.drawable.ic_home_flood));
        category.add(new Category(3, "Earthquake", R.drawable.ic_earthquake));
        category.add(new Category(4, "Trauma", R.drawable.ic_medical_bag));
        category.add(new Category(5, "Electric Shock", R.drawable.ic_electric));
        category.add(new Category(6, "Avalanche", R.drawable.ic_mountain));
        category.add(new Category(7, "Heat-stroke", R.drawable.ic_white_balance_sunny));
        category.add(new Category(8, "Heart-Attack", R.drawable.ic_heart_pulse));
        category.add(new Category(9, "Drowning", R.drawable.ic_waves));
        category.add(new Category(10, "Landslide", R.drawable.ic_slope_downhill));

        return category;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Button btnDate = findViewById(R.id.btnDate);
        btnDate.setText(currentDateString);
    }
    public void onClickShowPopUp(View view) {



        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.pop_up, null);


        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        } else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_GALLERY);
            } else {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, PERMISSION_REQUEST_GALLERY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        popupView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, 100);
                }
                return true;
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA:
                default:
            case PERMISSION_REQUEST_GALLERY:
                break;
        }
    }
}


