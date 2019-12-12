package com.example.takeaction.incidents;

import android.os.Bundle;
import android.widget.Toast;

import com.example.takeaction.NavigationDrawer;
import com.example.takeaction.R;

public class IncidentDetails extends NavigationDrawer {

    public static final String INCIDENT_KEY = "INCIDENT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IncidentList incident = (IncidentList) getIntent().getSerializableExtra(INCIDENT_KEY);
        Toast.makeText(this, incident.getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_incident_details;
    }
}
