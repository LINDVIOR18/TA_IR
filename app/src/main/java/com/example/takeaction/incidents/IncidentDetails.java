package com.example.takeaction.incidents;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.takeaction.R;

public class IncidentDetails extends AppCompatActivity {

    public static final String INCIDENT_KEY = "INCIDENT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_details);

        IncidentList incident = (IncidentList) getIntent().getSerializableExtra(INCIDENT_KEY);
        Toast.makeText(this, incident.getTitle(), Toast.LENGTH_LONG).show();
    }
}
