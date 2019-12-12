package com.example.takeaction.incident;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.takeaction.NavigationDrawer;
import com.example.takeaction.R;
import com.example.takeaction.address.GetIncidentCoordinatesActivity;
import com.example.takeaction.address.IncidentAddress;
import com.example.takeaction.firebase.AuthDataCallback;
import com.example.takeaction.firebase.IncidentRepository;
import com.example.takeaction.homemap.HomeMapActivity;
import com.example.takeaction.model.CategoryModel;
import com.example.takeaction.model.IncidentModel;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ReportIncidentActivity extends NavigationDrawer implements DatePickerDialog.OnDateSetListener {

    public static final String ADDRESS_KEY = "ADDRESS_KEY";
    private TextView coordinates;
    private IncidentRepository incidentRepository;
    private IncidentAddress address;
    private TextInputLayout etTitle;
    private TextInputLayout etDescription;
    private long date;
    private CategoryModel categoryModel;

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

    private boolean validateSubmit() {
        int messageRes = -1;
        if (TextUtils.isEmpty(Objects.requireNonNull(etTitle.getEditText()).getText().toString())) {
            messageRes = R.string.etTitle;
        } else if (TextUtils.isEmpty(Objects.requireNonNull(etDescription.getEditText()).getText().toString())) {
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
        }
    }


    private void createIncident() {

        IncidentModel incidentModel = new IncidentModel(incidentRepository.getUid(), Objects.requireNonNull(etTitle.getEditText()).getText().toString(),
                Objects.requireNonNull(etDescription.getEditText()).getText().toString(),
                categoryModel, address, date);

        incidentRepository.createIncident(incidentModel, new AuthDataCallback<IncidentModel>() {
            @Override
            public void onSuccess(IncidentModel response) {
                Toast.makeText(ReportIncidentActivity.this, "incidentRepository success", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ReportIncidentActivity.this,HomeMapActivity.class));
                finish();
            }

            @Override
            public void onError() {
                Toast.makeText(ReportIncidentActivity.this, "incidentRepository error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
