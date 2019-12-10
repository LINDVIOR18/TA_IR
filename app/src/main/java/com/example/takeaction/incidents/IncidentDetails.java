package com.example.takeaction.incidents;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.takeaction.R;
import com.example.takeaction.model.IncidentModel;

import java.util.Objects;

public class IncidentDetails extends AppCompatActivity {

    public static final String INCIDENT_KEY = "INCIDENT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_details);

        IncidentModel incident = (IncidentModel) getIntent().getSerializableExtra(INCIDENT_KEY);
        Toast.makeText(this, Objects.requireNonNull(incident).getTitle(), Toast.LENGTH_LONG).show();
    }
}
