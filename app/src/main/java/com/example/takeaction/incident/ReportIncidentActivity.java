package com.example.takeaction.incident;

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
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import com.example.takeaction.R;
import com.example.takeaction.firebase.AuthDataCallback;
import com.example.takeaction.firebase.IncidentRepository;
import com.example.takeaction.model.CategoryModel;
import com.example.takeaction.model.IncidentModel;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportIncidentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    static final int PERMISSION_REQUEST_CAMERA = 100;
    static final int PERMISSION_REQUEST_GALLERY = 101;
    private IncidentRepository incidentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);
        Spinner categoriesSpinner = findViewById(R.id.spCategories);

        incidentRepository = new IncidentRepository(FirebaseDatabase.getInstance().getReference());

        CategoryAdapter adapter = new CategoryAdapter(this,
                R.layout.spinner_category, getCategoryListMock());
        categoriesSpinner.setAdapter(adapter);

        Button btnAddress = findViewById(R.id.adress);
        Button btnDate = findViewById(R.id.btnDate);
        Button btnSent = findViewById(R.id.send);

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date Picker");
            }
        });
        btnSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createIncident();
            }
        });
    }

    private List<CategoryModel> getCategoryListMock() {
        List<CategoryModel> categoryModel = new ArrayList<>();
        categoryModel.add(new CategoryModel(1, "Fire", R.drawable.ic_fire));
        categoryModel.add(new CategoryModel(2, "Flood", R.drawable.ic_home_flood));
        categoryModel.add(new CategoryModel(3, "Earthquake", R.drawable.ic_earthquake));
        categoryModel.add(new CategoryModel(4, "Trauma", R.drawable.ic_medical_bag));
        categoryModel.add(new CategoryModel(5, "Electric Shock", R.drawable.ic_electric));
        categoryModel.add(new CategoryModel(6, "Avalanche", R.drawable.ic_mountain));
        categoryModel.add(new CategoryModel(7, "Heat-stroke", R.drawable.ic_white_balance_sunny));
        categoryModel.add(new CategoryModel(8, "Heart-Attack", R.drawable.ic_heart_pulse));
        categoryModel.add(new CategoryModel(9, "Drowning", R.drawable.ic_waves));
        categoryModel.add(new CategoryModel(10, "Landslide", R.drawable.ic_slope_downhill));

        return categoryModel;
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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
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

    private void createIncident() {

        IncidentModel incidentModel = new IncidentModel(incidentRepository.getUid(), "user", "Title", "desc",
                new CategoryModel(1, "name", 1), null, 15, null);

        incidentRepository.createIncident(incidentModel, new AuthDataCallback<IncidentModel>() {
            @Override
            public void onSuccess(IncidentModel response) {
                Toast.makeText(ReportIncidentActivity.this, "incidentRepository success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                Toast.makeText(ReportIncidentActivity.this, "incidentRepository error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
