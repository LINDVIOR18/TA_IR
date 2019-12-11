package com.example.takeaction.incidents;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.takeaction.R;

public class IncidentDetails extends AppCompatActivity {


    public static final String INCIDENT_KEY = "INCIDENT_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incident_details);
        TextView tvTitle = findViewById(R.id.tv_incident);
        TextView tvAddress = findViewById(R.id.tv_address);
        ImageView ivImage = findViewById(R.id.iv_incident);
        TextView tvText = findViewById(R.id.tv_description);

        IncidentList incident = (IncidentList) getIntent().getSerializableExtra(INCIDENT_KEY);
        assert incident != null;
        tvTitle.setText(incident.getTitle());
        tvAddress.setText(incident.getAddress());
        ivImage.setImageResource(incident.getImg());
        tvText.setText(incident.getDescription());
    }
}
