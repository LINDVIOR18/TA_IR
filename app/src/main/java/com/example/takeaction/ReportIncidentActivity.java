package com.example.takeaction;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.fragment.app.DialogFragment;

import com.example.takeaction.data.Category;
import com.example.takeaction.data.CategoryAdapter;
import com.example.takeaction.data.DatePickerFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ReportIncidentActivity extends NavigationDrawer implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_report_incident;
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
}


