package com.example.takeaction.incidents;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        ivImage.setBackground(this.getResources().getDrawable(incident.getImg(), null));
        tvText.setText(incident.getDescription());
    }
}
