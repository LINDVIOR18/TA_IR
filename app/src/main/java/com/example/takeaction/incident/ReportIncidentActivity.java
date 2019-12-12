package com.example.takeaction.incident;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.takeaction.NavigationDrawer;
import com.example.takeaction.R;
import com.example.takeaction.address.GetIncidentCoordinatesActivity;
import com.example.takeaction.address.IncidentAddress;
import com.example.takeaction.cameradialog.CameraDialog;
import com.example.takeaction.firebase.AuthDataCallback;
import com.example.takeaction.firebase.IncidentRepository;
import com.example.takeaction.model.CategoryModel;
import com.example.takeaction.model.IncidentModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static com.example.takeaction.PermissionManager.CAMERA_PERMISSION_CODE;
import static com.example.takeaction.PermissionManager.STORAGE_PERMISSION_CODE;

public class ReportIncidentActivity extends NavigationDrawer implements DatePickerDialog.OnDateSetListener {
    public static final String ADDRESS_KEY = "ADDRESS_KEY";
    static final int PERMISSION_REQUEST_CAMERA = 100;
    static final int PERMISSION_REQUEST_GALLERY = 101;
    static final int INTENT_GET_COORDINATES = 102;
    private static final String DEFAULT_IMG = "data:image/jpeg;base64,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";
    private TextView coordinates;
    private IncidentRepository incidentRepository;
    private ImageButton imageButton;
    private IncidentAddress address;
    private TextInputLayout etTitle;
    private TextInputLayout etDescription;
    private long date;
    private CategoryModel categoryModel;
    private Object Bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_incident);
        Spinner categoriesSpinner = findViewById(R.id.spCategories);

        incidentRepository = new IncidentRepository(FirebaseDatabase.getInstance().getReference());
        categoryModel = getCategoryListMock().get(0);

        CategoryAdapter adapter = new CategoryAdapter(this,
                R.layout.spinner_category, getCategoryListMock());
        categoriesSpinner.setAdapter(adapter);

        Button btnAddress = findViewById(R.id.adress);
        Button btnDate = findViewById(R.id.btnDate);
        Button btnSent = findViewById(R.id.send);
        coordinates = findViewById(R.id.coordinates);
        etTitle = findViewById(R.id.title);
        etDescription = findViewById(R.id.description);
        imageButton = findViewById(R.id.cameraButton);

        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportIncidentActivity.this, GetIncidentCoordinatesActivity.class);
                startActivityForResult(intent, GetIncidentCoordinatesActivity.REQUEST_CODE);
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
                if (validateSubmit()) {
                    createIncident();
                }
            }
        });

        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryModel = getCategoryListMock().get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_report_incident;
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
        date = c.getTimeInMillis();

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        Button btnDate = findViewById(R.id.btnDate);
        btnDate.setText(currentDateString);
    }

    public void onClickShowPopUp(View view) {
        CameraDialog cameraDialog = new CameraDialog(this);
        cameraDialog.show();
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

    private boolean validateSubmit() {
        int messageRes = -1;
        if (TextUtils.isEmpty(etTitle.getEditText().getText().toString())) {
            messageRes = R.string.etTitle;
        } else if (TextUtils.isEmpty(etDescription.getEditText().getText().toString())) {
            messageRes = R.string.etDescription;
        } else if (address == null) {
            messageRes = R.string.setAddress;
        } else if (date == 0) {
            messageRes = R.string.setDate;
        }
        if (messageRes == -1) {
            return true;
        } else {
            Toast.makeText(this, messageRes, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GetIncidentCoordinatesActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                address = data.getParcelableExtra(GetIncidentCoordinatesActivity.ADDRESS_KEY);
                coordinates.setText(Objects.requireNonNull(address).getName());
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

        if ((requestCode == CAMERA_PERMISSION_CODE) && resultCode == RESULT_OK) {

            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");

            imageButton = findViewById(R.id.cameraButton);
            imageButton.setImageBitmap(bitmap);
        }
        if ((requestCode == STORAGE_PERMISSION_CODE) && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imageButton = findViewById(R.id.cameraButton);
            imageButton.setImageBitmap(selectedImage);
        }
        if (requestCode == STORAGE_PERMISSION_CODE && resultCode == RESULT_OK && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                ImageButton imageButton = findViewById(R.id.cameraButton);
                imageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void createIncident() {

        IncidentModel incidentModel = new IncidentModel(incidentRepository.getUid(), incidentRepository.getAuthor(), Objects.requireNonNull(etTitle.getEditText()).getText().toString(),
                Objects.requireNonNull(etDescription.getEditText()).getText().toString(),
                categoryModel, address, date, DEFAULT_IMG);

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
